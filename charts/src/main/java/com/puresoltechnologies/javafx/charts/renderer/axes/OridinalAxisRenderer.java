package com.puresoltechnologies.javafx.charts.renderer.axes;

import java.util.List;

import com.puresoltechnologies.javafx.charts.axes.Axis;
import com.puresoltechnologies.javafx.charts.plots.Plot;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;

public class OridinalAxisRenderer extends AbstractAxisRenderer<Comparable<Object>> {

    public OridinalAxisRenderer(Canvas canvas, Axis<Comparable<Object>> axis, List<Plot<?, ?, ?>> plots) {
	super(canvas, axis, plots);
    }

    @Override
    public Comparable<Object> getMin() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Comparable<Object> getMax() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    protected double getLabelThickness() {
	Text text = new Text("WQ");
	text.setFont(axisLabelFont.get().toFont());
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

}
