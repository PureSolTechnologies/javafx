module com.puresoltechnologies.javafx.extensions {

    requires com.puresoltechnologies.javafx.utils;

    requires com.github.spotbugs.annotations;
    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    requires slf4j.api;
    requires jdk.xml.dom;

    exports com.puresoltechnologies.javafx.extensions;
    exports com.puresoltechnologies.javafx.extensions.dialogs;
    exports com.puresoltechnologies.javafx.extensions.dialogs.about;
    exports com.puresoltechnologies.javafx.extensions.dialogs.wizard;
    exports com.puresoltechnologies.javafx.extensions.dialogs.procedure;
    exports com.puresoltechnologies.javafx.extensions.fonts;
    exports com.puresoltechnologies.javafx.extensions.menu;
    exports com.puresoltechnologies.javafx.extensions.properties;
    exports com.puresoltechnologies.javafx.extensions.splash;
    exports com.puresoltechnologies.javafx.extensions.status;
    exports com.puresoltechnologies.javafx.extensions.toolbar;

    opens com.puresoltechnologies.javafx.extensions.icons;
    opens com.puresoltechnologies.javafx.extensions.icons.FatCow_Icons16x16;
    opens com.puresoltechnologies.javafx.extensions.icons.FatCow_Icons32x32;

    uses com.puresoltechnologies.javafx.extensions.dialogs.about.AboutDialogContribution;
}
