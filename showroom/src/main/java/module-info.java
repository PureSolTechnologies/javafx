module com.puresoltechnologies.javafx.showroom {

    requires transitive com.puresoltechnologies.javafx.charts;
    requires transitive com.puresoltechnologies.javafx.extensions;
    requires transitive com.puresoltechnologies.javafx.perspectives;
    requires transitive com.puresoltechnologies.javafx.preferences;
    requires transitive com.puresoltechnologies.javafx.reactive;
    requires transitive com.puresoltechnologies.javafx.services;
    requires transitive com.puresoltechnologies.javafx.tasks;
    requires com.puresoltechnologies.javafx.utils;
    requires transitive com.puresoltechnologies.javafx.workspaces;

    requires transitive com.puresoltechnologies.graphs.graph;
    requires transitive com.puresoltechnologies.streaming.iterators;

    requires transitive javafx.controls;
    requires transitive javafx.graphics;

    exports com.puresoltechnologies.javafx.showroom;
    exports com.puresoltechnologies.javafx.showroom.parts;
    exports com.puresoltechnologies.javafx.showroom.services;

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
    provides com.puresoltechnologies.javafx.services.Service
	    with com.puresoltechnologies.javafx.showroom.services.ShowRoomService;

}