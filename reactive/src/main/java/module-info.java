module com.puresoltechnologies.javafx.reactive {

    requires slf4j.api;

    exports com.puresoltechnologies.javafx.reactive;
    exports com.puresoltechnologies.javafx.reactive.flux;

    uses com.puresoltechnologies.javafx.reactive.flux.Store;
}
