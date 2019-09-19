package com.puresoltechnologies.javafx.charts.plots.ohlc;

import java.time.Instant;

import com.puresoltechnologies.javafx.charts.axes.Axis;
import com.puresoltechnologies.javafx.charts.axes.InstantAxisRenderer;
import com.puresoltechnologies.javafx.charts.axes.NumberAxis;
import com.puresoltechnologies.javafx.charts.axes.NumberAxisRenderer;
import com.puresoltechnologies.javafx.charts.plots.AbstractPlotRenderer;
import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.preferences.OHLCPlotProperties;
import com.puresoltechnologies.javafx.preferences.Preferences;

import javafx.beans.property.ObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class OHLCPlotRenderer<Y extends Number & Comparable<Y>> extends
	AbstractPlotRenderer<Instant, Y, OHLCValue<Y>, InstantAxisRenderer<Axis<Instant>>, NumberAxisRenderer<Y, NumberAxis<Y>>> {

    private static final ObjectProperty<Color> upwardTrendColor = Preferences
	    .getProperty(OHLCPlotProperties.UPWARD_TREND_COLOR);
    private static final ObjectProperty<Color> downwardTrendColor = Preferences
	    .getProperty(OHLCPlotProperties.DOWNWARD_TREND_COLOR);

    public OHLCPlotRenderer(Plot<Instant, Y, OHLCValue<Y>> plot, InstantAxisRenderer xAxisRenderer,
	    NumberAxisRenderer yAxisRenderer) {
	super(plot, xAxisRenderer, yAxisRenderer);
    }

    @Override
    public void draw(Canvas canvas, double x, double y, double width, double height) {
	super.draw(canvas, x, y, width, height);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	InstantAxisRenderer<?> xAxisRenderer = getXAxisRenderer();
	NumberAxisRenderer<?, ?> yAxisRenderer = getYAxisRenderer();
	Plot<Instant, Y, OHLCValue<Y>> plot = getPlot();
	gc.setStroke(plot.getColor());
	gc.setLineWidth(1.0);
	for (OHLCValue<Y> value : plot.getData()) {
	    double startX = xAxisRenderer.calculatePos(x, y, width, height, value.getStart());
	    double endX = xAxisRenderer.calculatePos(x, y, width, height, value.getEnd());
	    double openY = yAxisRenderer.calculatePos(x, y, width, height, value.getOpen());
	    double closeY = yAxisRenderer.calculatePos(x, y, width, height, value.getClose());
	    double highY = yAxisRenderer.calculatePos(x, y, width, height, value.getHigh());
	    double lowY = yAxisRenderer.calculatePos(x, y, width, height, value.getLow());
	    gc.setFill(plot.getColor());
	    double middlePos = (endX - startX) / 2.0;
	    gc.fillRect(startX + ((endX - startX) / 3.0) + middlePos, highY, (endX - startX) / 3.0, lowY - highY);
	    gc.strokeRect(startX + ((endX - startX) / 3.0) + middlePos, highY, (endX - startX) / 3.0, lowY - highY);
	    if (value.isIncrease()) {
		gc.setFill(upwardTrendColor.get());
		gc.fillRect(startX + middlePos, closeY, endX - startX, openY - closeY);
	    } else {
		gc.setFill(downwardTrendColor.get());
		gc.fillRect(startX + middlePos, openY, endX - startX, closeY - openY);
	    }
	}
    }

}
