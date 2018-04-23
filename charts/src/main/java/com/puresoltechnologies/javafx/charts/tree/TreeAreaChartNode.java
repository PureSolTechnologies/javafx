package com.puresoltechnologies.javafx.charts.tree;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.puresoltechnologies.graphs.trees.TreeLink;
import com.puresoltechnologies.graphs.trees.TreeNode;

/**
 * This interface is used to specify the tree area chart.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public interface TreeAreaChartNode extends TreeNode<TreeAreaChartNode> {

    @Override
    public String getName();

    /**
     * Returns the value to be applied to the area.
     * 
     * @return
     */
    public double getValue();

    @Override
    public default Set<TreeLink<TreeAreaChartNode>> getEdges() {
	Set<TreeLink<TreeAreaChartNode>> edges = new HashSet<>();
	getChildren().forEach(child -> edges.add(new TreeLink<TreeAreaChartNode>(this, child)));
	return edges;
    }

    @Override
    public TreeAreaChartNode getParent();

    @Override
    public default boolean hasChildren() {
	return !getChildren().isEmpty();
    }

    @Override
    public List<TreeAreaChartNode> getChildren();

}
