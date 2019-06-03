package com.puresoltechnologies.javafx.showroom.parts;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.puresoltechnologies.javafx.charts.ChartView;
import com.puresoltechnologies.javafx.charts.axes.Axis;
import com.puresoltechnologies.javafx.charts.axes.AxisType;
import com.puresoltechnologies.javafx.charts.axes.NumberAxis;
import com.puresoltechnologies.javafx.charts.axes.TimeSeriesAxis;
import com.puresoltechnologies.javafx.charts.plots.ohlc.OHLCPlot;
import com.puresoltechnologies.javafx.charts.plots.ohlc.OHLCValue;
import com.puresoltechnologies.javafx.perspectives.PartHeaderToolBar;
import com.puresoltechnologies.javafx.perspectives.parts.AbstractViewer;
import com.puresoltechnologies.javafx.perspectives.parts.PartContentType;
import com.puresoltechnologies.javafx.perspectives.parts.PartOpenMode;
import com.puresoltechnologies.javafx.showroom.ShowRoom;
import com.puresoltechnologies.javafx.utils.FXThreads;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class OhlcPlotSampleViewer extends AbstractViewer {

    private final BorderPane borderPane = new BorderPane();

    public OhlcPlotSampleViewer() {
	super("OHLC Plot Sample", PartOpenMode.AUTO_AND_MANUAL, PartContentType.ONE_PER_PERSPECTIVE);
	try {
	    setImage(ResourceUtils.getImage(ShowRoom.class, "icons/FatCow_Icons16x16/chart_bar.png"));
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public Optional<PartHeaderToolBar> getToolBar() {
	return Optional.empty();
    }

    @Override
    public void initialize() {
	TimeSeriesAxis timeAxis = new TimeSeriesAxis("Time", AxisType.X);
	Axis<Double> priceAxis = new NumberAxis<>("Price", "EUR", AxisType.Y, Double.class);
	OHLCPlot<Double> ohlcPlot = new OHLCPlot<>("OHLC Plot", timeAxis, priceAxis);
	ChartView chartView = new ChartView("OHLC Plot");
	chartView.addPlot(ohlcPlot);
	borderPane.setCenter(chartView);

	FXThreads.proceedOnFXThread(() -> ohlcPlot.setData(generateTestOHLCData()));
    }

    private List<OHLCValue<Double>> generateTestOHLCData() {
	Instant begin = Instant.ofEpochSecond(1483228800);
	Instant end = Instant.ofEpochSecond(1514764800);
	List<OHLCValue<Double>> data = new ArrayList<>();
	double days = 0;
	Instant current = begin;
	Random random = new Random(1234567890l);
	while (current.isBefore(end)) {
	    Instant next = current.plus(1, ChronoUnit.DAYS);
	    double open = Math.sin(((2 * Math.PI) / 28.0) * days);
	    double close = open + random.nextGaussian();
	    double high = Math.max(open, close) + 0.5;
	    double low = Math.min(open, close) - 0.5;
	    data.add(new OHLCValue<>(current, next, open, high, low, close));
	    days += 1.0;
	    current = next;
	}

	return data;
    }

    @Override
    public void close() {
	// TODO Auto-generated method stub

    }

    @Override
    public Node getContent() {
	return borderPane;
    }

}
