package com.puresoltechnologies.javafx.charts.meter;

import com.puresoltechnologies.javafx.utils.FXDefaultFonts;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class MeterCanvas<T extends Number> extends Canvas {

    private final StringProperty unit = new SimpleStringProperty();
    private final ObjectProperty<T> start = new SimpleObjectProperty<T>();
    private final ObjectProperty<T> end = new SimpleObjectProperty<T>();
    private final ObjectProperty<T> value = new SimpleObjectProperty<T>();

    public MeterCanvas() {
	super();
	widthProperty().addListener(event -> redraw());
	heightProperty().addListener(event -> redraw());
	unit.addListener((observable, oldValue, newValue) -> redraw());
	start.addListener((observable, oldValue, newValue) -> redraw());
	end.addListener((observable, oldValue, newValue) -> redraw());
	value.addListener((observable, oldValue, newValue) -> redraw());
	draw();
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

    @Override
    public double minHeight(double height) {
	return 100;
    }

    @Override
    public double maxHeight(double height) {
	return 10000;
    }

    @Override
    public double prefHeight(double height) {
	return minHeight(height);
    }

    @Override
    public double minWidth(double width) {
	return 200;
    }

    @Override
    public double maxWidth(double width) {
	return 10000;
    }

    @Override
    public double prefWidth(double width) {
	return minWidth(width);
    }

    @Override
    public boolean isResizable() {
	return true;
    }

    @Override
    public void resize(double width, double height) {
	super.resize(width, height);
	setWidth(width);
	setHeight(height);
	redraw();
    }

    private void redraw() {
	clear();
	draw();
    }

    private void clear() {
	GraphicsContext gc = getGraphicsContext2D();
	gc.setFill(Color.WHITE);
	gc.fillRect(0.0, 0.0, getWidth(), getHeight());
    }

    private void draw() {
	GraphicsContext gc = getGraphicsContext2D();
	double width = getWidth();
	double height = getHeight();
	double diameter = Math.min(width / 2.0, height) * 2.0;
	double centerKnobDiameter = diameter / 10.0;
	double scaleDiameter = diameter * 0.9;
	double left = (width - diameter) / 2.0;
	double top = height - diameter / 2.0;
	// Draw background
	gc.setLineWidth(1.0);
	gc.setStroke(Color.DARKGRAY);
	gc.setFill(Color.LIGHTGREY);
	gc.fillArc(left, top, diameter, diameter, 0, 180, ArcType.CHORD);
	gc.strokeArc(left, top, diameter, diameter, 0, 180, ArcType.CHORD);

	// Draw Unit
	gc.setLineWidth(1.0);
	gc.setFill(Color.BLACK);
	gc.setFont(Font.font(FXDefaultFonts.defaultFamily, FontWeight.BLACK, FontPosture.REGULAR, diameter / 20.0));
	gc.setTextBaseline(VPos.CENTER);
	gc.setTextAlign(TextAlignment.CENTER);
	gc.fillText(unit.get(), left + diameter / 2.0, top + diameter / 4.0);

	// Draw handle
	if (value.get() != null) {
	    gc.setFill(Color.BLACK);
	    gc.setTransform(1.0, 0.0, 0.0, 1.0, width / 2.0, height);
	    gc.transform(1.0, 0.0, 0.0, -1.0, 0.0, 0.0);

	    double rad = Math.PI * (1 - (value.get().doubleValue() - start.get().doubleValue())
		    / (end.get().doubleValue() - start.get().doubleValue()));
	    gc.transform(Math.cos(rad), Math.sin(rad), -Math.sin(rad), Math.cos(rad), 0.0, 0.0);
	    gc.fillPolygon(new double[] { 0, scaleDiameter * 0.45, 0 },
		    new double[] { centerKnobDiameter / 4.0, 0, -centerKnobDiameter / 4.0 }, 3);

	    gc.setTransform(1.0, 0.0, 0.0, 1.0, 0.0, 0.0);
	}
	// Draw center knob
	gc.setFill(Color.DARKGRAY);
	gc.fillArc(left + (diameter - centerKnobDiameter) / 2.0, top + (diameter - centerKnobDiameter) / 2.0,
		centerKnobDiameter, centerKnobDiameter, 0, 180, ArcType.CHORD);

	drawScale(gc, width, height, diameter, scaleDiameter, left, top);
    }

    private void drawScale(GraphicsContext gc, double width, double height, double diameter, double scaleDiameter,
	    double left, double top) {
	// Draw scale
	gc.setStroke(Color.SEAGREEN);
	gc.setFill(Color.SEAGREEN);
	gc.setLineWidth(10.0);
	gc.strokeArc(left + (diameter - scaleDiameter) / 2.0, top + (diameter - scaleDiameter) / 2.0, scaleDiameter,
		scaleDiameter, 0, 180, ArcType.OPEN);

	// Start and end labels
	gc.setFont(Font.font(FXDefaultFonts.defaultFamily, FontWeight.NORMAL, FontPosture.REGULAR, diameter / 20.0));
	gc.setTextBaseline(VPos.BOTTOM);
	gc.setTextAlign(TextAlignment.LEFT);
	gc.fillText(String.valueOf(start.get()), left + (diameter - scaleDiameter) / 2.0 + 10.0, top + diameter / 2.0);
	gc.setTextAlign(TextAlignment.RIGHT);
	gc.fillText(String.valueOf(end.get()), left + (diameter + scaleDiameter) / 2.0 - 10.0, top + diameter / 2.0);

	gc.setLineWidth(1.0);
	gc.strokeLine(left + (diameter - scaleDiameter) / 2.0 - 10.0, top + diameter / 2.0, //
		left + (diameter - scaleDiameter) / 2.0 + 10.0, top + diameter / 2.0);
	gc.strokeLine(left + (diameter + scaleDiameter) / 2.0 - 10.0, top + diameter / 2.0, //
		left + (diameter + scaleDiameter) / 2.0 + 10.0, top + diameter / 2.0);

	gc.setTransform(1.0, 0.0, 0.0, 1.0, width / 2.0, height);
	gc.transform(1.0, 0.0, 0.0, -1.0, 0.0, 0.0);
	for (int i = 1; i <= 9; i++) {
	    gc.transform(Math.cos(Math.PI / 10.0), Math.sin(Math.PI / 10.0), -Math.sin(Math.PI / 10.0),
		    Math.cos(Math.PI / 10.0), 0.0, 0.0);
	    gc.strokeLine(scaleDiameter / 2.0 - 10.0, 0.0, scaleDiameter / 2.0 + 10.0, 0.0);
	    gc.fillText("?", scaleDiameter / 2.0 - 10.0, 0.0);
	}
	gc.setTransform(1.0, 0.0, 0.0, 1.0, 0.0, 0.0);
    }

}
