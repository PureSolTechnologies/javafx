package com.puresoltechnologies.javafx.charts.tree;

import javafx.scene.layout.BorderPane;

public class TreeChartView extends BorderPane {

    private final TreeChartArea area = new TreeChartArea();

    public TreeChartView() {
	super();
	setCenter(area);
    }

    public void setTreeData(TreeDataNode rootNode) {

    }
}
