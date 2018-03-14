package com.puresoltechnologies.javafx.charts;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import com.puresoltechnologies.javafx.charts.axes.AxisType;
import com.puresoltechnologies.javafx.charts.axes.NumberAxis;
import com.puresoltechnologies.javafx.charts.axes.TimeSeriesAxis;
import com.puresoltechnologies.javafx.charts.plots.PlotData;
import com.puresoltechnologies.javafx.charts.plots.ohlc.OHLCPlot;
import com.puresoltechnologies.javafx.charts.plots.ohlc.OHLCValue;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class OHLCPlotIT extends ApplicationTest {

    ChartView chartView;

    @Override
    public void start(Stage stage) {
	chartView = new ChartView();
	TimeSeriesAxis xAxis = new TimeSeriesAxis("Timestamp", AxisType.X);
	NumberAxis<Double> yAxis = new NumberAxis<>("Exchange Rate", null, AxisType.Y, Double.class);
	PlotData<Instant, Double, OHLCValue<Double>> ohlcData = generateTestOHLCData();
	OHLCPlot<Double> ohlcPlot = new OHLCPlot<Double>("OHLCPlot", xAxis, yAxis, ohlcData);
	chartView.setTitle("Plot Test");
	chartView.setSubTitle("OHLC Data");
	chartView.addPlot(ohlcPlot);

	Scene scene = new Scene(chartView, 1024, 768);
	stage.setResizable(true);
	stage.setScene(scene);
	stage.show();
    }

    private PlotData<Instant, Double, OHLCValue<Double>> generateTestOHLCData() {
	PlotData<Instant, Double, OHLCValue<Double>> testData = new PlotData<>() {

	    private final Instant begin = Instant.ofEpochSecond(1483228800);
	    private final Instant end = Instant.ofEpochSecond(1514764800);
	    private final List<OHLCValue<Double>> data = new ArrayList<>();
	    {
		double days = 0;
		Instant current = begin;
		Random random = new Random(1234567890l);
		while (current.isBefore(end)) {
		    Instant next = current.plus(1, ChronoUnit.DAYS);
		    double open = Math.sin(2 * Math.PI / 28.0 * days);
		    double close = open + random.nextGaussian();
		    double high = Math.max(open, close) + 0.5;
		    double low = Math.min(open, close) - 0.5;
		    data.add(new OHLCValue<>(current, next, open, high, low, close));
		    days += 1.0;
		    current = next;
		}
	    }

	    @Override
	    public Instant getMinX() {
		return begin;
	    }

	    @Override
	    public Instant getMaxX() {
		return end;
	    }

	    @Override
	    public Double getMinY() {
		return -2.0;
	    }

	    @Override
	    public Double getMaxY() {
		return 2.0;
	    }

	    @Override
	    public List<OHLCValue<Double>> getData() {
		return data;
	    }
	};
	return testData;
    }

    @Test
    public void testPerspectiveDialog() throws InterruptedException {
	Thread.sleep(1000);
    }

}
