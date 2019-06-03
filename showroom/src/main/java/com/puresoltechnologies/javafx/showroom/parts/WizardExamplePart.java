package com.puresoltechnologies.javafx.showroom.parts;

import java.util.Optional;

import com.puresoltechnologies.javafx.extensions.dialogs.wizard.WizardDialog;
import com.puresoltechnologies.javafx.perspectives.PartHeaderToolBar;
import com.puresoltechnologies.javafx.perspectives.parts.AbstractViewer;
import com.puresoltechnologies.javafx.perspectives.parts.PartContentType;
import com.puresoltechnologies.javafx.perspectives.parts.PartOpenMode;
import com.puresoltechnologies.javafx.showroom.dialogs.wizards.ExampleWizardData;
import com.puresoltechnologies.javafx.showroom.dialogs.wizards.ExampleWizardPage1;
import com.puresoltechnologies.javafx.showroom.dialogs.wizards.ExampleWizardPage2;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class WizardExamplePart extends AbstractViewer {

    private final Button startWizard = new Button("Start Wizard...");
    private final VBox content = new VBox(startWizard);

    public WizardExamplePart() {
	super("Wizard Dialog Example", PartOpenMode.AUTO_AND_MANUAL, PartContentType.ONE_PER_PERSPECTIVE);
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
	WizardDialog<ExampleWizardData> wizardDialog = new WizardDialog<>(ExampleWizardData::new);
	wizardDialog.setTitle("Wizard Example");
	ExampleWizardData data = new ExampleWizardData();
	wizardDialog.addPage(new ExampleWizardPage1(data));
	wizardDialog.addPage(new ExampleWizardPage2(data));
	wizardDialog.showAndWait();
    }

    @Override
    public Node getContent() {
	return content;
    }

}
