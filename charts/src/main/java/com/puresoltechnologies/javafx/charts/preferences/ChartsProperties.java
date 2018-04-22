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

    static final String PROPERTY_BASE = ChartsProperties.class.getPackage().getName();

    static final Color backgroundColorDefault = Color.WHITE;
    static final Color axisColorDefault = Color.BLACK;

    public static final PropertyDefinition<Color> BACKGROUND_COLOR = new SimplePropertyDefinition<Color>(
	    PROPERTY_BASE + ".background.color", "Background Color", "Background color to be used for charts.",
	    Color.class, backgroundColorDefault);

    public static final PropertyDefinition<Color> AXIS_COLOR = new SimplePropertyDefinition<Color>(
	    PROPERTY_BASE + ".axis.color", "Axis Color", "Axis color to be used for all plots.", Color.class,
	    axisColorDefault);

    static final FontDefinition titleFontDefault = new FontDefinition("Serif", FontWeight.BOLD, 24.0,
	    FontPosture.REGULAR, Color.BLACK);
    static final FontDefinition subTitleFontDefault = new FontDefinition("Serif", FontWeight.BOLD, 20.0,
	    FontPosture.ITALIC, Color.BLACK);
    static final FontDefinition axisTitleFontDefault = new FontDefinition("SansSerif", FontWeight.BOLD, 12.0,
	    FontPosture.REGULAR, Color.BLACK);
    static final FontDefinition axisLabelFontDefault = new FontDefinition("SansSerif", FontWeight.NORMAL, 10.0,
	    FontPosture.REGULAR, Color.BLACK);
    static final FontDefinition dataLabelFontDefault = new FontDefinition("SansSerif", FontWeight.NORMAL, 10.0,
	    FontPosture.REGULAR, Color.BLACK);

    public static final PropertyDefinition<FontDefinition> TITLE_FONT = new SimplePropertyDefinition<FontDefinition>(
	    PROPERTY_BASE + ".title.font", "Chart Title Font", "Font to be used for chart titles.",
	    FontDefinition.class, titleFontDefault);

    public static final PropertyDefinition<FontDefinition> SUBTITLE_FONT = new SimplePropertyDefinition<FontDefinition>(
	    PROPERTY_BASE + ".subtitle.font", "Chart Subtitle Font", "Font to be used for chart subtitles.",
	    FontDefinition.class, subTitleFontDefault);

    public static final PropertyDefinition<FontDefinition> AXIS_TITLE_FONT = new SimplePropertyDefinition<FontDefinition>(
	    PROPERTY_BASE + ".chart.axis.title.font", "Axis Title Font", "Font to be used for chart axis titles.",
	    FontDefinition.class, axisTitleFontDefault);

    public static final PropertyDefinition<FontDefinition> AXIS_LABEL_FONT = new SimplePropertyDefinition<FontDefinition>(
	    PROPERTY_BASE + ".chart.axis.label.font", "Axis Label Font", "Font to be used for chart axis labels.",
	    FontDefinition.class, axisLabelFontDefault);

    public static final PropertyDefinition<FontDefinition> DATA_LABEL_FONT = new SimplePropertyDefinition<FontDefinition>(
	    PROPERTY_BASE + ".chart.data.label.font", "Data Label Font", "Font to be used for chart data labels.",
	    FontDefinition.class, dataLabelFontDefault);

}
