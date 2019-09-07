package com.puresoltechnologies.javafx.charts.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import com.puresoltechnologies.javafx.charts.preferences.ChartsProperties;
import com.puresoltechnologies.javafx.extensions.fonts.FontDefinition;
import com.puresoltechnologies.javafx.preferences.Preferences;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SquarifiedTreeMapRenderer<T extends TreeMapNode> implements TreeMapRenderer<T> {

    protected static final ObjectProperty<FontDefinition> dataLabelFont = Preferences
	    .getProperty(ChartsProperties.DATA_LABEL_FONT);

    protected final ObjectProperty<Color> axisColor = new SimpleObjectProperty<>(Color.BLACK);

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
	stack.push(node);
	double titleHeight = drawBox(canvas, x, y, width, height, node);
	List<TreeMapNode> children = node.getChildren();
	Collections.sort(children, (l, r) -> Double.compare(l.getValue(), r.getValue()) * -1);
	squarify(canvas, depth, x, y + titleHeight, width, height - titleHeight, children, stack, new ArrayList<>());
	stack.pop();
    }

    private void squarify(TreeMapCanvas<T> canvas, int depth, double x, double y, double width, double height,
	    List<TreeMapNode> children, Stack<TreeMapNode> stack, List<TreeMapNode> row) {
	double restSum = calcSum(children);
	double rowLength = Math.min(width, height);
	double rowWidth = calcRowWidth(row, children, rowLength, width, height);
	if (children.size() == 0) {
	    if (width > height) {
		layoutRowVertical(canvas, depth, x, y, rowWidth, height, row, stack, restSum);
	    } else {
		layoutRowHorizontal(canvas, depth, x, y, width, rowWidth, row, stack, restSum);
	    }
	    return;
	}
	TreeMapNode c = children.get(0);
	if ((row.size() == 0) //
		|| (worst(row, null, rowLength, restSum, width * height) >= worst(row, c, rowLength, restSum,
			width * height))) {
	    children.remove(0);
	    row.add(c);
	    squarify(canvas, depth, x, y, width, height, children, stack, row);
	} else {
	    if (width > height) {
		layoutRowVertical(canvas, depth, x, y, rowWidth, height, row, stack, restSum);
		squarify(canvas, depth, x + rowWidth, y, width - rowWidth, height, children, stack, new ArrayList<>());
	    } else {
		layoutRowHorizontal(canvas, depth, x, y, width, rowWidth, row, stack, restSum);
		squarify(canvas, depth, x, y + rowWidth, width, height - rowWidth, children, stack, new ArrayList<>());
	    }
	}
    }

    private double calcRowWidth(List<TreeMapNode> row, List<TreeMapNode> children, double rowLength, double width,
	    double height) {
	double rowSum = calcSum(row);
	double childrenSum = calcSum(children);
	return (width * height * rowSum) / ((childrenSum + rowSum) * rowLength);
    }

    private void layoutRowHorizontal(TreeMapCanvas<T> canvas, int depth, double x, double y, double width,
	    double height, List<TreeMapNode> rowEntries, Stack<TreeMapNode> stack, double nodeSum) {
	double rowSum = calcSum(rowEntries);
	double position = x;
	for (TreeMapNode rowEntry : rowEntries) {
	    double step = width * (rowEntry.getValue() / rowSum);
	    drawNode(canvas, depth - 1, position, y, step, height, rowEntry, stack);
	    position += step;
	}
    }

    private void layoutRowVertical(TreeMapCanvas<T> canvas, int depth, double x, double y, double width, double height,
	    List<TreeMapNode> rowEntries, Stack<TreeMapNode> stack, double nodeSum) {
	double rowSum = calcSum(rowEntries);
	double position = y;
	for (TreeMapNode rowEntry : rowEntries) {
	    double step = (height * (rowEntry.getValue())) / rowSum;
	    drawNode(canvas, depth - 1, x, position, width, step, rowEntry, stack);
	    position += step;
	}
    }

    private double worst(List<TreeMapNode> row, TreeMapNode r, double rowLength, double restSum, double totalArea) {
	double worst = 0.0;
	double rowSum = calcSum(row);
	double rowWidth = (totalArea * rowSum) / ((restSum + rowSum) * rowLength);
	if (r != null) {
	    double elementLength = (totalArea * r.getValue()) / ((restSum + rowSum + r.getValue()) / rowLength);
	    worst = Math.max(rowWidth / elementLength, elementLength / rowWidth);
	}
	for (TreeMapNode child : row) {
	    double elementLength = (totalArea * child.getValue())
		    / ((restSum + rowSum + (r != null ? r.getValue() : 0.0)) * rowLength);
	    double current = Math.max(rowWidth / elementLength, elementLength / rowWidth);
	    worst = Math.max(current, worst);
	}
	return worst;
    }

    private double drawBox(TreeMapCanvas<T> canvas, double x, double y, double width, double height, TreeMapNode node) {
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

	return labelHeight + 10.0;
    }

    private double calcSum(List<TreeMapNode> children) {
	double sum = 0.0;
	for (TreeMapNode child : children) {
	    sum += child.getValue();
	}
	return sum;
    }

}
