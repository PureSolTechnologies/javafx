package com.puresoltechnologies.javafx.charts;

import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.plots.PlotArea;
import com.puresoltechnologies.javafx.utils.FXThreads;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * This is the component to be used to plot into.
 * 
 * @author Rick-Rainer Ludwig
 */
public class ChartView extends GridPane {

    private final PlotArea plotArea = new PlotArea();
    private final Label title = new Label();
    private final Label subTitle = new Label();

    public ChartView(String title) {
	this();
	this.title.setText(title);
    }

    public ChartView() {
	super();
	Font defaultFont = Font.getDefault();
	title.setFont(Font.font(defaultFont.getFamily(), FontWeight.BOLD, defaultFont.getSize() * 1.5));
	title.setAlignment(Pos.TOP_CENTER);
	subTitle.setFont(Font.font(defaultFont.getFamily(), FontWeight.BOLD, defaultFont.getSize()));
	subTitle.setAlignment(Pos.TOP_CENTER);
	Label status = new Label("Status");
	GridPane.setConstraints(title, 0, 0, 1, 1, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.NEVER);
	GridPane.setConstraints(subTitle, 0, 1, 1, 1, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.NEVER);
	GridPane.setConstraints(plotArea, 0, 2, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
	GridPane.setConstraints(status, 0, 3, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
	getChildren().addAll(title, subTitle, plotArea, status);
	setGridLinesVisible(false);
    }

    public void setTitle(String title) {
	FXThreads.proceedOnFXThread(() -> ChartView.this.title.setText(title));
    }

    public String getTitle() {
	return title.getText();
    }

    public void setSubTitle(String subTitle) {
	FXThreads.proceedOnFXThread(() -> ChartView.this.subTitle.setText(subTitle));
    }

    public String getSubTitle() {
	return subTitle.getText();
    }

    public void addPlot(Plot<?, ?, ?> plot) {
	plotArea.addPlot(plot);
    }

}
