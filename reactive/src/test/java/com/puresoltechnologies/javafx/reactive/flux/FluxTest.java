package com.puresoltechnologies.javafx.reactive.flux;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puresoltechnologies.javafx.reactive.MessageBroker;

public class FluxTest {

    @BeforeEach
    public void initialize() {
	assertFalse(Flux.isInitialized());
	MessageBroker.initialize();
	Flux.initialize();
	assertTrue(Flux.isInitialized());
    }

    @AfterEach
    public void shutdown() {
	assertTrue(Flux.isInitialized());
	Flux.shutdown();
	MessageBroker.shutdown();
	assertFalse(Flux.isInitialized());
    }

    @Test
    public void testWithoutStores() {
	// Simple smoke test. Intentionally left blank.
    }

    /**
     * A double initialization is meant to throw an {@link IllegalStateException}.
     * But, the status is still initialized.
     */
    @Test
    public void testMultiInitialize() {
	assertThrows(IllegalStateException.class, () -> Flux.initialize());
	assertTrue(Flux.isInitialized());
    }

    /**
     * A double shut down is meant to throw an {@link IllegalStateException}. But,
     * the status is still uninitialized.
     */
    @Test
    public void testMultiShutdown() {
	assertTrue(Flux.isInitialized());
	Flux.shutdown();
	assertFalse(Flux.isInitialized());
	assertThrows(IllegalStateException.class, () -> Flux.shutdown());
	assertFalse(Flux.isInitialized());
	/*
	 * We need to initialize again, to satisfy @AfterAll shutdown method.
	 */
	Flux.initialize();
	assertTrue(Flux.isInitialized());
    }

    private enum Actions {
	ACTION_1;
    }

    private static class TestStore extends Store<Actions, String> {

	@Override
	protected void handle(Payload payload) {
	    // TODO Auto-generated method stub
	}

    }

    @Test
    public void testRegisterStore() {
	TestStore store = new TestStore();
	Flux.registerStore(store);
	Store<Actions, String> foundStore = Flux.getStore(TestStore.class);
	assertSame(store, foundStore);
    }

    @Test
    public void testRegisteredStoreIsGoneAfterReInitialization() {
	TestStore store = new TestStore();
	Flux.registerStore(store);
	Store<Actions, String> foundStore = Flux.getStore(TestStore.class);
	assertSame(store, foundStore);
	Flux.shutdown();
	Flux.initialize();
	foundStore = Flux.getStore(TestStore.class);
	assertNull(foundStore);
    }
}
