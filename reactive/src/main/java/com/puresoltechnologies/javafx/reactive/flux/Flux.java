package com.puresoltechnologies.javafx.reactive.flux;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.SubmissionPublisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Flux {

    private static final Logger logger = LoggerFactory.getLogger(Flux.class);

    private static final Map<Class<?>, AbstractStore<?, ?>> stores = new HashMap<>();
    private static boolean initialized = false;
    private static final SubmissionPublisher<Payload<?, ?>> dispatcher = new SubmissionPublisher<>();

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
	@SuppressWarnings("rawtypes")
	ServiceLoader<Store> serviceLoader = ServiceLoader.load(Store.class);
	serviceLoader.stream() //
		.map(loader -> loader.get()) //
		.filter(service -> {
		    if (AbstractStore.class.isAssignableFrom(service.getClass())) {
			return true;
		    } else {
			logger.warn("Provided store '" + service.getClass() + "' does not implement AbstractStore.");
			return false;
		    }
		}) //
		.map(store -> (AbstractStore<?, ?>) store) //
		.forEach(store -> {
		    stores.put(store.getClass(), store);
		    dispatcher.subscribe(store);
		});
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
    public static synchronized <A extends Enum<A>, D> void registerStore(AbstractStore<A, D> store) {
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

    public static synchronized <A extends Enum<A>, D> Store<A, D> getStore(Class<? extends Store<A, D>> clazz) {
	@SuppressWarnings("unchecked")
	Store<A, D> store = (Store<A, D>) stores.get(clazz);
	return store;
    }

    public static synchronized <A extends Enum<A>, D> void dispatch(A action, D data) {
	dispatcher.submit(new Payload<>(action, data));
    }

    /**
     * Private constructor to avoid instantiation.
     */
    private Flux() {
    }
}
