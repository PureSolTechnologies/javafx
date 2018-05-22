package com.puresoltechnologies.javafx.charts.meter;

import com.puresoltechnologies.javafx.utils.FXDefaultFonts;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class MeterView<T extends Number> extends GridPane {

    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty unit = new SimpleStringProperty();
    private final ObjectProperty<T> start = new SimpleObjectProperty<T>();
    private final ObjectProperty<T> end = new SimpleObjectProperty<T>();
    private final ObjectProperty<T> value = new SimpleObjectProperty<T>();

    private final Label titleLabel = new Label();
    private final MeterCanvas<T> meterCanvas = new MeterCanvas<>();
    private final Label legendLabel = new Label();

    public MeterView() {
	titleLabel.setFont(FXDefaultFonts.boldFont);
	titleLabel.textProperty().bind(title);
	setConstraints(titleLabel, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
	getChildren().add(titleLabel);

	meterCanvas.unitProperty().bind(unit);
	meterCanvas.startProperty().bind(start);
	meterCanvas.endProperty().bind(end);
	meterCanvas.valueProperty().bind(value);
	setConstraints(meterCanvas, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
	getChildren().add(meterCanvas);

	setConstraints(legendLabel, 0, 2, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
	getChildren().add(legendLabel);
    }

    public StringProperty titleProperty() {
	return title;
    }

    public String getTitle() {
	return title.get();
    }

    public void setTitle(String title) {
	this.title.set(title);
    }

    public StringProperty unitProperty() {
	return unit;
    }

    public String getUnit() {
	return unit.get();
    }

    public void setUnit(String unit) {
	this.unit.set(unit);
    }

    public ObjectProperty<T> startProperty() {
	return start;
    }

    public T getStart() {
	return start.get();
    }

    public void setStart(T start) {
	this.start.set(start);
    }

    public ObjectProperty<T> endProperty() {
	return end;
    }

    public T getEnd() {
	return end.get();
    }

    public void setEnd(T start) {
	this.end.set(start);
    }

    public ObjectProperty<T> valueProperty() {
	return value;
    }

    public T getValue() {
	return value.get();
    }

    public void setValue(T value) {
	this.value.set(value);
    }

}
