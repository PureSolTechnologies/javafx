package com.puresoltechnologies.javafx.charts.axes;

import com.puresoltechnologies.javafx.charts.plots.Plot;

import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;

public class NominalAxisRenderer<T, A extends Axis<T>> extends AbstractAxisRenderer<T, A> {

    public NominalAxisRenderer(A axis, ObservableList<Plot<?, ?, ?>> plots) {
	super(axis, plots);
    }

    @Override
    public void scale(double factor, double ratioMinToMax) {
	// intentionally left empty, because a nominal axis cannot be scaled
    }

    @Override
    public void move(double fractionOfRange) {
	// intentionally left empty, because a nominal axis cannot be moved
    }

    @Override
    protected double getLabelThickness() {
	Text text = new Text("WQ");
	text.setFont(getAxis().getLabelFont().toFont());
	text.applyCss();
	return 2 * text.getLayoutBounds().getHeight();
    }

    @Override
    public double calculatePos(double x, double y, double width, double height, T value) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    protected void drawTicks(GraphicsContext gc, double x, double y, double width, double height) {
	// TODO Auto-generated method stub

    }

    @Override
    protected void updateMinMax() {
	// TODO Auto-generated method stub

    }

}
