module com.puresoltechnologies.javafx.charts {

    requires com.puresoltechnologies.javafx.extensions;
    requires com.puresoltechnologies.javafx.preferences;
    requires transitive com.puresoltechnologies.graphs.trees;
    requires com.puresoltechnologies.javafx.utils;

    requires java.desktop;
    requires transitive javafx.graphics;

    exports com.puresoltechnologies.javafx.charts;
    exports com.puresoltechnologies.javafx.charts.axes;
    exports com.puresoltechnologies.javafx.charts.meter;
    exports com.puresoltechnologies.javafx.charts.plots;
    exports com.puresoltechnologies.javafx.charts.plots.box;
    exports com.puresoltechnologies.javafx.charts.plots.ohlc;
    exports com.puresoltechnologies.javafx.charts.plots.timeseries;
    exports com.puresoltechnologies.javafx.charts.plots.xy;
    exports com.puresoltechnologies.javafx.charts.tree;

    provides com.puresoltechnologies.javafx.preferences.dialogs.PreferencesPage
	    with com.puresoltechnologies.javafx.charts.preferences.ChartPreferencesPage,
	    com.puresoltechnologies.javafx.charts.preferences.OHLCPlotPreferencesPage;

    opens com.puresoltechnologies.javafx.charts.icons.FatCow_Icons16x16;
}
