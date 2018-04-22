package com.puresoltechnologies.javafx.charts.preferences;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.puresoltechnologies.javafx.extensions.fonts.FontSelector;
import com.puresoltechnologies.javafx.preferences.Preferences;
import com.puresoltechnologies.javafx.preferences.dialogs.PreferencesPage;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.geometry.Orientation;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ChartPreferencesPage implements PreferencesPage {

    private ColorPicker backgroundColorPicker;
    private ColorPicker axisColorPicker;
    private FontSelector titleFontSelector;
    private FontSelector subTitleFontSelector;
    private FontSelector axisTitleFontSelector;
    private FontSelector axisLabelFontSelector;
    private FontSelector dataLabelFontSelector;

    @Override
    public String getName() {
	return "General Chart Settings";
    }

    @Override
    public Image getImage() {
	try {
	    return ResourceUtils.getImage(this, "/icons/FatCow_Icons16x16/chart_stock.png");
	} catch (IOException e) {
	    return null;
	}
    }

    @Override
    public List<String> getPath() {
	return Arrays.asList("Charts");
    }

    @Override
    public Pane getPane() {
	GridPane gridPane = new GridPane();
	gridPane.setHgap(10.0);
	gridPane.setVgap(10.0);

	Label backgroundColorLabel = new Label("Backgroud Color:");
	GridPane.setConstraints(backgroundColorLabel, 0, 0);
	backgroundColorPicker = new ColorPicker();
	GridPane.setConstraints(backgroundColorPicker, 1, 0);

	Label axisColorLabel = new Label("Axis Color:");
	GridPane.setConstraints(axisColorLabel, 0, 1);
	axisColorPicker = new ColorPicker();
	GridPane.setConstraints(axisColorPicker, 1, 1);

	Separator separator = new Separator(Orientation.HORIZONTAL);
	GridPane.setConstraints(separator, 0, 2, 2, 1);

	Label titelFontLabel = new Label("Title Font");
	GridPane.setConstraints(titelFontLabel, 0, 3);
	titleFontSelector = new FontSelector();
	GridPane.setConstraints(titleFontSelector, 1, 3);

	Label subTitleLabel = new Label("Subtitle Font");
	GridPane.setConstraints(subTitleLabel, 0, 4);
	subTitleFontSelector = new FontSelector();
	GridPane.setConstraints(subTitleFontSelector, 1, 4);

	Label axisTitleLabel = new Label("Axis Title Font");
	GridPane.setConstraints(axisTitleLabel, 0, 5);
	axisTitleFontSelector = new FontSelector();
	GridPane.setConstraints(axisTitleFontSelector, 1, 5);

	Label axisLabelFontLabel = new Label("Axis Label Font");
	GridPane.setConstraints(axisLabelFontLabel, 0, 6);
	axisLabelFontSelector = new FontSelector();
	GridPane.setConstraints(axisLabelFontSelector, 1, 6);

	Label dataLabelFontLabel = new Label("Data Label Font");
	GridPane.setConstraints(dataLabelFontLabel, 0, 7);
	dataLabelFontSelector = new FontSelector();
	GridPane.setConstraints(dataLabelFontSelector, 1, 7);

	gridPane.getChildren().addAll(backgroundColorLabel, backgroundColorPicker, axisColorLabel, axisColorPicker,
		separator, titelFontLabel, titleFontSelector, subTitleLabel, subTitleFontSelector, axisTitleLabel,
		axisTitleFontSelector, axisLabelFontLabel, axisLabelFontSelector, dataLabelFontLabel,
		dataLabelFontSelector);
	return gridPane;
    }

    @Override
    public void reset() {
	backgroundColorPicker.setValue(ChartsProperties.BACKGROUND_COLOR.getDefaultValue());
	axisColorPicker.setValue(ChartsProperties.AXIS_COLOR.getDefaultValue());
	titleFontSelector.setValue(ChartsProperties.TITLE_FONT.getDefaultValue());
	subTitleFontSelector.setValue(ChartsProperties.SUBTITLE_FONT.getDefaultValue());
	axisTitleFontSelector.setValue(ChartsProperties.AXIS_TITLE_FONT.getDefaultValue());
	axisLabelFontSelector.setValue(ChartsProperties.AXIS_LABEL_FONT.getDefaultValue());
	dataLabelFontSelector.setValue(ChartsProperties.DATA_LABEL_FONT.getDefaultValue());
    }

    @Override
    public void load(Preferences preferences) {
	backgroundColorPicker.setValue(preferences.getValue(ChartsProperties.BACKGROUND_COLOR));
	axisColorPicker.setValue(preferences.getValue(ChartsProperties.AXIS_COLOR));
	titleFontSelector.setValue(preferences.getValue(ChartsProperties.TITLE_FONT));
	subTitleFontSelector.setValue(preferences.getValue(ChartsProperties.SUBTITLE_FONT));
	axisTitleFontSelector.setValue(preferences.getValue(ChartsProperties.AXIS_TITLE_FONT));
	axisLabelFontSelector.setValue(preferences.getValue(ChartsProperties.AXIS_LABEL_FONT));
	dataLabelFontSelector.setValue(preferences.getValue(ChartsProperties.DATA_LABEL_FONT));
    }

    @Override
    public void save(Preferences preferences) {
	preferences.setValue(ChartsProperties.BACKGROUND_COLOR, backgroundColorPicker.getValue());
	preferences.setValue(ChartsProperties.AXIS_COLOR, axisColorPicker.getValue());
	preferences.setValue(ChartsProperties.TITLE_FONT, titleFontSelector.getValue());
	preferences.setValue(ChartsProperties.SUBTITLE_FONT, subTitleFontSelector.getValue());
	preferences.setValue(ChartsProperties.AXIS_TITLE_FONT, axisTitleFontSelector.getValue());
	preferences.setValue(ChartsProperties.AXIS_LABEL_FONT, axisLabelFontSelector.getValue());
	preferences.setValue(ChartsProperties.DATA_LABEL_FONT, dataLabelFontSelector.getValue());
    }

}
