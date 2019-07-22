package com.puresoltechnologies.javafx.reactive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This is a broker implementation for a Publish-Subscribe-Pattern.
 *
 * @author Rick-Rainer Ludwig
 */
public class MessageBroker {

    private static MessageBroker instance = null;

    /**
     * This method initializes the broker.
     *
     * @throws IllegalStateException is thrown in case the broker was already
     *                               initialized.
     */
    public static synchronized void initialize() {
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
    public static synchronized void shutdown() {
	instance.close();
	instance = null;
    }

    /**
     * This method is used to check the initialization state of this broker.
     *
     * @return <code>true</code> is returned in case the Broker is initialized.
     *         <code>false</code> is returned otherwise.
     */
    public static synchronized boolean isInitialized() {
	return instance != null;
    }

    public static synchronized MessageBroker getBroker() {
	return instance;
    }

    private class Subject<T> implements AutoCloseable {
	private final SubmissionPublisher<T> submissionPublisher;
	private final List<T> lastItems;

	private Subject(Topic<T> topic) {
	    this.submissionPublisher = new SubmissionPublisher<>(executorService, topic.getBufferSize());
	    this.lastItems = new ArrayList<>();
	}

	@Override
	public void close() {
	    submissionPublisher.close();
	}
    }

    private final Map<Topic<?>, Subject<?>> subjects = new HashMap<>();

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
	Subject<T> subject = assurePresenceOfTopic(topic);
	subject.submissionPublisher.submit(message);
	List<T> lastItems = subject.lastItems;
	lastItems.add(message);
	if (lastItems.size() > topic.getHistorySize()) {
	    lastItems.remove(0);
	}
    }

    public <T> void subscribe(Topic<T> topic, Subscriber<T> subscriber) {
	Subject<T> subject = assurePresenceOfTopic(topic);
	subject.submissionPublisher.subscribe(subscriber);
	subject.lastItems.stream().forEach(message -> subscriber.onNext(message));
    }

    @SuppressWarnings("unchecked")
    private <T> Subject<T> assurePresenceOfTopic(Topic<T> topic) {
	Subject<T> subject = (Subject<T>) subjects.get(topic);
	if (subject == null) {
	    synchronized (subjects) {
		subject = (Subject<T>) subjects.get(topic);
		if (subject == null) {
		    subject = new Subject<>(topic);
		    subjects.put(topic, subject);
		}
	    }
	}
	return subject;
    }

}
