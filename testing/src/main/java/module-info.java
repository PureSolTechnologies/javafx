module com.puresoltechnologies.javafx.testing {

    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    requires transitive javafx.swing;
    requires org.junit.jupiter.api;
    requires awaitility;
    requires hamcrest.core;
    requires com.puresoltechnologies.graphs.trees;
    requires java.desktop;
    requires com.puresoltechnologies.javafx.utils;

    exports com.puresoltechnologies.javafx.testing;
    exports com.puresoltechnologies.javafx.testing.mouse;
    exports com.puresoltechnologies.javafx.testing.select;
    exports com.puresoltechnologies.javafx.testing.utils;

}
