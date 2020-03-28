package com.puresoltechnologies.javafx.testing.utils;

import java.io.PrintStream;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Labeled;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * This class is used to print a complete component tree for testing and
 * debugging.
 *
 * This printer is meant to print a complete tree. That's why this printer
 * starts with {@link Window#getWindows()} and drills down the stages, scenes
 * and popups.
 *
 * @author Rick-Rainer Ludwig
 */
public class ComponentTreePrinter {

    private final PrintStream printStream;

    /**
     * This is the default value constructor.
     *
     * @param printStream is the {@link PrintStream} to print the component tree to.
     */
    public ComponentTreePrinter(PrintStream printStream) {
	super();
	this.printStream = printStream;
    }

    public void print() {
	ObservableList<Window> windows = Window.getWindows();
	for (Window window : windows) {
	    if (Stage.class.isAssignableFrom(window.getClass())) {
		printStageTree((Stage) window);
	    } else if (PopupWindow.class.isAssignableFrom(window.getClass())) {
		printPopupWindowTree((PopupWindow) window);
	    } else {
		printStream.println();
		printStream.println("Window: " + window.getClass());
		printStream.println();
	    }
	}
    }

    private void printStageTree(Stage stage) {
	printStream.println();
	printStream.println("Stage: '" + stage.getTitle() + "'");
	printStream.println();
	printRootTree(0, stage.getScene().getRoot());
    }

    private void printPopupWindowTree(PopupWindow popupWindow) {
	if (Popup.class.isAssignableFrom(popupWindow.getClass())) {
	    Popup popup = (Popup) popupWindow;
	    printPopupTree(popup);
	} else {
	    printStream.println();
	    printStream.println("PopupWindow");
	    printStream.println();
	}
    }

    private void printPopupTree(Popup popup) {
	printStream.println();
	printStream.println("Popup");
	printStream.println();
	printChildren(0, popup.getContent());
    }

    private void printIntend(int width) {
	StringBuilder builder = new StringBuilder();
	for (int i = 0; i < width; i++) {
	    builder.append("  ");
	}
	printStream.print(builder);
    }

    private String getText(Node node) {
	StringBuilder builder = new StringBuilder();
	if (node.getId() != null) {
	    builder.append("'" + node.getId() + "'");
	} else {
	    builder.append("<no id>");
	}
	if (Labeled.class.isAssignableFrom(node.getClass())) {
	    builder.append(" \"" + ((Labeled) node).getText() + "\"");
	}
	builder.append(" (" + node.getClass() + ")");
	return builder.toString();
    }

    private void printRootTree(int i, Parent root) {
	printIntend(i);
	printStream.print("+ ");
	printStream.println(getText(root));
	printChildren(i + 1, root.getChildrenUnmodifiable());
    }

    private void printChildren(int i, ObservableList<Node> children) {
	for (Node child : children) {
	    if (Parent.class.isAssignableFrom(child.getClass())) {
		printRootTree(i, (Parent) child);
	    } else {
		printIntend(i);
		printStream.print("- ");
		printStream.println(getText(child));
	    }
	}
    }

}
