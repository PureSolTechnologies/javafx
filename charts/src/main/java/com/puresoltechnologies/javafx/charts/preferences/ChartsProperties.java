package com.puresoltechnologies.javafx.charts.preferences;

import com.puresoltechnologies.javafx.extensions.fonts.FontDefinition;
import com.puresoltechnologies.javafx.extensions.properties.PropertyDefinition;
import com.puresoltechnologies.javafx.extensions.properties.SimplePropertyDefinition;

import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * This interface contains all constants to chart property definitions related
 * to the charts.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public interface ChartsProperties {

    static final String PROPERTY_BASE = ChartsProperties.class.getPackage().getName();

    static final FontDefinition titleFontDefault = new FontDefinition("Serif", FontWeight.BOLD, 24.0,
	    FontPosture.REGULAR, Color.BLACK);
    static final FontDefinition subTitleFontDefault = new FontDefinition("Serif", FontWeight.BOLD, 20.0,
	    FontPosture.ITALIC, Color.BLACK);
    static final FontDefinition axisTitleFontDefault = new FontDefinition("SansSerif", FontWeight.BOLD, 12.0,
	    FontPosture.REGULAR, Color.BLACK);
    static final FontDefinition axisLabelFontDefault = new FontDefinition("SansSerif", FontWeight.NORMAL, 10.0,
	    FontPosture.REGULAR, Color.BLACK);

    public static final PropertyDefinition<FontDefinition> TITLE_FONT = new SimplePropertyDefinition<FontDefinition>(
	    PROPERTY_BASE + ".chart.title.font", "Chart Title Font", "Font to be used for chart titles.",
	    FontDefinition.class, titleFontDefault);

    public static final PropertyDefinition<FontDefinition> SUBTITLE_FONT = new SimplePropertyDefinition<FontDefinition>(
	    PROPERTY_BASE + ".chart.subtitle.font", "Chart Subtitle Font", "Font to be used for chart subtitles.",
	    FontDefinition.class, subTitleFontDefault);

    public static final PropertyDefinition<FontDefinition> AXIS_TITLE_FONT = new SimplePropertyDefinition<FontDefinition>(
	    PROPERTY_BASE + ".chart.axis.title.font", "Axis Title Font", "Font to be used for chart axis titles.",
	    FontDefinition.class, axisTitleFontDefault);

    public static final PropertyDefinition<FontDefinition> AXIS_LABEL_FONT = new SimplePropertyDefinition<FontDefinition>(
	    PROPERTY_BASE + ".chart.axis.label.font", "Axis Label Font", "Font to be used for chart axis labels.",
	    FontDefinition.class, axisLabelFontDefault);

}
