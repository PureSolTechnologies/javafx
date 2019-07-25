package com.puresoltechnologies.javafx.charts;

import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.charts.plots.PlotCanvas;
import com.puresoltechnologies.javafx.charts.preferences.ChartsProperties;
import com.puresoltechnologies.javafx.extensions.fonts.FontDefinition;
import com.puresoltechnologies.javafx.preferences.Preferences;
import com.puresoltechnologies.javafx.utils.FXNodeUtils;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

    private final StringProperty titleProperty = new SimpleStringProperty();
    private final StringProperty subTitleProperty = new SimpleStringProperty();

    private final PlotCanvas plotCanvas = new PlotCanvas();
    private final Label titleLabel = new Label();
    private final Label subTitleLabel = new Label();

    public ChartView(String title) {
	this();
	titleProperty.setValue(title);
    }

    public ChartView() {
	super();
	titleLabel.textProperty().bind(titleProperty);
	titleLabel.visibleProperty().bind(titleProperty.isNotEmpty());
	titleLabel.managedProperty().bind(titleProperty.isNotEmpty());
	titleLabel.setFont(TITLE_FONT.get().toFont());
	titleLabel.setAlignment(Pos.TOP_CENTER);
	FXNodeUtils.setTextColor(titleLabel, TITLE_FONT.get().getColor());
	subTitleLabel.textProperty().bind(subTitleProperty);
	subTitleLabel.visibleProperty().bind(subTitleProperty.isNotEmpty());
	subTitleLabel.managedProperty().bind(subTitleProperty.isNotEmpty());
	subTitleLabel.setFont(SUBTITLE_FONT.get().toFont());
	subTitleLabel.setAlignment(Pos.TOP_CENTER);
	FXNodeUtils.setTextColor(subTitleLabel, SUBTITLE_FONT.get().getColor());
	plotCanvas.setManaged(true);
	GridPane.setConstraints(titleLabel, 0, 0, 1, 1, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.NEVER);
	GridPane.setConstraints(subTitleLabel, 0, 1, 1, 1, HPos.CENTER, VPos.TOP, Priority.ALWAYS, Priority.NEVER);
	GridPane.setConstraints(plotCanvas, 0, 2, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
	getChildren().addAll(titleLabel, subTitleLabel, plotCanvas);
	setGridLinesVisible(false);
    }

    public StringProperty titleProperty() {
	return titleProperty;
    }

    public void setTitle(String title) {
	titleProperty.setValue(title);
    }

    public String getTitle() {
	return titleProperty.getValue();
    }

    public StringProperty subTitleProperty() {
	return subTitleProperty;
    }

    public void setSubTitle(String subTitle) {
	subTitleProperty.setValue(subTitle);
    }

    public String getSubTitle() {
	return subTitleProperty.get();
    }

    public void addPlot(Plot<?, ?, ?> plot) {
	plotCanvas.addPlot(plot);
    }

}
