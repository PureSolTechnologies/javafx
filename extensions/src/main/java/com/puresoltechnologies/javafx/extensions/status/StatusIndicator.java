package com.puresoltechnologies.javafx.extensions.status;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;

public class StatusIndicator<T> extends Label {

    private final ObjectProperty<T> statusProperty = new SimpleObjectProperty<>();
    private final StatusRenderer<T> renderer;
    private final Canvas canvas = new Canvas();

    public StatusIndicator(StatusRenderer<T> renderer) {
	this.renderer = renderer;
	setMinHeight(16.0);
	setMinWidth(16.0);
	setMaxHeight(Double.MAX_VALUE);
	setMaxWidth(Double.MAX_VALUE);

	setContentDisplay(ContentDisplay.LEFT);
	Canvas canvas = new Canvas();
	canvas.widthProperty().bind(heightProperty());
	canvas.heightProperty().bind(heightProperty());
	canvas.setVisible(true);
	setGraphic(canvas);

	statusProperty.addListener((observable, oldValue, newValue) -> rerender());

	widthProperty().addListener(event -> rerender());
	heightProperty().addListener(event -> rerender());
	rerender();
    }

    public StatusIndicator(String text, StatusRenderer<T> renderer) {
	this.renderer = renderer;
	Canvas canvas = new Canvas();
	setGraphic(canvas);
    }

    public ObjectProperty<T> statusProperty() {
	return statusProperty;
    }

    public T getStatus() {
	return statusProperty.getValue();
    }

    public void setStatus(T status) {
	statusProperty.setValue(status);
    }

    private void rerender() {
	renderer.render(this, statusProperty.getValue());
    }
}
