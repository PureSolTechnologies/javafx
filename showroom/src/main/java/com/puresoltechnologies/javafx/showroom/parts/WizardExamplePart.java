package com.puresoltechnologies.javafx.showroom.parts;

import java.util.Optional;

import com.puresoltechnologies.javafx.extensions.dialogs.wizard.WizardDialog;
import com.puresoltechnologies.javafx.perspectives.PartHeaderToolBar;
import com.puresoltechnologies.javafx.perspectives.parts.AbstractViewer;
import com.puresoltechnologies.javafx.perspectives.parts.PartOpenMode;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class WizardExamplePart extends AbstractViewer {

    private final Button startWizard = new Button("Start Wizard...");
    private final VBox content = new VBox(startWizard);

    public WizardExamplePart() {
	super("Wizard Dialog Example", PartOpenMode.AUTO_AND_MANUAL);
    }

    @Override
    public Optional<PartHeaderToolBar> getToolBar() {
	return Optional.empty();
    }

    @Override
    public void close() {
	// Intentionally left blank
    }

    @Override
    public void initialize() {
	startWizard.setOnAction((event) -> {
	    startWizard();
	    event.consume();
	});
    }

    private void startWizard() {
	WizardDialog<String> wizardDIalog = new WizardDialog<>();
	wizardDIalog.showAndWait();
    }

    @Override
    public Node getContent() {
	return content;
    }

}
