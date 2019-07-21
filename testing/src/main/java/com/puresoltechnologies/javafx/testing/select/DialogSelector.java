package com.puresoltechnologies.javafx.testing.select;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public interface DialogSelector extends NodeSearch {

    default Selection<Parent> findDialogByTitle(String title) {
	List<Window> stages = Window.getWindows().stream()
		.filter(window -> Stage.class.equals(window.getClass()) && title.equals(((Stage) window).getTitle()))
		.collect(Collectors.toList());
	if (stages.isEmpty()) {
	    return null;
	}
	if (stages.size() > 1) {
	    throw new IllegalStateException("Multiple stages were found.");
	}
	return new Selection<>(stages.get(0).getScene().getRoot());

    }

    default Selection<DialogPane> findStageByTitle(String title) {
	List<Node> nodes = findNodes(node -> DialogPane.class.equals(node.getClass()));
	if (nodes.isEmpty()) {
	    return null;
	}

	DialogPane dialog = null;
	for (Node node : nodes) {
	    DialogPane dialogPane = (DialogPane) node;
	    Scene scene = dialogPane.getScene();
	    Window window = scene.getWindow();
	    if (Stage.class.isAssignableFrom(window.getClass())) {
		Stage stage = (Stage) window;
		if (stage.getTitle().equals(title)) {
		    if (dialog != null) {
			throw new IllegalStateException("Multiple dialogs were found.");
		    }
		    dialog = dialogPane;
		}
	    }
	}
	return new Selection<>(dialog);

    }

}
