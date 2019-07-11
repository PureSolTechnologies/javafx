package com.puresoltechnologies.javafx.showroom.dialogs.wizards;

import com.puresoltechnologies.javafx.extensions.dialogs.wizard.AbstractWizardPage;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ExampleWizardPage2 extends AbstractWizardPage<ExampleWizardData> {

    private final GridPane gridPane = new GridPane();

    public ExampleWizardPage2(ExampleWizardData data) {
	super("Step 2", "Description for Step 2");

	CheckBox proceedCheckBox = new CheckBox("Can proceed");
	GridPane.setConstraints(proceedCheckBox, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES,
		Priority.NEVER);

	CheckBox finishCheckBox = new CheckBox("Can finish");
	GridPane.setConstraints(finishCheckBox, 0, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES, Priority.NEVER);

	gridPane.getChildren().addAll(proceedCheckBox, finishCheckBox);
	canProceedProperty().bind(proceedCheckBox.selectedProperty());
	canFinishProperty().bind(finishCheckBox.selectedProperty());
    }

    @Override
    public Node getNode() {
	return gridPane;
    }

    @Override
    public void initialize() {
	// Intentionally left empty.
    }

    @Override
    public void close(ButtonType type) {
	// Intentionally left empty.
    }

    @Override
    public void onArrival() {
	// Intentionally left empty.
    }

    @Override
    public void onBack() {
	// Intentionally left empty.
    }

    @Override
    public void onNext() {
	// Intentionally left empty.
    }
}
