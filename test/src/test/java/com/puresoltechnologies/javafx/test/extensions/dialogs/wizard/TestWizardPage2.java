package com.puresoltechnologies.javafx.test.extensions.dialogs.wizard;

import com.puresoltechnologies.javafx.extensions.dialogs.wizard.AbstractWizardPage;

import javafx.scene.Node;
import javafx.scene.control.Slider;

public class TestWizardPage2 extends AbstractWizardPage<TestWizardResult> {

    private final Slider slider = new Slider(0, 100, 0);

    public TestWizardPage2() {
	super("Wizard Page 2", "An integer is to be entered.");

	setCanProceed(false);
	canFinishProperty().bind(slider.valueProperty().greaterThan(0.0));
    }

    @Override
    public Node getNode() {
	return slider;
    }

}
