package com.puresoltechnologies.javafx.reactive;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class FluxStore {

    private static final Logger logger = LoggerFactory.getLogger(FluxStore.class);

    private final Map<Topic<?>, Subject<?>> subjects = new HashMap<>();
    private final Map<Topic<?>, Object> last = new HashMap<>();

    FluxStore() {
    }

    public <T> void publish(Topic<T> topic, T message) {
	Subject<T> subject = assurePresenceOfTopic(topic);
	last.put(topic, message);
	subject.onNext(message);
    }

    public <T> Disposable subscribe(Topic<T> topic, Consumer<T> observer) {
	Subject<T> subject = assurePresenceOfTopic(topic);
	@SuppressWarnings("unchecked")
	T initialValue = (T) last.get(topic);
	if (initialValue != null) {
	    try {
		observer.accept(initialValue);
		return subject.subscribe(observer);
	    } catch (Exception e) {
		logger.warn(
			"Could not subscribe observer '" + observer.getClass() + "' to topic '" + topic.getId() + "'.");
		return null;
	    }
	} else {
	    return subject.subscribe(observer);
	}
    }

    @SuppressWarnings("unchecked")
    private <T> Subject<T> assurePresenceOfTopic(Topic<T> topic) {
	Subject<T> subject = (Subject<T>) subjects.get(topic);
	if (subject == null) {
	    synchronized (subjects) {
		subject = (Subject<T>) subjects.get(topic);
		if (subject == null) {
		    subject = PublishSubject.create();
		    subjects.put(topic, subject);
		}
	    }
	}
	return subject;
    }

}
