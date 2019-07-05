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

public class FluxStore implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(FluxStore.class);

    private final Map<Topic<?>, SubmissionPublisher<?>> subjects = new HashMap<>();

    private final ExecutorService executorService;

    FluxStore() {
	executorService = Executors.newCachedThreadPool(new ThreadFactory() {
	    private final AtomicInteger id = new AtomicInteger(0);

	    @Override
	    public Thread newThread(Runnable target) {
		return new Thread(target, "FluxStore-thread-" + id.incrementAndGet());
	    }
	});
    }

    @Override
    public void close() {
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
		    subject = new SubmissionPublisher<>(executorService, 1);
		    subjects.put(topic, subject);
		}
	    }
	}
	return subject;
    }

}
