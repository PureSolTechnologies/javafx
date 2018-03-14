package com.puresoltechnologies.javafx.charts.preferences;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.puresoltechnologies.javafx.preferences.Preferences;
import com.puresoltechnologies.javafx.preferences.dialogs.PreferencesPage;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class OHLCPlotPreferencesPage implements PreferencesPage {

    private ColorPicker upColor;
    private ColorPicker downColor;

    @Override
    public String getName() {
	return "OHLC Plot Settings";
    }

    @Override
    public Image getImage() {
	try {
	    return ResourceUtils.getImage(this, "/icons/FatCow_Icons16x16/chart_bar.png");
	} catch (IOException e) {
	    return null;
	}
    }

    @Override
    public List<String> getPath() {
	return Arrays.asList("Charts", "OHLC Plots");
    }

    @Override
    public Pane getPane() {
	GridPane gridPane = new GridPane();

	Label upColorLabel = new Label("Upward trend color");
	GridPane.setRowIndex(upColorLabel, 0);
	GridPane.setColumnIndex(upColorLabel, 0);
	upColor = new ColorPicker();
	GridPane.setRowIndex(upColor, 0);
	GridPane.setColumnIndex(upColor, 1);
	Label downColorLabel = new Label("Downward trend color");
	GridPane.setRowIndex(downColorLabel, 1);
	GridPane.setColumnIndex(downColorLabel, 0);
	downColor = new ColorPicker();
	GridPane.setRowIndex(downColor, 1);
	GridPane.setColumnIndex(downColor, 1);

	gridPane.getChildren().addAll(upColorLabel, upColor, downColorLabel, downColor);
	return gridPane;
    }

    @Override
    public void reset() {
	upColor.setValue(Color.LIGHTSEAGREEN);
	downColor.setValue(Color.LIGHTCORAL);
    }

    @Override
    public void load(Preferences preferences) {
	// TODO Auto-generated method stub

    }

    @Override
    public void save(Preferences preferences) {
	// TODO Auto-generated method stub

    }

}
