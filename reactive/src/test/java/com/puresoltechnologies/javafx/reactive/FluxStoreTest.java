package com.puresoltechnologies.javafx.reactive;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class FluxStoreTest {

    private final Topic<Integer> topic = new Topic<>("test.counter", Integer.class);

    @BeforeEach
    public void initialize() {
	ReactiveFX.initialize();
    }

    @AfterEach
    public void shutdown() {
	ReactiveFX.shutdown();
    }

    private Subscriber<Integer> createSubscriber() {
	@SuppressWarnings("unchecked")
	Subscriber<Integer> subscriber = mock(Subscriber.class);
	Mockito.doAnswer(invocation -> {
	    Subscription subscription = invocation.getArgument(0);
	    subscription.request(Long.MAX_VALUE);
	    return null;
	}).when(subscriber).onSubscribe(any());
	return subscriber;
    }

    @Test
    public void testSingleSubscriber() throws InterruptedException {
	Subscriber<Integer> subscriber = createSubscriber();

	FluxStore store = ReactiveFX.getStore();
	store.subscribe(topic, subscriber);

	store.publish(topic, 1);
	Awaitility.await("Wait for delivery of message.") //
		.atMost(1, TimeUnit.SECONDS)//
		.pollInterval(10, TimeUnit.MILLISECONDS)//
		.until(() -> {
		    try {
			verify(subscriber, times(1)).onNext(1);
			return true;
		    } catch (Throwable e) {
			return false;
		    }
		});
    }

    @Test
    public void testMultipleSubscribers() throws InterruptedException {
	Subscriber<Integer> subscriber1 = createSubscriber();
	Subscriber<Integer> subscriber2 = createSubscriber();
	Subscriber<Integer> subscriber3 = createSubscriber();

	FluxStore store = ReactiveFX.getStore();
	store.subscribe(topic, subscriber1);
	store.subscribe(topic, subscriber2);
	store.subscribe(topic, subscriber3);

	store.publish(topic, 1);
	Awaitility.await("Wait for delivery of message.") //
		.atMost(1, TimeUnit.SECONDS)//
		.pollInterval(10, TimeUnit.MILLISECONDS)//
		.until(() -> {
		    try {
			verify(subscriber1, times(1)).onNext(1);
			verify(subscriber2, times(1)).onNext(1);
			verify(subscriber3, times(1)).onNext(1);
			return true;
		    } catch (Throwable e) {
			return false;
		    }
		});
    }

    @Test
    public void testMultipleSubscribersManyMessages() throws InterruptedException {
	Subscriber<Integer> subscriber1 = createSubscriber();
	Subscriber<Integer> subscriber2 = createSubscriber();
	Subscriber<Integer> subscriber3 = createSubscriber();

	FluxStore store = ReactiveFX.getStore();
	store.subscribe(topic, subscriber1);
	store.subscribe(topic, subscriber2);
	store.subscribe(topic, subscriber3);

	for (int i = 0; i < 1000; i++) {
	    store.publish(topic, i);
	}

	Awaitility.await("Wait for delivery of message.") //
		.atMost(1, TimeUnit.SECONDS)//
		.pollInterval(10, TimeUnit.MILLISECONDS)//
		.until(() -> {
		    try {
			verify(subscriber1, times(1000)).onNext(any());
			verify(subscriber2, times(1000)).onNext(any());
			verify(subscriber3, times(1000)).onNext(any());
			return true;
		    } catch (Throwable e) {
			return false;
		    }
		});

	for (int i = 0; i < 1000; i++) {
	    verify(subscriber1, times(1)).onNext(i);
	    verify(subscriber2, times(1)).onNext(i);
	    verify(subscriber3, times(1)).onNext(i);
	}
    }
}
