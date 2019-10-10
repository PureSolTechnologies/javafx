module com.puresoltechnologies.javafx.reactive {

    requires com.puresoltechnologies.javafx.utils;

    requires slf4j.api;

    exports com.puresoltechnologies.javafx.reactive;
    exports com.puresoltechnologies.javafx.reactive.flux;

    uses com.puresoltechnologies.javafx.reactive.flux.Store;
}
