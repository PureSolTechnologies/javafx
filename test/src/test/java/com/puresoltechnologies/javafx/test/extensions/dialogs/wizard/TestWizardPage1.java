package com.puresoltechnologies.javafx.test.extensions.dialogs.wizard;

import com.puresoltechnologies.javafx.extensions.dialogs.wizard.AbstractWizardPage;

import javafx.scene.Node;
import javafx.scene.control.TextArea;

public class TestWizardPage1 extends AbstractWizardPage<TestWizardResult> {

    private final TextArea textArea = new TextArea();

    public TestWizardPage1() {
	super("Wizard Page 1", "A string is to be entered.");

	canProceedProperty().bind(textArea.lengthProperty().greaterThan(0));
	setCanFinish(false);
    }

    @Override
    public Node getNode() {
	return textArea;
    }

    @Override
    public void initialize() {
	// Intentionally left empty.
    }

    @Override
    public void close() {
	// Intentionally left empty.
    }

    @Override
    public void onArrival() {
	// Intentionally left empty.
    }

    @Override
    public void onLeave() {
	// Intentionally left empty.
    }
}
