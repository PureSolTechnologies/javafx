package com.puresoltechnologies.javafx.extensions.dialogs.procedure;

/**
 * This class represents a single step for the step interface.
 *
 * @author Rick-Rainer Ludwig
 */
public class Step<T> {

    private final StepInputPane<T> getStepInputPane;
    private final StepVisualizationPane<T> getStepVisualizationPane;

    public Step(StepInputPane<T> getStepInputPane, StepVisualizationPane<T> getStepVisualizationPane) {
	super();
	this.getStepInputPane = getStepInputPane;
	this.getStepVisualizationPane = getStepVisualizationPane;
    }

}
