package com.puresoltechnologies.javafx.charts.tree;

import java.awt.Point;

import com.puresoltechnologies.javafx.charts.preferences.ChartsProperties;
import com.puresoltechnologies.javafx.extensions.fonts.FontDefinition;
import com.puresoltechnologies.javafx.preferences.Preferences;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;

class TreeMapCanvas<T extends TreeMapNode> extends Canvas {

    protected static final ObjectProperty<Color> backgroundColor = Preferences
	    .getProperty(ChartsProperties.BACKGROUND_COLOR);
    protected static final ObjectProperty<Color> axisColor = Preferences.getProperty(ChartsProperties.AXIS_COLOR);
    protected static final ObjectProperty<FontDefinition> dataLabelFont = Preferences
	    .getProperty(ChartsProperties.DATA_LABEL_FONT);

    private final TreeMapRenderer<T> renderer;
    private T rootNode = null;
    private int depth = 1;
    private final Tooltip tooltip = new Tooltip();

    public TreeMapCanvas(TreeMapRenderer<T> renderer) {
	super();
	this.renderer = renderer;
	widthProperty().addListener(event -> draw());
	heightProperty().addListener(event -> draw());
	draw();
	Tooltip.install(this, tooltip);

	tooltip.setOnShowing(windowEvent -> {// called just prior to being shown
	    Point mouse = java.awt.MouseInfo.getPointerInfo().getLocation();
	    Point2D local = TreeMapCanvas.this.screenToLocal(mouse.x, mouse.y);

	    // my app-specific code to get the chart's yaxis value
	    // then set the text as I want
	    // double pitch = yaxis.getValueForDisplay(local.getY()).doubleValue();
	    // double freq = AudioUtil.pitch2frequency(pitch);
	    // t.setText(String.format("Pitch %.1f: %.1f Hz %.1f samples", pitch, freq,
	    // audio.rate / freq));
	});
    }

    @Override
    public double minHeight(double height) {
	return 200;
    }

    @Override
    public double maxHeight(double height) {
	return 10000;
    }

    @Override
    public double prefHeight(double height) {
	return minHeight(height);
    }

    @Override
    public double minWidth(double width) {
	return 320;
    }

    @Override
    public double maxWidth(double width) {
	return 10000;
    }

    @Override
    public double prefWidth(double width) {
	return minWidth(width);
    }

    @Override
    public boolean isResizable() {
	return true;
    }

    @Override
    public void resize(double width, double height) {
	super.resize(width, height);
	setWidth(width);
	setHeight(height);
	draw();
    }

    private void draw() {
	clearPlotArea();
	if (rootNode != null) {
	    double x = 0.0;
	    double y = 0.0;
	    double width = getWidth();
	    double height = getHeight();
	    renderer.drawMap(this, depth, x, y, width, height, rootNode);
	}
    }

    private void clearPlotArea() {
	double width = getWidth();
	double height = getHeight();
	GraphicsContext gc = getGraphicsContext2D();
	gc.setFill(backgroundColor.get());
	gc.setStroke(backgroundColor.get());
	gc.fillRect(0.0, 0.0, width, height);
    }

    public void setData(T rootNode) {
	this.rootNode = rootNode;
	draw();
    }

    public void setDepthTest(int depth) {
	this.depth = depth;
	draw();
    }

}
