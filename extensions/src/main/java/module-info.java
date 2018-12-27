module com.puresoltechnologies.javafx.extensions {

    requires com.puresoltechnologies.javafx.utils;
    requires transitive javafx.controls;

    exports com.puresoltechnologies.javafx.extensions;
    exports com.puresoltechnologies.javafx.extensions.dialogs;
    exports com.puresoltechnologies.javafx.extensions.fonts;
    exports com.puresoltechnologies.javafx.extensions.menu;
    exports com.puresoltechnologies.javafx.extensions.properties;
    exports com.puresoltechnologies.javafx.extensions.splash;

    opens com.puresoltechnologies.javafx.extensions.icons.FatCow_Icons16x16;
    opens com.puresoltechnologies.javafx.extensions.icons.FatCow_Icons32x32;

    uses com.puresoltechnologies.javafx.extensions.dialogs.AboutDialogContribution;
}
