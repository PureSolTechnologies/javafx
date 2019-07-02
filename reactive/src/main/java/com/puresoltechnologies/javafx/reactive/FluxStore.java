package com.puresoltechnologies.javafx.reactive;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.SubmissionPublisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FluxStore {

    private static final Logger logger = LoggerFactory.getLogger(FluxStore.class);

    private final Map<Topic<?>, SubmissionPublisher<?>> subjects = new HashMap<>();
    private final Map<Topic<?>, Object> last = new HashMap<>();

    FluxStore() {
    }

    public <T> void publish(Topic<T> topic, T message) {
	SubmissionPublisher<T> subject = assurePresenceOfTopic(topic);
	last.put(topic, message);
	subject.submit(message);
    }

    public <T> SubmissionPublisher<T> forTopic(Topic<T> topic) {
	return assurePresenceOfTopic(topic);
    }

    public <T> void subscribe(Topic<T> topic, Subscriber<T> subscriber) {
	SubmissionPublisher<T> subject = forTopic(topic);
	@SuppressWarnings("unchecked")
	T initialValue = (T) last.get(topic);
	if (initialValue != null) {
	    try {
		subscriber.onNext(initialValue);
		subject.subscribe(subscriber);
	    } catch (Exception e) {
		logger.warn(
			"Could not subscribe observer '" + subscriber.getClass() + "' to topic '" + topic.getId() + "'.");
		return;
	    }
	} else {
	    subject.subscribe(subscriber);
	}
    }

    @SuppressWarnings("unchecked")
    private <T> SubmissionPublisher<T> assurePresenceOfTopic(Topic<T> topic) {
	SubmissionPublisher<T> subject = (SubmissionPublisher<T>) subjects.get(topic);
	if (subject == null) {
	    synchronized (subjects) {
		subject = (SubmissionPublisher<T>) subjects.get(topic);
		if (subject == null) {
		    subject = new SubmissionPublisher<>(Executors.newCachedThreadPool(), 16);
		    subjects.put(topic, subject);
		}
	    }
	}
	return subject;
    }

}
