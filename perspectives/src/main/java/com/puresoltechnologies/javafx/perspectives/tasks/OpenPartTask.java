package com.puresoltechnologies.javafx.perspectives.tasks;

import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.PerspectiveService;
import com.puresoltechnologies.javafx.perspectives.dialogs.PartSelectionDialog;
import com.puresoltechnologies.javafx.perspectives.parts.Part;

import javafx.concurrent.Task;

public class OpenPartTask extends Task<Boolean> {

    public OpenPartTask() {
	super();
    }

    @Override
    protected Boolean call() throws Exception {
	Optional<Part> part = new PartSelectionDialog().showAndWait();
	if (part.isPresent()) {
	    Part partInstance = part.get();
	    if (partInstance.manualInitialization()) {
		PerspectiveService.openPart(partInstance);
		return true;
	    }
	}
	return false;
    }

}
