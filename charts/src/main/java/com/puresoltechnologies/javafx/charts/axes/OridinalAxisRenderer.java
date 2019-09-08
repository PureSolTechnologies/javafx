package com.puresoltechnologies.javafx.charts.axes;

import com.puresoltechnologies.javafx.charts.plots.Plot;

import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;

public class OridinalAxisRenderer<A extends Axis<Comparable<Object>>>
	extends AbstractAxisRenderer<Comparable<Object>, A> {

    public OridinalAxisRenderer(A axis, ObservableList<Plot<?, ?, ?>> plots) {
	super(axis, plots);
    }

    @Override
    protected double getLabelThickness() {
	Text text = new Text("WQ");
	text.setFont(getAxis().getLabelFont().toFont());
	text.applyCss();
	return text.getLayoutBounds().getHeight();
    }

    @Override
    public double calculatePos(double x, double y, double width, double height, Comparable<Object> value) {
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
