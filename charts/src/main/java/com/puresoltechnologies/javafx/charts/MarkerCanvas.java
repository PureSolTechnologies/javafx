package com.puresoltechnologies.javafx.charts;

import com.puresoltechnologies.javafx.charts.plots.InterpolationType;
import com.puresoltechnologies.javafx.charts.plots.LinePlot;
import com.puresoltechnologies.javafx.charts.plots.MarkerType;
import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.plots.PointBasedPlot;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class MarkerCanvas extends Canvas {

    private final ObjectProperty<Plot<?, ?, ?>> plot = new SimpleObjectProperty<>();

    public MarkerCanvas(Region region, Plot<?, ?, ?> plot) {
	this(region);
	this.plot.setValue(plot);
    }

    public MarkerCanvas(Region region) {
	region.widthProperty().addListener((o, oldValue, newValue) -> {
	    setWidth(newValue.doubleValue() - region.getInsets().getLeft() - region.getInsets().getRight());
	});
	region.heightProperty().addListener((o, oldValue, newValue) -> {
	    setHeight(newValue.doubleValue() - region.getInsets().getTop() - region.getInsets().getBottom());
	});

	widthProperty().addListener(event -> draw());
	heightProperty().addListener(event -> draw());
    }

    private void draw() {
	double width = getWidth();
	double height = getHeight();
	Plot<?, ?, ?> plot = this.plot.getValue();
	if ((width > 0.0) && (height > 0.0) && (plot != null)) {
	    Color color = plot.getColor();
	    GraphicsContext gc = getGraphicsContext2D();

	    if (PointBasedPlot.class.isAssignableFrom(plot.getClass())) {
		PointBasedPlot<?, ?, ?> pointBasedPlot = (PointBasedPlot<?, ?, ?>) plot;
		double size = pointBasedPlot.getMarkerSize();
		double x = (width - size) / 2.0;
		double y = (height - size) / 2.0;
		gc.clearRect(0, 0, width, height);
		if (LinePlot.class.isAssignableFrom(plot.getClass())) {
		    LinePlot<?, ?, ?> linePlot = (LinePlot<?, ?, ?>) plot;
		    if (pointBasedPlot.getInterpolationType() != InterpolationType.NONE) {
			gc.setFill(linePlot.getLineColor());
			gc.setStroke(linePlot.getLineColor());
			gc.setLineWidth(linePlot.getLineWidth());
			gc.setGlobalAlpha(linePlot.getLineAlpha());
			gc.strokeLine(0.0, height / 2.0, width, height / 2.0);
			gc.setGlobalAlpha(1.0);
		    }
		}
		gc.setFill(color);
		gc.setStroke(color);
		MarkerType markerType = pointBasedPlot.getMarkerType();
		markerType.renderTo(this, x, y, size, size);
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

    public void setPlot(Plot<?, ?, ?> plot) {
	this.plot.setValue(plot);
    }
}
