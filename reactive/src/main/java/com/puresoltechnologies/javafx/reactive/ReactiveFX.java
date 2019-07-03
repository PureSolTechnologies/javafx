package com.puresoltechnologies.javafx.reactive;

public class ReactiveFX implements AutoCloseable {

    private static ReactiveFX instance = null;

    public static void initialize() {
	if (instance != null) {
	    throw new IllegalStateException("CryptoCurrenciesClient was initialized already.");
	}
	instance = new ReactiveFX();
    }

    public static void shutdown() {
	instance.close();
	instance = null;
    }

    public static FluxStore getStore() {
	return instance.store;
    }

    private final FluxStore store = new FluxStore();

    /**
     * Private constructor to avoid accidental instantiation and to ensure
     * singleton.
     */
    private ReactiveFX() {
    }

    @Override
    public void close() {
	store.close();
    }
}
