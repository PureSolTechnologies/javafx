module com.puresoltechnologies.javafx.perspectives {

    requires com.puresoltechnologies.javafx.extensions;
    requires com.puresoltechnologies.javafx.preferences;
    requires com.puresoltechnologies.javafx.reactive;
    requires transitive com.puresoltechnologies.javafx.tasks;
    requires com.puresoltechnologies.javafx.utils;

    requires io.reactivex.rxjava2;
    requires transitive javafx.controls;
    requires transitive javafx.graphics;

    exports com.puresoltechnologies.javafx.perspectives;
    exports com.puresoltechnologies.javafx.perspectives.dialogs;
    exports com.puresoltechnologies.javafx.perspectives.menu;
    exports com.puresoltechnologies.javafx.perspectives.parts;
    exports com.puresoltechnologies.javafx.perspectives.preferences;

    provides com.puresoltechnologies.javafx.perspectives.parts.Part
	    with com.puresoltechnologies.javafx.perspectives.parts.TaskProgressViewer;
    provides com.puresoltechnologies.javafx.preferences.dialogs.PreferencesPage
	    with com.puresoltechnologies.javafx.perspectives.preferences.PerspectivesPreferencesPage;

    opens com.puresoltechnologies.javafx.perspectives.icons.FatCow_Icons16x16;
    opens com.puresoltechnologies.javafx.perspectives.icons.FatCow_Icons32x32;

    uses com.puresoltechnologies.javafx.perspectives.Perspective;
    uses com.puresoltechnologies.javafx.perspectives.parts.Part;
}
