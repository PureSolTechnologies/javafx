package com.puresoltechnologies.javafx.charts.tree;

import javafx.scene.layout.BorderPane;

public class TreeMapView<T extends TreeMapNode> extends BorderPane {

    private final TreeMapCanvas<T> area = new TreeMapCanvas<>(new SquarifiedTreeMapRenderer<>());

    public TreeMapView() {
	super();
	setCenter(area);
    }

    public void setTreeData(T rootNode) {
	area.setData(rootNode);
    }

    public void setDepth(int depth) {
	area.setDepthTest(depth);
    }
}
