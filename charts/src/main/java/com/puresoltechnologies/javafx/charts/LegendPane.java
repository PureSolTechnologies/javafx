package com.puresoltechnologies.javafx.charts;

import com.puresoltechnologies.javafx.charts.plots.Plot;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class LegendPane extends TableView<Plot<?, ?, ?>> {

    public LegendPane(ObservableList<Plot<?, ?, ?>> plots) {
	TableColumn<Plot<?, ?, ?>, String> markerColumn = new TableColumn<>("Marker");
	markerColumn.setCellValueFactory(plot -> plot.getValue().titleProperty());
	markerColumn.setCellFactory(column -> new TableCell<>() {

	    @Override
	    protected void updateItem(String title, boolean empty) {
		super.updateItem(title, empty);
		if ((title == null) || empty) {
		    setText(null);
		    setGraphic(null);
		} else {
		    ObservableList<Plot<?, ?, ?>> items = LegendPane.this.getItems();
		    Plot<?, ?, ?> plot = items.get(getIndex());
		    Pane pane = new Pane();
		    MarkerCanvas canvas = new MarkerCanvas(pane, plot);
		    pane.getChildren().add(canvas);
		    pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		    setGraphic(pane);
		}
	    }
	});
	getColumns().add(markerColumn);

	TableColumn<Plot<?, ?, ?>, String> nameColumn = new TableColumn<>("Name");
	nameColumn.setCellValueFactory(plot -> plot.getValue().titleProperty());
	getColumns().add(nameColumn);

	setItems(plots);
	setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	setFixedCellSize(25);
	prefHeightProperty().bind(fixedCellSizeProperty().multiply(Bindings.size(getItems()).add(1.1)));
	minHeightProperty().bind(prefHeightProperty());
	maxHeightProperty().bind(prefHeightProperty());
	// The header are hidden as next step...
	widthProperty().addListener((ChangeListener<Number>) (source, oldWidth, newWidth) -> {
	    Pane header = (Pane) lookup("TableHeaderRow");
	    if (header.isVisible()) {
		header.setMaxHeight(0);
		header.setMinHeight(0);
		header.setPrefHeight(0);
		header.setVisible(false);
	    }
	});
    }

}
