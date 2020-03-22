package com.puresoltechnologies.javafx.testing.select;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.Window;

public interface DialogSelector extends NodeSearch {

    default Selection<Parent> findDialogByTitle(String title) {
	List<Window> stages = Window.getWindows().stream() //
		.filter(window -> Stage.class.isAssignableFrom(window.getClass())
			&& title.equals(((Stage) window).getTitle())) //
		.collect(Collectors.toList());
	if (stages.isEmpty()) {
	    return null;
	}
	if (stages.size() > 1) {
	    throw new IllegalStateException("Multiple stages were found.");
	}
	return new Selection<>(stages.get(0).getScene().getRoot());

    }

}
