package com.puresoltechnologies.javafx.charts;

import com.puresoltechnologies.javafx.charts.plots.Plot;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;

public class LegendPane extends TilePane {

    private final ChartView chartView;

    public LegendPane(ChartView chartView, ObservableList<Plot<?, ?, ?>> plots) {
	this.chartView = chartView;
	setPadding(new Insets(5.0));
	setHgap(5.0);
	setVgap(5.0);
	plots.addListener((ListChangeListener<Plot<?, ?, ?>>) (change) -> {
	    showSymbols(change.getList());
	});
	chartView.backgroundColorProperty().addListener((observable, oldValue, newValue) -> {
	    updateBackground();
	});
	updateBackground();
    }

    private void updateBackground() {
	setBackground(
		new Background(new BackgroundFill(chartView.getBackgroundColor(), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void showSymbols(ObservableList<? extends Plot<?, ?, ?>> plots) {
	getChildren().clear();
	plots.forEach(plot -> showSymbol(plot));
    }

    private void showSymbol(Plot<?, ?, ?> plot) {
	GridPane pane = new GridPane();

	Pane symbolPane = new Pane();
	MarkerCanvas canvas = new MarkerCanvas(symbolPane, plot);
	symbolPane.getChildren().add(canvas);
	symbolPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

	Label label = new Label(plot.getTitle());

	GridPane.setConstraints(symbolPane, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES, Priority.NEVER);
	GridPane.setConstraints(label, 1, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES, Priority.NEVER);
	pane.getChildren().addAll(symbolPane, label);

	getChildren().add(pane);
    }

}
