package com.puresoltechnologies.javafx.charts.plots.ohlc;

import java.time.Instant;

import com.puresoltechnologies.javafx.charts.axes.InstantAxisRenderer;
import com.puresoltechnologies.javafx.charts.axes.NumberAxisRenderer;
import com.puresoltechnologies.javafx.charts.plots.AbstractPlotRenderer;
import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.preferences.OHLCPlotProperties;
import com.puresoltechnologies.javafx.preferences.Preferences;

import javafx.beans.property.ObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class OHLCPlotRenderer<Y extends Number & Comparable<Y>>
	extends AbstractPlotRenderer<Instant, Y, OHLCValue<Y>, InstantAxisRenderer, NumberAxisRenderer> {

    private static final ObjectProperty<Color> upwardTrendColor = Preferences
	    .getProperty(OHLCPlotProperties.UPWARD_TREND_COLOR);
    private static final ObjectProperty<Color> downwardTrendColor = Preferences
	    .getProperty(OHLCPlotProperties.DOWNWARD_TREND_COLOR);

    public OHLCPlotRenderer(Canvas canvas, Plot<Instant, Y, OHLCValue<Y>> plot, InstantAxisRenderer xAxisRenderer,
	    NumberAxisRenderer yAxisRenderer) {
	super(canvas, plot, xAxisRenderer, yAxisRenderer);
    }

    @Override
    public void renderTo(double x, double y, double width, double height) {
	GraphicsContext gc = getCanvas().getGraphicsContext2D();
	InstantAxisRenderer xAxisRenderer = getXAxisRenderer();
	NumberAxisRenderer yAxisRenderer = getYAxisRenderer();
	Plot<Instant, Y, OHLCValue<Y>> plot = getPlot();
	gc.setStroke(Color.BLACK);
	gc.setLineWidth(1.0);
	for (OHLCValue<Y> value : plot.getData()) {
	    double startX = xAxisRenderer.calculatePos(x, y, width, height, value.getStart());
	    double endX = xAxisRenderer.calculatePos(x, y, width, height, value.getEnd());
	    double openY = yAxisRenderer.calculatePos(x, y, width, height, value.getOpen());
	    double closeY = yAxisRenderer.calculatePos(x, y, width, height, value.getClose());
	    double highY = yAxisRenderer.calculatePos(x, y, width, height, value.getHigh());
	    double lowY = yAxisRenderer.calculatePos(x, y, width, height, value.getLow());
	    gc.setFill(Color.GRAY);
	    gc.fillRect(startX + (endX - startX) / 3.0, highY, (endX - startX) / 3.0, lowY - highY);
	    if (value.isIncrease()) {
		gc.setFill(upwardTrendColor.get());
		gc.fillRect(startX, closeY, endX - startX, closeY - openY);
	    } else {
		gc.setFill(downwardTrendColor.get());
		gc.fillRect(startX, openY, endX - startX, openY - closeY);
	    }
	}
    }

}
