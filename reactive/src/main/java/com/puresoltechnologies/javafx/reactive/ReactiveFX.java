package com.puresoltechnologies.javafx.reactive;

public class ReactiveFX {

    private static ReactiveFX instance = null;

    public static void initialize() {
	if (instance != null) {
	    throw new IllegalStateException("CryptoCurrenciesClient was initialized already.");
	}
	instance = new ReactiveFX();
    }

    public static void shutdown() {
	instance = null;
    }

    public static FluxStore getStore() {
	return instance.store;
    }

    private final FluxStore store = new FluxStore();

}
