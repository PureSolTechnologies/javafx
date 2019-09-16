package com.puresoltechnologies.javafx.charts;

import com.puresoltechnologies.javafx.charts.plots.Plot;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class LegendTable extends TableView<Plot<?, ?, ?>> {

    private final ObservableList<Plot<?, ?, ?>> plots;

    public LegendTable(ObservableList<Plot<?, ?, ?>> plots) {
	this.plots = plots;
	TableColumn<Plot<?, ?, ?>, Plot<?, ?, ?>> markerColumn = new TableColumn<>("Marker");
	markerColumn.setCellValueFactory(plot -> new SimpleObjectProperty<>(plot.getValue()));
	markerColumn.setCellFactory(column -> new TableCell<>() {

	    @Override
	    protected void updateItem(Plot<?, ?, ?> plot, boolean empty) {
		super.updateItem(plot, empty);
		setText(null);
		if ((plot == null) || empty) {
		    setGraphic(null);
		} else {
		    MarkerCanvas canvas = new MarkerCanvas(this, plot);

		    setGraphic(canvas);
		}
	    }
	});
	getColumns().add(markerColumn);

	TableColumn<Plot<?, ?, ?>, String> nameColumn = new TableColumn<>("Name");
	nameColumn.setCellValueFactory(plot -> new SimpleStringProperty(plot.getValue().getTitle()));
	getColumns().add(nameColumn);

	setItems(plots);
	setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	setFixedCellSize(25);
	prefHeightProperty().bind(fixedCellSizeProperty().multiply(Bindings.size(getItems()).add(0.1)));
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
