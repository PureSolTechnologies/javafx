package com.puresoltechnologies.javafx.testing.select;

import javafx.scene.control.Button;

public interface ButtonSelector extends NodeFullSearch {

    default Selection<Button> getButtonById(String id) {
	return findNodeById(Button.class, id);
    }

    default Selection<Button> getButtonByText(String text) {
	return findNodeByText(Button.class, text);
    }

}
