package com.puresoltechnologies.javafx.charts.tree;

/**
 * There are a lot of different tree map layout algorithms which have all their
 * advantages and disadvantages for different kinds of trees. To be flexible,
 * this interface is provided to exchange the tree map renderer to support
 * different kinds of tree maps.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public interface TreeMapRenderer<T extends TreeMapNode> {

    /**
     * This method is used to renderer the map.
     */
    public void drawMap(TreeMapCanvas<T> canvas, int depth, double x, double y, double width, double height,
	    T rootNode);

}
