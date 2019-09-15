package com.puresoltechnologies.javafx.charts.dialogs;

import java.util.List;

import com.puresoltechnologies.javafx.charts.ChartView;
import com.puresoltechnologies.javafx.charts.plots.Plot;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

/**
 * This class provides the chart properties dialog to configure the chart which
 * was provided in the constructor.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public class ChartPropertiesDialog extends Dialog<Void> {

    private final ChartView chartView;

    private final TreeView<String> chartElements = new TreeView<>();
    private final List<Plot<?, ?, ?>> plots;

    public ChartPropertiesDialog(ChartView chartView, List<Plot<?, ?, ?>> plots) {
	super();
	this.chartView = chartView;
	this.plots = plots;
	setTitle(chartView.getTitle() + " Chart Properties");
	setHeaderText("Configure the chart, its axes and plots.");
	setResizable(true);

	DialogPane dialogPane = getDialogPane();
	dialogPane.getButtonTypes().addAll(ButtonType.CLOSE);

	BorderPane borderPane = new BorderPane();
	borderPane.setLeft(chartElements);
	borderPane.setCenter(new Label("Select a chart element to configure."));
	dialogPane.setContent(borderPane);

	createTree();
    }

    private void createTree() {
	TreeItem<String> rootItem = new TreeItem<>("Chart");
	rootItem.setExpanded(true);

	TreeItem<String> axesItem = new TreeItem<>("Axes");
	axesItem.setExpanded(true);

	TreeItem<String> plotsItem = new TreeItem<>("Plots");
	plotsItem.setExpanded(true);

	for (Plot<?, ?, ?> plot : plots) {
	    TreeItem<String> plotItem = new TreeItem<>(plot.getTitle());
	    plotsItem.getChildren().add(plotItem);
	}

	rootItem.getChildren().addAll(axesItem, plotsItem);

	chartElements.setRoot(rootItem);
    }

}
