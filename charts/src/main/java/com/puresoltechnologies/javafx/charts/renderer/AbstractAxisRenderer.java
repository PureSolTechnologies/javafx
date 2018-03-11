package com.puresoltechnologies.javafx.charts.renderer;

import com.puresoltechnologies.javafx.charts.plots.Axis;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class AbstractAxisRenderer<T> implements AxisRenderer {

    private static final double AXIS_THICKNESS = 10.0;
    private static final double AXIS_HALF_THICKNESS = AXIS_THICKNESS / 2.0;
    private static final Font AXIS_LABEL_FONT = Font.font("Verdana", FontWeight.NORMAL, 10.0);
    private static final Font AXIS_TITLE_FONT = Font.font("Verdana", FontWeight.MEDIUM, 16.0);

    private final Canvas canvas;
    private final Axis<T> axis;

    public AbstractAxisRenderer(Canvas canvas, Axis<T> axis) {
	super();
	this.canvas = canvas;
	this.axis = axis;
    }

    public final Axis<T> getAxis() {
	return axis;
    }

    public final Canvas getCanvas() {
	return canvas;
    }

    @Override
    public double getTickness() {
	double thickness = AXIS_THICKNESS;
	Text text = new Text("WQ");
	text.setFont(AXIS_LABEL_FONT);
	text.applyCss();
	thickness += text.getLayoutBounds().getHeight();
	text.setText(axis.getTitle());
	text.setFont(AXIS_TITLE_FONT);
	text.applyCss();
	thickness += text.getLayoutBounds().getHeight();
	return thickness;
    }

    @Override
    public void renderTo(double x, double y, double width, double height) {
	// TITLE
	Text titleText = new Text(axis.getTitle());
	titleText.setFont(AXIS_TITLE_FONT);
	titleText.applyCss();

	GraphicsContext gc = canvas.getGraphicsContext2D();
	gc.setFill(Color.LIGHTGRAY);
	gc.setStroke(Color.LIGHTGRAY);
	gc.fillRect(x, y, width, height);
	gc.setFill(Color.BLACK);
	gc.setStroke(Color.BLACK);
	String axisTitle = axis.getTitle();
	if (axis.getUnit() != null) {
	    axisTitle += " (" + axis.getUnit() + ")";
	}
	switch (axis.getAxisType()) {
	case X:
	    // Axis
	    gc.strokeLine(x, y, x + width, y);
	    // Title
	    gc.setFont(AXIS_TITLE_FONT);
	    gc.setTextAlign(TextAlignment.CENTER);
	    gc.setTextBaseline(VPos.TOP);
	    gc.strokeText(axisTitle, x + width / 2.0, y + height - titleText.getLayoutBounds().getHeight());
	    break;
	case ALT_X:
	    // Axis
	    gc.strokeLine(x, y + height, x + width, y + height);
	    // Title
	    gc.setFont(AXIS_TITLE_FONT);
	    gc.setTextAlign(TextAlignment.CENTER);
	    gc.setTextBaseline(VPos.TOP);
	    gc.strokeText(axisTitle, x + width / 2.0, y);
	    break;
	case Y:
	    // Axis
	    gc.strokeLine(x + width, y, x + width, y + height);
	    // Title
	    gc.rotate(-90);
	    gc.setFont(AXIS_TITLE_FONT);
	    gc.setTextAlign(TextAlignment.CENTER);
	    gc.setTextBaseline(VPos.TOP);
	    gc.strokeText(axisTitle, -canvas.getHeight() / 2.0, 0.0);
	    gc.rotate(90);
	    break;
	case ALT_Y:
	    // Axis
	    gc.strokeLine(x, y, x, y + height);
	    // Title
	    gc.rotate(-90);
	    gc.setFont(AXIS_TITLE_FONT);
	    gc.setTextAlign(TextAlignment.CENTER);
	    gc.setTextBaseline(VPos.TOP);
	    gc.strokeText(axisTitle, -canvas.getHeight() / 2.0,
		    canvas.getWidth() - titleText.getLayoutBounds().hashCode());
	    gc.rotate(90);
	    break;
	}
    }

}
