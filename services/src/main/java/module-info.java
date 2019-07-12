module com.puresoltechnologies.javafx.services {

    requires transitive com.puresoltechnologies.javafx.extensions;
    requires com.puresoltechnologies.javafx.utils;

    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    requires slf4j.api;

    exports com.puresoltechnologies.javafx.services;
    exports com.puresoltechnologies.javafx.services.dialogs;
    exports com.puresoltechnologies.javafx.services.menu;

    uses com.puresoltechnologies.javafx.services.Service;

}