package com.puresoltechnologies.javafx.charts.axes;

import com.puresoltechnologies.javafx.charts.plots.Plot;

import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;

public class OrdinalAxisRenderer<T extends Comparable<T>, A extends OrdinalAxis<T>> extends NominalAxisRenderer<T, A> {

    public OrdinalAxisRenderer(A axis, ObservableList<Plot<?, ?, ?>> plots) {
	super(axis, plots);
	/*
	 * For Ordinal Axes, auto-scaling is possible to be handled.
	 */
	axis.minProperty().addListener((o, oldValue, newValue) -> {
	    autoScaleMinProperty().set(newValue == null);
	});
	axis.maxProperty().addListener((o, oldValue, newValue) -> {
	    autoScaleMaxProperty().set(newValue == null);
	});
    }

    @Override
    public void scale(double factor, double ratioMinToMax) {
	// TODO Auto-generated method stub
    }

    @Override
    public void move(double fractionOfRange) {
	// TODO Auto-generated method stub
    }

    @Override
    protected double getLabelThickness() {
	Text text = new Text("WQ");
	text.setFont(getAxis().getLabelFont().toFont());
	text.applyCss();
	return text.getLayoutBounds().getHeight();
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
