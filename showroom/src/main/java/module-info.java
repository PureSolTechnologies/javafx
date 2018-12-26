module com.puresoltechnologies.javafx.showcase {

    requires com.puresoltechnologies.javafx.extensions;
    requires com.puresoltechnologies.javafx.perspectives;
    requires com.puresoltechnologies.javafx.preferences;
    requires com.puresoltechnologies.javafx.reactive;
    requires com.puresoltechnologies.javafx.tasks;
    requires com.puresoltechnologies.javafx.utils;
    requires com.puresoltechnologies.javafx.workspaces;

    requires javafx.controls;
    requires javafx.graphics;

    exports com.puresoltechnologies.javafx.showroom;

    opens com.puresoltechnologies.javafx.showroom.icons.FatCow_Icons16x16;
    opens com.puresoltechnologies.javafx.showroom.icons.FatCow_Icons32x32;
    opens com.puresoltechnologies.javafx.showroom.splash;

    provides com.puresoltechnologies.javafx.perspectives.Perspective
	    with com.puresoltechnologies.javafx.showroom.perspectives.StartPerspective;
    // provides com.puresoltechnologies.javafx.perspectives.parts.Part with ;
}