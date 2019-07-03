package com.puresoltechnologies.javafx.reactive;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.SubmissionPublisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FluxStore implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(FluxStore.class);

    private final Map<Topic<?>, SubmissionPublisher<?>> subjects = new HashMap<>();

    FluxStore() {
    }

    @Override
    public void close() {
	subjects.values().forEach(subject -> {
	    /*
	     * Workaround: The SubmissionPublisher does not shutdown the ExecuterServices in
	     * the background. Because of that, the application shutdown is delayed.
	     *
	     * In the next two lines, the executor is retrieved and explicitly shutdown.
	     */
	    Executor executor = subject.getExecutor();
	    if (ExecutorService.class.isAssignableFrom(executor.getClass())) {
		((ExecutorService) executor).shutdownNow();
	    }
	    subject.close();
	});
	subjects.clear();
    }

    public <T> void publish(Topic<T> topic, T message) {
	SubmissionPublisher<T> subject = assurePresenceOfTopic(topic);
	subject.submit(message);
    }

    public <T> SubmissionPublisher<T> forTopic(Topic<T> topic) {
	return assurePresenceOfTopic(topic);
    }

    public <T> void subscribe(Topic<T> topic, Subscriber<T> subscriber) {
	SubmissionPublisher<T> subject = forTopic(topic);
	subject.subscribe(subscriber);
    }

    @SuppressWarnings("unchecked")
    private <T> SubmissionPublisher<T> assurePresenceOfTopic(Topic<T> topic) {
	SubmissionPublisher<T> subject = (SubmissionPublisher<T>) subjects.get(topic);
	if (subject == null) {
	    synchronized (subjects) {
		subject = (SubmissionPublisher<T>) subjects.get(topic);
		if (subject == null) {
		    subject = new SubmissionPublisher<>(Executors.newCachedThreadPool(), 1);
		    subjects.put(topic, subject);
		}
	    }
	}
	return subject;
    }

}
