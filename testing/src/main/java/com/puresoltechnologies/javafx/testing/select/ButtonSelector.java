package com.puresoltechnologies.javafx.testing.select;

import javafx.scene.control.Button;

public interface ButtonSelector extends NodeSearch {

    default Selection<Button> findButtonById(String id) {
	return findNodeById(Button.class, id);
    }

    default Selection<Button> findButtonByText(String text) {
	return findNodeByText(Button.class, text);
    }

}
