package com.puresoltechnologies.javafx.charts.preferences;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.puresoltechnologies.javafx.extensions.fonts.FontSelector;
import com.puresoltechnologies.javafx.preferences.Preferences;
import com.puresoltechnologies.javafx.preferences.dialogs.PreferencesPage;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ChartPreferencesPage implements PreferencesPage {

    private FontSelector titleFontSelector;
    private FontSelector subTitleFontSelector;
    private FontSelector axisTitleFontSelector;
    private FontSelector axisLabelFontSelector;

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

	Label titelFontLabel = new Label("Title Font");
	GridPane.setConstraints(titelFontLabel, 0, 0);
	titleFontSelector = new FontSelector();
	GridPane.setConstraints(titleFontSelector, 1, 0);

	Label subTitleLabel = new Label("Subtitle Font");
	GridPane.setConstraints(subTitleLabel, 0, 1);
	subTitleFontSelector = new FontSelector();
	GridPane.setConstraints(subTitleFontSelector, 1, 1);

	Label axisTitleLabel = new Label("Axis Title Font");
	GridPane.setConstraints(axisTitleLabel, 0, 2);
	axisTitleFontSelector = new FontSelector();
	GridPane.setConstraints(axisTitleFontSelector, 1, 2);

	Label axisLabelFontLabel = new Label("Subtitle Font");
	GridPane.setConstraints(axisLabelFontLabel, 0, 3);
	axisLabelFontSelector = new FontSelector();
	GridPane.setConstraints(axisLabelFontSelector, 1, 3);

	gridPane.getChildren().addAll(titelFontLabel, titleFontSelector, subTitleLabel, subTitleFontSelector,
		axisTitleLabel, axisTitleFontSelector, axisLabelFontLabel, axisLabelFontSelector);
	return gridPane;
    }

    @Override
    public void reset() {
	titleFontSelector.setValue(ChartsProperties.TITLE_FONT.getDefaultValue());
	subTitleFontSelector.setValue(ChartsProperties.SUBTITLE_FONT.getDefaultValue());
	axisTitleFontSelector.setValue(ChartsProperties.AXIS_TITLE_FONT.getDefaultValue());
	axisLabelFontSelector.setValue(ChartsProperties.AXIS_LABEL_FONT.getDefaultValue());
    }

    @Override
    public void load(Preferences preferences) {
	titleFontSelector.setValue(preferences.getValue(ChartsProperties.TITLE_FONT));
	subTitleFontSelector.setValue(preferences.getValue(ChartsProperties.SUBTITLE_FONT));
	axisTitleFontSelector.setValue(preferences.getValue(ChartsProperties.AXIS_TITLE_FONT));
	axisLabelFontSelector.setValue(preferences.getValue(ChartsProperties.AXIS_LABEL_FONT));
    }

    @Override
    public void save(Preferences preferences) {
	preferences.setValue(ChartsProperties.TITLE_FONT, titleFontSelector.getValue());
	preferences.setValue(ChartsProperties.SUBTITLE_FONT, subTitleFontSelector.getValue());
	preferences.setValue(ChartsProperties.AXIS_TITLE_FONT, axisTitleFontSelector.getValue());
	preferences.setValue(ChartsProperties.AXIS_LABEL_FONT, axisLabelFontSelector.getValue());
    }

}
