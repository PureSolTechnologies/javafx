package com.puresoltechnologies.javafx.services.dialogs;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class ServicesControlDialog extends Dialog<Void> {

    public ServicesControlDialog() {
	super();
	setTitle("Services Control");
	getDialogPane().getButtonTypes().add(ButtonType.OK);
    }

}
