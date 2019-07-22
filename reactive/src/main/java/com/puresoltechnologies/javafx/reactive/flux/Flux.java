package com.puresoltechnologies.javafx.reactive.flux;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.puresoltechnologies.javafx.reactive.MessageBroker;
import com.puresoltechnologies.javafx.reactive.Topic;

public class Flux {

    private static final Logger logger = LoggerFactory.getLogger(Flux.class);

    public static final Topic<Payload> FLUX_TOPIC = new Topic<>(Flux.class.getName(), Payload.class);

    private static final Map<Class<?>, Store<?, ?>> stores = new HashMap<>();
    private static final Map<Class<?>, Topic<Payload>> topics = new HashMap<>();
    private static boolean initialized = false;

    /**
     * This method initializes the broker.
     *
     * @throws IllegalStateException is thrown in case the broker was already
     *                               initialized.
     */
    public static synchronized void initialize() {
	if (initialized) {
	    throw new IllegalStateException("Flux was already initialized.");
	}
	if (!MessageBroker.isInitialized()) {
	    throw new IllegalStateException(
		    "MessageBroker was not initialized, yet. It needs to be initialized before Flux.");
	}

	@SuppressWarnings("rawtypes")
	ServiceLoader<Store> serviceLoader = ServiceLoader.load(Store.class);
	serviceLoader.stream() //
		.map(loader -> loader.get()) //
		.forEach(store -> stores.put(store.getClass(), store));
	initialized = true;
    }

    /**
     * This method registers a new store to Flux. Flux needs to be initialized. When
     * Flux is {@link #shutdown()}, this registration is lost and an
     * {@link #initialize()} will not bring it back automatically.
     *
     * @param <A>   is the {@link Enum} type of the action. Multiple stores with
     *              same actions can be restored.
     * @param <D>   is the type of the message.
     * @param store an object to be registered.
     */
    public static synchronized <A extends Enum<A>, D> void registerStore(Store<A, D> store) {
	assertInitialized();
	stores.put(store.getClass(), store);
    }

    /**
     * This method shuts the broker down.
     *
     * @throws IllegalStateException is thrown in case the broker was already
     *                               shutdown or not initialized.
     */
    public static synchronized void shutdown() {
	assertInitialized();
	initialized = false;
	stores.clear();
    }

    private static void assertInitialized() {
	if (!initialized) {
	    throw new IllegalStateException("Flux was not initialized, yet.");
	}
    }

    /**
     * This method is used to check the initialization state of this broker.
     *
     * @return <code>true</code> is returned in case the Broker is initialized.
     *         <code>false</code> is returned otherwise.
     */
    public static synchronized boolean isInitialized() {
	return initialized;
    }

    public static synchronized <A extends Enum<A>, D, S extends Store<A, D>> S getStore(Class<S> clazz) {
	assertInitialized();
	@SuppressWarnings("unchecked")
	S store = (S) stores.get(clazz);
	return store;
    }

    public static synchronized <A extends Enum<A>, D> void dispatch(A action, D data) {
	assertInitialized();
	MessageBroker broker = MessageBroker.getBroker();
	broker.publish(FLUX_TOPIC, new Payload(action, data));
    }

    /**
     * Private constructor to avoid instantiation.
     */
    private Flux() {
    }
}
