package com.puresoltechnologies.javafx.showroom.parts;

import java.io.IOException;
import java.util.Optional;

import com.puresoltechnologies.javafx.charts.ChartView;
import com.puresoltechnologies.javafx.charts.axes.AxisType;
import com.puresoltechnologies.javafx.charts.axes.NumberAxis;
import com.puresoltechnologies.javafx.charts.plots.xy.XYPlot;
import com.puresoltechnologies.javafx.perspectives.PartHeaderToolBar;
import com.puresoltechnologies.javafx.perspectives.parts.AbstractViewer;
import com.puresoltechnologies.javafx.perspectives.parts.PartContentType;
import com.puresoltechnologies.javafx.perspectives.parts.PartOpenMode;
import com.puresoltechnologies.javafx.showroom.ShowRoom;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class XYPlotSampleViewer extends AbstractViewer {

    private final BorderPane borderPane = new BorderPane();

    public XYPlotSampleViewer() {
	super("XY Plot Sample", PartOpenMode.AUTO_AND_MANUAL, PartContentType.ONE_PER_PERSPECTIVE);
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
	NumberAxis<Float> xAxis = new NumberAxis<>("X", "xxx", AxisType.X, Float.class);
	NumberAxis<Float> yAxis = new NumberAxis<>("Y", "yyy", AxisType.Y, Float.class);
	XYPlot<Float, Float, Color> ohlcPlot = new XYPlot<>("XY Plot", xAxis, yAxis);
	ChartView chartView = new ChartView("XY Plot");
	chartView.addPlot(ohlcPlot);
	borderPane.setCenter(chartView);
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
