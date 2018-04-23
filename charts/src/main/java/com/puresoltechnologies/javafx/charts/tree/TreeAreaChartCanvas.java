package com.puresoltechnologies.javafx.charts.tree;

import java.util.List;
import java.util.Stack;

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

class TreeAreaChartCanvas<T extends TreeAreaChartNode> extends Canvas {

    protected static final ObjectProperty<Color> backgroundColor = Preferences
	    .getProperty(ChartsProperties.BACKGROUND_COLOR);
    protected static final ObjectProperty<Color> axisColor = Preferences.getProperty(ChartsProperties.AXIS_COLOR);
    protected static final ObjectProperty<FontDefinition> dataLabelFont = Preferences
	    .getProperty(ChartsProperties.DATA_LABEL_FONT);

    private T rootNode = null;
    private int depth = 1;

    public TreeAreaChartCanvas() {
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
	double x = 0.0;
	double y = 0.0;
	double width = getWidth();
	double height = getHeight();
	Stack<TreeAreaChartNode> stack = new Stack<>();
	drawNode(depth, x, y, width, height, rootNode, width > height, stack);
    }

    private void drawNode(int depth, double x, double y, double width, double height, TreeAreaChartNode dataNode,
	    boolean horizontal, Stack<TreeAreaChartNode> stack) {
	if (depth == 0) {
	    return;
	}
	if (stack.contains(dataNode)) {
	    return;
	}
	stack.push(dataNode);
	GraphicsContext gc = getGraphicsContext2D();
	gc.setStroke(axisColor.get());

	gc.strokeRect(x, y, width, height);

	double labelHeight = getLabelHeight(dataNode.getName());
	// gc.strokeLine(x, y + 10.0 + labelHeight, width, y + 10.0 + labelHeight);

	gc.setFill(axisColor.get());
	gc.setFont(dataLabelFont.get().toFont());
	gc.setTextAlign(TextAlignment.LEFT);
	gc.setTextBaseline(VPos.BOTTOM);
	gc.fillText(dataNode.getName(), x + 5.0, y + 5.0 + labelHeight);

	List<TreeAreaChartNode> children = dataNode.getChildren();
	double sum = 0.0;
	for (TreeAreaChartNode child : children) {
	    sum += child.getValue();
	}
	if (horizontal) {
	    double position = x + width * (dataNode.getValue() - sum) / dataNode.getValue();
	    for (TreeAreaChartNode child : children) {
		double step = width * (sum - child.getValue()) / sum;
		drawNode(depth - 1, position, y, step, height, child, !horizontal, stack);
		position += step;
	    }
	} else {
	    double position = y + height * (dataNode.getValue() - sum) / dataNode.getValue();
	    for (TreeAreaChartNode child : children) {
		double step = height * (sum - child.getValue()) / child.getValue();
		drawNode(depth - 1, x, position, width, step, child, !horizontal, stack);
		position += step;
	    }
	}
	stack.pop();
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
