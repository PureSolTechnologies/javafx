module com.puresoltechnologies.javafx.workspaces {

    requires com.puresoltechnologies.javafx.utils;

    requires java.prefs;
    requires transitive javafx.controls;

    exports com.puresoltechnologies.javafx.workspaces;
    exports com.puresoltechnologies.javafx.workspaces.dialogs;
    exports com.puresoltechnologies.javafx.workspaces.menu;

    opens com.puresoltechnologies.javafx.workspaces.icons.FatCow_Icons16x16;
    opens com.puresoltechnologies.javafx.workspaces.icons.FatCow_Icons32x32;
}
