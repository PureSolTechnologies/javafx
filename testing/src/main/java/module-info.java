module com.puresoltechnologies.javafx.testing {

    exports com.puresoltechnologies.javafx.testing;
    exports com.puresoltechnologies.javafx.testing.mouse;
    exports com.puresoltechnologies.javafx.testing.select;
    exports com.puresoltechnologies.javafx.testing.utils;

    requires javafx.graphics;
    requires javafx.controls;
    requires org.junit.jupiter.api;
    requires awaitility;
    requires hamcrest.core;

}
