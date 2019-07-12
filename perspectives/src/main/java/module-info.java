module com.puresoltechnologies.javafx.perspectives {

    requires transitive com.puresoltechnologies.javafx.extensions;
    requires transitive com.puresoltechnologies.javafx.preferences;
    requires transitive com.puresoltechnologies.javafx.reactive;
    requires transitive com.puresoltechnologies.javafx.tasks;
    requires com.puresoltechnologies.javafx.utils;

    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    requires transitive javafx.web;
    requires slf4j.api;

    exports com.puresoltechnologies.javafx.perspectives;
    exports com.puresoltechnologies.javafx.perspectives.dialogs;
    exports com.puresoltechnologies.javafx.perspectives.menu;
    exports com.puresoltechnologies.javafx.perspectives.parts;
    exports com.puresoltechnologies.javafx.perspectives.preferences;

    provides com.puresoltechnologies.javafx.perspectives.parts.Part
	    with com.puresoltechnologies.javafx.perspectives.parts.TaskProgressViewer, //
	    com.puresoltechnologies.javafx.perspectives.parts.BrowserPart;
    provides com.puresoltechnologies.javafx.preferences.dialogs.PreferencesPage
	    with com.puresoltechnologies.javafx.perspectives.preferences.PerspectivesPreferencesPage;

    opens com.puresoltechnologies.javafx.perspectives.icons.FatCow_Icons16x16;
    opens com.puresoltechnologies.javafx.perspectives.icons.FatCow_Icons32x32;

    uses com.puresoltechnologies.javafx.perspectives.Perspective;
    uses com.puresoltechnologies.javafx.perspectives.parts.Part;
}
