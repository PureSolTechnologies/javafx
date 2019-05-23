module com.puresoltechnologies.javafx.showroom {

    requires com.puresoltechnologies.javafx.charts;
    requires com.puresoltechnologies.javafx.extensions;
    requires transitive com.puresoltechnologies.javafx.perspectives;
    requires com.puresoltechnologies.javafx.preferences;
    requires com.puresoltechnologies.javafx.reactive;
    requires com.puresoltechnologies.javafx.tasks;
    requires com.puresoltechnologies.javafx.utils;
    requires com.puresoltechnologies.javafx.workspaces;

    requires com.puresoltechnologies.graphs.graph;
    requires com.puresoltechnologies.streaming.iterators;

    requires javafx.controls;
    requires javafx.graphics;

    requires java.management;

    exports com.puresoltechnologies.javafx.showroom;
    exports com.puresoltechnologies.javafx.showroom.parts;

    opens com.puresoltechnologies.javafx.showroom.icons.FatCow_Icons16x16;
    opens com.puresoltechnologies.javafx.showroom.icons.FatCow_Icons32x32;
    opens com.puresoltechnologies.javafx.showroom.splash;

    provides com.puresoltechnologies.javafx.perspectives.Perspective
	    with com.puresoltechnologies.javafx.showroom.perspectives.ChartsPerspective,
	    com.puresoltechnologies.javafx.showroom.perspectives.StartPerspective,
	    com.puresoltechnologies.javafx.showroom.perspectives.TasksPerspective;
    provides com.puresoltechnologies.javafx.perspectives.parts.Part
	    with com.puresoltechnologies.javafx.showroom.parts.StartSampleTasksViewer,
	    com.puresoltechnologies.javafx.showroom.parts.SampleTaskResultsViewer,
	    com.puresoltechnologies.javafx.showroom.parts.BoxPlotSampleViewer,
	    com.puresoltechnologies.javafx.showroom.parts.TimeseriesPlotSampleViewer,
	    com.puresoltechnologies.javafx.showroom.parts.XYPlotSampleViewer,
	    com.puresoltechnologies.javafx.showroom.parts.OhlcPlotSampleViewer,
	    com.puresoltechnologies.javafx.showroom.parts.WizardExamplePart,
	    com.puresoltechnologies.javafx.showroom.parts.StepInterfaceExamplePart;
}