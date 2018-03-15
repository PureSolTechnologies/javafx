package com.puresoltechnologies.javafx.charts;

import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.plots.PlotArea;
import com.puresoltechnologies.javafx.charts.preferences.ChartsProperties;
import com.puresoltechnologies.javafx.extensions.fonts.FontDefinition;
import com.puresoltechnologies.javafx.preferences.Preferences;
import com.puresoltechnologies.javafx.utils.FXNodeUtils;
import com.puresoltechnologies.javafx.utils.FXThreads;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * This is the component to be used to plot into.
 * 
 * @author Rick-Rainer Ludwig
 */
public class ChartView extends GridPane {

    protected static final ObjectProperty<FontDefinition> TITLE_FONT = Preferences
	    .getProperty(ChartsProperties.TITLE_FONT);
    protected static final ObjectProperty<FontDefinition> SUBTITLE_FONT = Preferences
	    .getProperty(ChartsProperties.SUBTITLE_FONT);

    private final PlotArea plotArea = new PlotArea();
    private final Label title = new Label();
    private final Label subTitle = new Label();

    public ChartView(String title) {
	this();
	this.title.setText(title);
    }

    public ChartView() {
	super();
	title.setFont(TITLE_FONT.get().toFont());
	title.setAlignment(Pos.TOP_CENTER);
	FXNodeUtils.setTextColor(title, TITLE_FONT.get().getColor());
	subTitle.setFont(SUBTITLE_FONT.get().toFont());
	subTitle.setAlignment(Pos.TOP_CENTER);
	FXNodeUtils.setTextColor(subTitle, SUBTITLE_FONT.get().getColor());
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
