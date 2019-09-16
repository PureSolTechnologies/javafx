package com.puresoltechnologies.javafx.charts;

import com.puresoltechnologies.javafx.charts.plots.InterpolationType;
import com.puresoltechnologies.javafx.charts.plots.MarkerType;
import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.plots.PointBasedPlot;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;

public class MarkerCanvas extends Canvas {

    private final Plot<?, ?, ?> plot;

    public MarkerCanvas(TableCell<Plot<?, ?, ?>, Plot<?, ?, ?>> tableCell, Plot<?, ?, ?> plot) {
	this.plot = plot;
	tableCell.widthProperty().addListener((o, oldValue, newValue) -> {
	    setWidth(newValue.doubleValue() - tableCell.getInsets().getLeft() - tableCell.getInsets().getRight());
	});
	tableCell.heightProperty().addListener((o, oldValue, newValue) -> {
	    setHeight(newValue.doubleValue() - tableCell.getInsets().getTop() - tableCell.getInsets().getBottom());
	});

	widthProperty().addListener(event -> draw());
	heightProperty().addListener(event -> draw());
    }

    private void draw() {
	double width = getWidth();
	double height = getHeight();
	if ((width > 0.0) && (height > 0.0)) {
	    Color color = plot.getColor();
	    GraphicsContext gc = getGraphicsContext2D();

	    if (PointBasedPlot.class.isAssignableFrom(plot.getClass())) {
		PointBasedPlot<?, ?, ?> pointBasedPlot = (PointBasedPlot<?, ?, ?>) plot;
		double size = pointBasedPlot.getMarkerSize();
		double x = (width - size) / 2.0;
		double y = (height - size) / 2.0;
		gc.setFill(Color.WHITE);
		gc.setStroke(Color.WHITE);
		gc.fillRect(0, 0, width, height);
		gc.setFill(color);
		gc.setStroke(color);
		MarkerType markerType = pointBasedPlot.getMarkerType();
		markerType.renderTo(this, x, y, size, size);
		if (pointBasedPlot.getInterpolationType() != InterpolationType.NONE) {
		    gc.setGlobalAlpha(0.2);
		    gc.strokeLine(0.0, height / 2.0, width, height / 2.0);
		    gc.setGlobalAlpha(1.0);
		}
	    } else {
		gc.setFill(color);
		gc.setStroke(color);
		gc.fillRect(0, 0, width, height);
	    }
	}
    }

    @Override
    public double minHeight(double height) {
	return 16;
    }

    @Override
    public double maxHeight(double height) {
	return Double.MAX_VALUE;
    }

    @Override
    public double prefHeight(double height) {
	return 16;
    }

    @Override
    public double minWidth(double width) {
	return 48;
    }

    @Override
    public double maxWidth(double width) {
	return Double.MAX_VALUE;
    }

    @Override
    public double prefWidth(double width) {
	return 48;
    }

    @Override
    public boolean isResizable() {
	return true;
    }
}
