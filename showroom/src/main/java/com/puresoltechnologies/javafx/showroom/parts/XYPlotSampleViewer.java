package com.puresoltechnologies.javafx.showroom.parts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.puresoltechnologies.javafx.charts.ChartView;
import com.puresoltechnologies.javafx.charts.axes.AxisType;
import com.puresoltechnologies.javafx.charts.axes.NumberAxis;
import com.puresoltechnologies.javafx.charts.plots.xy.XYPlot;
import com.puresoltechnologies.javafx.charts.plots.xy.XYValue;
import com.puresoltechnologies.javafx.perspectives.PartHeaderToolBar;
import com.puresoltechnologies.javafx.perspectives.parts.AbstractViewer;
import com.puresoltechnologies.javafx.perspectives.parts.PartOpenMode;
import com.puresoltechnologies.javafx.showroom.ShowRoom;
import com.puresoltechnologies.javafx.utils.FXThreads;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

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
	XYPlot<Double, Double> xyPlot = new XYPlot<>("XY Plot", xAxis, yAxis);
	ChartView chartView = new ChartView("XY Plot");
	chartView.addPlot(xyPlot);
	borderPane.setCenter(chartView);

	FXThreads.proceedOnFXThread(() -> xyPlot.setData(generateTestXYData()));
    }

    private List<XYValue<Double, Double>> generateTestXYData() {
	double begin = -2.0f;
	double end = 2.0f;
	List<XYValue<Double, Double>> data = new ArrayList<>();
	double current = begin;
	while (current < end) {
	    double next = current + 0.1f;
	    data.add(new XYValue<>(current, current * current));
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
