module com.puresoltechnologies.javafx.services {

    requires com.puresoltechnologies.javafx.extensions;
    requires com.puresoltechnologies.javafx.utils;

    requires javafx.graphics;
    requires javafx.controls;
    requires slf4j.api;

    exports com.puresoltechnologies.javafx.services;
    exports com.puresoltechnologies.javafx.services.dialogs;
    exports com.puresoltechnologies.javafx.services.menu;

    uses com.puresoltechnologies.javafx.services.Service;

}