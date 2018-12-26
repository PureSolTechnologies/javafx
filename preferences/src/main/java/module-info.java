module com.puresoltechnologies.javafx.preferences {

    requires com.puresoltechnologies.javafx.extensions;
    requires com.puresoltechnologies.javafx.utils;

    requires javafx.controls;

    exports com.puresoltechnologies.javafx.preferences;
    exports com.puresoltechnologies.javafx.preferences.dialogs;
    exports com.puresoltechnologies.javafx.preferences.menu;

    opens com.puresoltechnologies.javafx.preferences.icons.FatCow_Icons16x16;
    opens com.puresoltechnologies.javafx.preferences.icons.FatCow_Icons32x32;

    uses com.puresoltechnologies.javafx.preferences.dialogs.PreferencesPage;
}
