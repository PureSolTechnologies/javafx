package com.puresoltechnologies.javafx.charts.tree;

import java.util.ArrayList;
import java.util.Collections;
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
	double labelHeight = getLabelHeight(node.getName());
	stack.push(node);
	drawBox(canvas, x, y, width, height, node);
	List<TreeMapNode> children = node.getChildren();
	Collections.sort(children, (l, r) -> -Double.valueOf(l.getValue()).compareTo(r.getValue()));
	double nodeSum = calcSum(children);
	squarify(canvas, depth, x, y + 10.0 + labelHeight, width, height - 10.0 - labelHeight, children, nodeSum, stack,
		new ArrayList<>());
	stack.pop();
    }

    private void squarify(TreeMapCanvas<T> canvas, int depth, double x, double y, double width, double height,
	    List<TreeMapNode> children, double nodeSum, Stack<TreeMapNode> stack, List<TreeMapNode> row) {
	if (children.size() == 0) {
	    layoutRow(canvas, depth, x, y, width, height, row, stack, nodeSum);
	    return;
	}
	double rowWidth = Math.max(width, height);
	TreeMapNode c = children.get(0);
	if (worst(row, null, rowWidth) <= worst(row, c, rowWidth)) {
	    children.remove(0);
	    row.add(c);
	    squarify(canvas, depth, x, y, width, height, children, nodeSum, stack, row);
	} else {
	    if (width > height) {
		layoutRow(canvas, depth, x, y, rowWidth, height, row, stack, nodeSum);
		squarify(canvas, depth, x + rowWidth, y, width - rowWidth, height, children, nodeSum, stack,
			new ArrayList<>());
	    } else {
		layoutRow(canvas, depth, x, y, width, rowWidth, row, stack, nodeSum);
		squarify(canvas, depth, x, y + rowWidth, width, height - rowWidth, children, nodeSum, stack,
			new ArrayList<>());
	    }
	}
    }

    private void layoutRow(TreeMapCanvas<T> canvas, int depth, double x, double y, double width, double height,
	    List<TreeMapNode> row, Stack<TreeMapNode> stack, double nodeSum) {
	double rowSum = calcSum(row);
	if (width > height) {
	    double rowWidth = width * rowSum / nodeSum;
	    double position = 0;
	    for (TreeMapNode child : row) {
		double step = height * (child.getValue()) / rowSum;
		drawNode(canvas, depth - 1, x, position, rowWidth, step, child, stack);
		position += step;
	    }
	} else {
	    double rowWidth = height * rowSum / nodeSum;
	    double position = 0;
	    for (TreeMapNode child : row) {
		double step = width * (child.getValue() / rowSum);
		drawNode(canvas, depth - 1, position, y, step, rowWidth, child, stack);
		position += step;
	    }
	}
    }

    private double worst(List<TreeMapNode> row, TreeMapNode r, double rowWidth) {
	double worst = 0.0;
	double rowSum = calcSum(row);
	if (r != null) {
	    worst = Math.max(rowWidth * rowWidth * r.getValue() / (rowSum * rowSum),
		    rowSum * rowSum / (rowWidth * rowWidth * r.getValue()));
	}
	for (TreeMapNode child : row) {
	    double current = Math.max(rowWidth * rowWidth * child.getValue() / (rowSum * rowSum),
		    rowSum * rowSum / (rowWidth * rowWidth * child.getValue()));
	    worst = Math.max(current, worst);
	}
	return worst;
    }

    private void drawBox(TreeMapCanvas<T> canvas, double x, double y, double width, double height, TreeMapNode node) {
	GraphicsContext gc = canvas.getGraphicsContext2D();
	gc.setStroke(axisColor.get());

	gc.setFill(Color.WHITE);
	gc.fillRect(x, y, width, height);
	gc.strokeRect(x, y, width, height);

	double labelHeight = getLabelHeight(node.getName());
	gc.setFill(Color.AQUAMARINE);
	gc.fillRect(x, y, width, 10.0 + labelHeight);
	gc.strokeRect(x, y, width, 10.0 + labelHeight);

	gc.setFill(axisColor.get());
	gc.setFont(dataLabelFont.get().toFont());
	gc.setTextAlign(TextAlignment.LEFT);
	gc.setTextBaseline(VPos.BOTTOM);
	gc.fillText(node.getName(), x + 5.0, y + 5.0 + labelHeight);
    }

    private double calcSum(List<TreeMapNode> children) {
	double sum = 0.0;
	for (TreeMapNode child : children) {
	    sum += child.getValue();
	}
	return sum;
    }

}
