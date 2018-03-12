package com.puresoltechnologies.javafx.charts.renderer.axis;

import java.util.List;

import com.puresoltechnologies.javafx.charts.plots.Axis;
import com.puresoltechnologies.javafx.charts.plots.Plot;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;

public class NominalAxisRenderer extends AbstractAxisRenderer<Object> {

    public NominalAxisRenderer(Canvas canvas, Axis<Object> axis, List<Plot<?, ?, ?>> plots) {
	super(canvas, axis, plots);
    }

    @Override
    protected double getLabelThickness() {
	Text text = new Text("WQ");
	text.setFont(AXIS_LABEL_FONT);
	text.applyCss();
	return text.getLayoutBounds().getHeight();
    }

    @Override
    protected void drawTicks(GraphicsContext gc, double x, double y, double width, double height) {
	// TODO Auto-generated method stub

    }

}
