package com.puresoltechnologies.javafx.charts.tree;

import javafx.scene.layout.BorderPane;

public class TreeAreaChartView<T extends TreeAreaChartNode> extends BorderPane {

    private final TreeAreaChartCanvas<T> area = new TreeAreaChartCanvas<>();

    public TreeAreaChartView() {
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
