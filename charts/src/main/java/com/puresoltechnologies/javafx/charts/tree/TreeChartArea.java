package com.puresoltechnologies.javafx.charts.tree;

import com.puresoltechnologies.javafx.charts.preferences.ChartsProperties;
import com.puresoltechnologies.javafx.extensions.fonts.FontDefinition;
import com.puresoltechnologies.javafx.preferences.Preferences;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TreeChartArea extends Canvas {

    protected static final ObjectProperty<Color> backgroundColor = Preferences
	    .getProperty(ChartsProperties.BACKGROUND_COLOR);
    protected static final ObjectProperty<Color> axisColor = Preferences.getProperty(ChartsProperties.AXIS_COLOR);
    protected static final ObjectProperty<FontDefinition> dataLabelFont = Preferences
	    .getProperty(ChartsProperties.DATA_LABEL_FONT);

    private TreeDataNode rootNode = null;

    public TreeChartArea() {
	super();
	widthProperty().addListener(event -> draw());
	heightProperty().addListener(event -> draw());
	draw();
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

    public double getLabelHeight(String name) {
	Text text = new Text(name);
	text.setFont(dataLabelFont.get().toFont());
	text.applyCss();
	return text.getLayoutBounds().getHeight();
    }

    private void draw() {
	clearPlotArea();
	if (rootNode != null) {
	    drawTree();
	}
    }

    private void drawTree() {
	double width = getWidth();
	double height = getHeight();
	double x = 0.0;
	double y = 0.0;
	drawNode(width, height, x, y, rootNode);
    }

    private void drawNode(double width, double height, double x, double y, TreeDataNode node) {
	GraphicsContext gc = getGraphicsContext2D();
	gc.setStroke(axisColor.get());

	gc.strokeRect(x, y, width, height);

	double labelHeight = getLabelHeight(node.getName());
	gc.strokeLine(x, y + 10.0 + labelHeight, width, y + 10.0 + labelHeight);

	gc.setFill(axisColor.get());
	gc.setFont(dataLabelFont.get().toFont());
	gc.setTextAlign(TextAlignment.LEFT);
	gc.setTextBaseline(VPos.BOTTOM);
	gc.fillText(node.getName(), x + 5.0, y + 5.0 + labelHeight);
    }

    private void clearPlotArea() {
	double width = getWidth();
	double height = getHeight();
	GraphicsContext gc = getGraphicsContext2D();
	gc.setFill(backgroundColor.get());
	gc.setStroke(backgroundColor.get());
	gc.fillRect(0.0, 0.0, width, height);
    }

    public void setData(TreeDataNode rootNode) {
	this.rootNode = rootNode;
	draw();
    }

}
