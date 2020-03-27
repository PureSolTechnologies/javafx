package com.puresoltechnologies.javafx.showroom.parts;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.puresoltechnologies.javafx.charts.ChartView;
import com.puresoltechnologies.javafx.charts.ChartView.LegendPosition;
import com.puresoltechnologies.javafx.charts.axes.AxisType;
import com.puresoltechnologies.javafx.charts.axes.NumberAxis;
import com.puresoltechnologies.javafx.charts.axes.TimeSeriesAxis;
import com.puresoltechnologies.javafx.charts.plots.InterpolationType;
import com.puresoltechnologies.javafx.charts.plots.MarkerType;
import com.puresoltechnologies.javafx.charts.plots.PlotDatum;
import com.puresoltechnologies.javafx.charts.plots.timeseries.TimeSeriesPlot;
import com.puresoltechnologies.javafx.perspectives.PartHeaderToolBar;
import com.puresoltechnologies.javafx.perspectives.parts.AbstractViewer;
import com.puresoltechnologies.javafx.perspectives.parts.PartOpenMode;
import com.puresoltechnologies.javafx.showroom.ShowRoom;
import com.puresoltechnologies.javafx.utils.FXThreads;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class TimeseriesPlotSampleViewer extends AbstractViewer {

    private static class TimeSeriesDatum implements PlotDatum<Instant, Double> {
	protected final Instant instant;
	protected final Double value;

	public TimeSeriesDatum(Instant instant, Double value) {
	    super();
	    this.instant = instant;
	    this.value = value;
	}

	@Override
	public String getClipboardString(Locale locale) {
	    return instant.getEpochSecond() + "\t" + value;
	}

    }

    private final BorderPane borderPane = new BorderPane();

    public TimeseriesPlotSampleViewer() {
	super("Timeseries Plot Sample", PartOpenMode.AUTO_AND_MANUAL);
	try {
	    setImage(ResourceUtils.getImage(ShowRoom.class, "icons/FatCow_Icons16x16/chart_curve.png"));
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
	TimeSeriesAxis xAxis = new TimeSeriesAxis("time (s)", AxisType.X);
	NumberAxis<Double> yAxis = new NumberAxis<>("measurement", "unit", AxisType.Y, Double.class);
	TimeSeriesPlot<Double, TimeSeriesDatum> plot = new TimeSeriesPlot<>("Time Series Sample", xAxis, yAxis,
		(datum) -> datum.instant, (datum) -> datum.value);
	plot.setColor(Color.RED);
	plot.setInterpolationType(InterpolationType.STRAIGHT_LINE);
	plot.setMarkerType(MarkerType.SQUARE);
	plot.setMarkerSize(5.0);
	plot.setLineWidth(3.0);
	plot.setLineAlpha(0.5);

	ChartView chartView = new ChartView("Time Series Plot");
	chartView.setCopyLocale(Locale.GERMANY);
	chartView.addPlot(plot);
	chartView.setPlotPadding(new Insets(20.0));
	chartView.setLegendPosition(LegendPosition.BOTTOM);

	borderPane.setCenter(chartView);
	FXThreads.proceedOnFXThread(() -> plot.setData(generateData()));
    }

    private List<TimeSeriesDatum> generateData() {
	Instant start = Instant.now().minusSeconds(3600);
	List<TimeSeriesDatum> data = new ArrayList<>();
	for (int i = 0; i <= 3600; i += 100) {
	    Instant current = start.plusSeconds(i);
	    data.add(new TimeSeriesDatum(current, Math.sin((Math.PI * i) / (5 * 180.0))));
	}
	return data;
    }

    @Override
    public void close() {
	// intentionally left empty
    }

    @Override
    public Node getContent() {
	return borderPane;
    }

}
