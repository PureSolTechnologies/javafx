package com.puresoltechnologies.javafx.charts.tree;

import java.util.List;
import java.util.Stack;

import com.puresoltechnologies.javafx.charts.preferences.ChartsProperties;
import com.puresoltechnologies.javafx.extensions.fonts.FontDefinition;
import com.puresoltechnologies.javafx.preferences.Preferences;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SquarifiedTreeMapRenderer<T extends TreeMapNode> implements TreeMapRenderer<T> {

    protected static final ObjectProperty<Color> axisColor = Preferences.getProperty(ChartsProperties.AXIS_COLOR);
    protected static final ObjectProperty<FontDefinition> dataLabelFont = Preferences
	    .getProperty(ChartsProperties.DATA_LABEL_FONT);

    @Override
    public void drawMap(TreeMapCanvas<T> canvas, int depth, double x, double y, double width, double height,
	    T rootNode) {
	Stack<TreeMapNode> stack = new Stack<>();
	drawNode(canvas, depth, x, y, width, height, rootNode, stack);
    }

    private double getLabelHeight(String name) {
	Text text = new Text(name);
	text.setFont(dataLabelFont.get().toFont());
	text.applyCss();
	return text.getLayoutBounds().getHeight();
    }

    private void drawNode(TreeMapCanvas<T> canvas, int depth, double x, double y, double width, double height,
	    TreeMapNode node, Stack<TreeMapNode> stack) {
	if ((depth == 0) || (stack.contains(node))) {
	    return;
	}
	drawBox(canvas, x, y, width, height, node);

	stack.push(node);
	double sum = calcSum(node);
	List<TreeMapNode> children = node.getChildren();
	if (width > height) {
	    double position = x + width * (node.getValue() - sum) / node.getValue();
	    for (TreeMapNode child : children) {
		double step = width * (sum - child.getValue()) / node.getValue();
		drawNode(canvas, depth - 1, position, y, step, height, child, stack);
		position += step;
	    }
	} else {
	    double position = y + height * (node.getValue() - sum) / node.getValue();
	    for (TreeMapNode child : children) {
		double step = height * (sum - child.getValue()) / node.getValue();
		drawNode(canvas, depth - 1, x, position, width, step, child, stack);
		position += step;
	    }
	}
	stack.pop();
    }

    private void drawBox(TreeMapCanvas<T> canvas, double x, double y, double width, double height,
	    TreeMapNode node) {
	GraphicsContext gc = canvas.getGraphicsContext2D();
	gc.setStroke(axisColor.get());

	gc.strokeRect(x, y, width, height);

	double labelHeight = getLabelHeight(node.getName());
	gc.strokeLine(x, y + 10.0 + labelHeight, x + width, y + 10.0 + labelHeight);
	gc.strokeLine(x, y, x + width, y + height);
	gc.strokeLine(x, y + height, x + width, y);

	gc.setFill(axisColor.get());
	gc.setFont(dataLabelFont.get().toFont());
	gc.setTextAlign(TextAlignment.LEFT);
	gc.setTextBaseline(VPos.BOTTOM);
	gc.fillText(node.getName(), x + 5.0, y + 5.0 + labelHeight);
    }

    private double calcSum(TreeMapNode dataNode) {
	double sum = 0.0;
	for (TreeMapNode child : dataNode.getChildren()) {
	    sum += child.getValue();
	}
	return sum;
    }

}
