package com.puresoltechnologies.javafx.charts.preferences;

import com.puresoltechnologies.javafx.charts.ChartView;
import com.puresoltechnologies.javafx.charts.plots.Plot;
import com.puresoltechnologies.javafx.extensions.fonts.FontDefinition;
import com.puresoltechnologies.javafx.extensions.properties.PropertyDefinition;
import com.puresoltechnologies.javafx.extensions.properties.SimplePropertyDefinition;

import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * This interface contains all constants to chart property definitions related
 * to all {@link Plot}s and {@link ChartView}.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public interface ChartsProperties {

    String PROPERTY_BASE = ChartsProperties.class.getPackage().getName();

    FontDefinition titleFontDefault = new FontDefinition("Serif", FontWeight.BOLD, 24.0, FontPosture.REGULAR,
	    Color.BLACK);
    FontDefinition subTitleFontDefault = new FontDefinition("Serif", FontWeight.BOLD, 20.0, FontPosture.ITALIC,
	    Color.BLACK);
    FontDefinition axisTitleFontDefault = new FontDefinition("SansSerif", FontWeight.BOLD, 12.0, FontPosture.REGULAR,
	    Color.BLACK);
    FontDefinition axisLabelFontDefault = new FontDefinition("SansSerif", FontWeight.NORMAL, 10.0, FontPosture.REGULAR,
	    Color.BLACK);
    FontDefinition dataLabelFontDefault = new FontDefinition("SansSerif", FontWeight.NORMAL, 10.0, FontPosture.REGULAR,
	    Color.BLACK);

    PropertyDefinition<FontDefinition> TITLE_FONT = new SimplePropertyDefinition<>(PROPERTY_BASE + ".title.font",
	    "Chart Title Font", "Font to be used for chart titles.", FontDefinition.class, titleFontDefault);

    PropertyDefinition<FontDefinition> SUBTITLE_FONT = new SimplePropertyDefinition<>(PROPERTY_BASE + ".subtitle.font",
	    "Chart Subtitle Font", "Font to be used for chart subtitles.", FontDefinition.class, subTitleFontDefault);

    PropertyDefinition<FontDefinition> AXIS_TITLE_FONT = new SimplePropertyDefinition<>(
	    PROPERTY_BASE + ".chart.axis.title.font", "Axis Title Font", "Font to be used for chart axis titles.",
	    FontDefinition.class, axisTitleFontDefault);

    PropertyDefinition<FontDefinition> AXIS_LABEL_FONT = new SimplePropertyDefinition<>(
	    PROPERTY_BASE + ".chart.axis.label.font", "Axis Label Font", "Font to be used for chart axis labels.",
	    FontDefinition.class, axisLabelFontDefault);

    PropertyDefinition<FontDefinition> DATA_LABEL_FONT = new SimplePropertyDefinition<>(
	    PROPERTY_BASE + ".chart.data.label.font", "Data Label Font", "Font to be used for chart data labels.",
	    FontDefinition.class, dataLabelFontDefault);

}
