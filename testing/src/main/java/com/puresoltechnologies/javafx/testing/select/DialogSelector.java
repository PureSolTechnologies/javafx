package com.puresoltechnologies.javafx.testing.select;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public interface DialogSelector extends NodeSearch {

    default Selection<DialogPane> findDialogByTitle(String title) {
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
