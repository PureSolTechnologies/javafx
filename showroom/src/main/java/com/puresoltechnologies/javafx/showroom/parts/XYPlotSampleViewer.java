package com.puresoltechnologies.javafx.showroom.parts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.puresoltechnologies.javafx.charts.ChartView;
import com.puresoltechnologies.javafx.charts.axes.AxisType;
import com.puresoltechnologies.javafx.charts.axes.NumberAxis;
import com.puresoltechnologies.javafx.charts.plots.InterpolationType;
import com.puresoltechnologies.javafx.charts.plots.MarkerType;
import com.puresoltechnologies.javafx.charts.plots.xy.XYPlot;
import com.puresoltechnologies.javafx.charts.plots.xy.XYValue;
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

public class XYPlotSampleViewer extends AbstractViewer {

    private final BorderPane borderPane = new BorderPane();

    public XYPlotSampleViewer() {
	super("XY Plot Sample", PartOpenMode.AUTO_AND_MANUAL);
	try {
	    setImage(ResourceUtils.getImage(ShowRoom.class, "icons/FatCow_Icons16x16/chart_line.png"));
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
	NumberAxis<Double> xAxis = new NumberAxis<>("X", "xxx", AxisType.X, Double.class);
	NumberAxis<Double> yAxis = new NumberAxis<>("Y", "yyy", AxisType.Y, Double.class);
	NumberAxis<Double> altXAxis = new NumberAxis<>("Alt X", "xxx", AxisType.ALT_X, Double.class);
	altXAxis.setColor(Color.BLUE);
	NumberAxis<Double> altYAxis = new NumberAxis<>("Alt Y", "yyy", AxisType.ALT_Y, Double.class);
	altYAxis.setColor(Color.BLUE);

	XYPlot<Double, Double> xySinePlot = new XYPlot<>("Sine", xAxis, yAxis);
	xySinePlot.setColor(Color.RED);
	xySinePlot.setInterpolationType(InterpolationType.STRAIGHT_LINE);
	xySinePlot.setMarkerType(MarkerType.SQUARE);
	xySinePlot.setMarkerSize(2.0);

	XYPlot<Double, Double> xyCosinePlot = new XYPlot<>("Cosine", xAxis, yAxis);
	xyCosinePlot.setColor(Color.GREEN);
	xyCosinePlot.setInterpolationType(InterpolationType.NONE);
	xyCosinePlot.setMarkerType(MarkerType.DIAMOND);
	xyCosinePlot.setMarkerSize(5.0);

	XYPlot<Double, Double> xyTangentPlot = new XYPlot<>("Tangent", altXAxis, altYAxis);
	xyTangentPlot.setColor(Color.BLUE);
	xyTangentPlot.setInterpolationType(InterpolationType.CUBIC_SPLINES);
	xyTangentPlot.setMarkerType(MarkerType.CROSS);
	xyTangentPlot.setMarkerSize(10.0);

	ChartView chartView = new ChartView("XY Plot");
	chartView.setCopyLocale(Locale.GERMANY);
	chartView.addPlot(xySinePlot);
	chartView.addPlot(xyCosinePlot);
	chartView.addPlot(xyTangentPlot);
	chartView.setPlotPadding(new Insets(20.0));

	borderPane.setCenter(chartView);
	FXThreads.proceedOnFXThread(() -> xySinePlot.setData(generateSineXYData()));
	FXThreads.proceedOnFXThread(() -> xyCosinePlot.setData(generateCosineXYData()));
	FXThreads.proceedOnFXThread(() -> xyTangentPlot.setData(generateTangentXYData()));
    }

    private List<XYValue<Double, Double>> generateSineXYData() {
	double begin = -2.0 * Math.PI;
	double end = 2.0 * Math.PI;
	List<XYValue<Double, Double>> data = new ArrayList<>();
	double current = begin;
	while (current < end) {
	    double next = current + 0.1f;
	    data.add(new XYValue<>(current, Math.sin(current)));
	    current = next;
	}
	return data;
    }

    private List<XYValue<Double, Double>> generateCosineXYData() {
	double begin = -2.0 * Math.PI;
	double end = 2.0 * Math.PI;
	List<XYValue<Double, Double>> data = new ArrayList<>();
	double current = begin;
	while (current < end) {
	    double next = current + 0.1f;
	    data.add(new XYValue<>(current, Math.cos(current)));
	    current = next;
	}
	return data;
    }

    private List<XYValue<Double, Double>> generateTangentXYData() {
	double begin = -2.0 * Math.PI;
	double end = 2.0 * Math.PI;
	List<XYValue<Double, Double>> data = new ArrayList<>();
	double current = begin;
	while (current < end) {
	    double next = current + 0.1f;
	    data.add(new XYValue<>(current, Math.tan(current)));
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
