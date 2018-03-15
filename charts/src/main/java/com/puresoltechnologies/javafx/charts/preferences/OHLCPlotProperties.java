package com.puresoltechnologies.javafx.charts.preferences;

import com.puresoltechnologies.javafx.charts.plots.ohlc.OHLCPlot;
import com.puresoltechnologies.javafx.extensions.properties.PropertyDefinition;
import com.puresoltechnologies.javafx.extensions.properties.SimplePropertyDefinition;

import javafx.scene.paint.Color;

/**
 * This interface contains all properties for the {@link OHLCPlot}.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public interface OHLCPlotProperties {

    static final String PROPERTY_BASE = ChartsProperties.class.getPackage().getName();

    static final Color upwardTrendColorDefault = Color.LIGHTSEAGREEN;
    static final Color downwardTrendColorDefault = Color.LIGHTCORAL;

    public static final PropertyDefinition<Color> UPWARD_TREND_COLOR = new SimplePropertyDefinition<Color>(
	    PROPERTY_BASE + ".plot.ohlc.color.upward", "Upward Trend Color",
	    "Color to be used for OHLC boxed in cases of upward trends.", Color.class, upwardTrendColorDefault);

    public static final PropertyDefinition<Color> DOWNWARD_TREND_COLOR = new SimplePropertyDefinition<Color>(
	    PROPERTY_BASE + ".plot.ohlc.color.downward", "Downward Trend Color",
	    "Color to be used for OHLC boxed in cases of downward trends.", Color.class, downwardTrendColorDefault);
}
