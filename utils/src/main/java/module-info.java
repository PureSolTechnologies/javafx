module com.puresoltechnologies.javafx.utils {

    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    requires transitive javafx.web;
    requires com.github.spotbugs.annotations;
    requires slf4j.api;
    requires jdk.xml.dom;

    exports com.puresoltechnologies.javafx.utils;
    exports com.puresoltechnologies.javafx.utils.graphics;
    exports com.puresoltechnologies.javafx.utils.web;

}
