package com.puresoltechnologies.javafx.reactive;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a broker implementation for a Publish-Subscribe-Pattern.
 *
 * @author Rick-Rainer Ludwig
 */
public class MessageBroker {

    private static final Logger logger = LoggerFactory.getLogger(MessageBroker.class);

    private static MessageBroker instance = null;

    /**
     * This method initializes the broker.
     *
     * @throws IllegalStateException is thrown in case the broker was already
     *                               initialized.
     */
    public static void initialize() {
	if (instance != null) {
	    throw new IllegalStateException("Broker was initialized already.");
	}
	instance = new MessageBroker();
    }

    /**
     * This method shuts the broker down.
     *
     * @throws IllegalStateException is thrown in case the broker was already
     *                               shutdown or not initialized.
     */
    public static void shutdown() {
	instance.close();
	instance = null;
    }

    /**
     * This method is used to check the initialization state of this broker.
     *
     * @return <code>true</code> is returned in case the Broker is initialized.
     *         <code>false</code> is returned otherwise.
     */
    public static boolean isInitialized() {
	return instance != null;
    }

    public static MessageBroker getStore() {
	return instance;
    }

    private final Map<Topic<?>, SubmissionPublisher<?>> subjects = new HashMap<>();

    private final ExecutorService executorService;

    private MessageBroker() {
	executorService = Executors.newCachedThreadPool(new ThreadFactory() {
	    private final AtomicInteger id = new AtomicInteger(0);

	    @Override
	    public Thread newThread(Runnable target) {
		return new Thread(target, "FluxStore-thread-" + id.incrementAndGet());
	    }
	});
    }

    private void close() {
	subjects.values().forEach(subject -> {
	    subject.close();
	});
	executorService.shutdownNow();
	subjects.clear();
    }

    public <T> void publish(Topic<T> topic, T message) {
	SubmissionPublisher<T> subject = assurePresenceOfTopic(topic);
	subject.submit(message);
    }

    public <T> void subscribe(Topic<T> topic, Subscriber<T> subscriber) {
	SubmissionPublisher<T> subject = assurePresenceOfTopic(topic);
	subject.subscribe(subscriber);
    }

    @SuppressWarnings("unchecked")
    private <T> SubmissionPublisher<T> assurePresenceOfTopic(Topic<T> topic) {
	SubmissionPublisher<T> subject = (SubmissionPublisher<T>) subjects.get(topic);
	if (subject == null) {
	    synchronized (subjects) {
		subject = (SubmissionPublisher<T>) subjects.get(topic);
		if (subject == null) {
		    subject = new SubmissionPublisher<>(executorService, topic.getBufferSize());
		    subjects.put(topic, subject);
		}
	    }
	}
	return subject;
    }

}
