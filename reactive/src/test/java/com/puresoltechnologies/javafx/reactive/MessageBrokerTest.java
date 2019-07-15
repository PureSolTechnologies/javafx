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

public class MessageBrokerTest {

    private final Topic<Integer> topic = new Topic<>("test.counter", Integer.class);
    private final Topic<Integer> topicWithBuffer = new Topic<>("test.counter", Integer.class, 3);

    private Subscriber<Integer> subscriber;

    @BeforeEach
    public void initialize() {
	MessageBroker.initialize();
    }

    @AfterEach
    public void shutdown() {
	MessageBroker.shutdown();
    }

    private Subscriber<Integer> createSubscriber() {
	@SuppressWarnings("unchecked")
	Subscriber<Integer> subscriber = mock(Subscriber.class);
	Mockito.doAnswer(invocation -> {
	    Subscription subscription = invocation.getArgument(0);
	    subscription.request(Long.MAX_VALUE);
	    System.out.println("subscribe");
	    return null;
	}).when(subscriber).onSubscribe(any());
	Mockito.doAnswer(invocation -> {
	    Integer message = invocation.getArgument(0);
	    System.out.println(message);
	    return null;
	}).when(subscriber).onNext(any());
	return subscriber;
    }

    @Test
    public void testSingleSubscriber() throws InterruptedException {
	Subscriber<Integer> subscriber = createSubscriber();

	MessageBroker store = MessageBroker.getStore();
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

	MessageBroker store = MessageBroker.getStore();
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

	MessageBroker store = MessageBroker.getStore();
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

    @Test
    public void testGetLastMessage() throws InterruptedException {
	Subscriber<Integer> subscriber1 = createSubscriber();
	Subscriber<Integer> subscriber2 = createSubscriber();

	MessageBroker store = MessageBroker.getStore();
	store.subscribe(topic, subscriber1);

	for (int i = 0; i < 10; i++) {
	    store.publish(topic, i);
	}

	Awaitility.await("Wait for delivery of messages.") //
		.atMost(1, TimeUnit.SECONDS)//
		.pollInterval(10, TimeUnit.MILLISECONDS)//
		.until(() -> {
		    try {
			verify(subscriber1, times(10)).onNext(any());
			return true;
		    } catch (Throwable e) {
			return false;
		    }
		});

	for (int i = 0; i < 10; i++) {
	    verify(subscriber1, times(1)).onNext(i);
	}

	store.subscribe(topic, subscriber2);
	Awaitility.await("Wait for delivery of buffered message.") //
		.atMost(1, TimeUnit.SECONDS)//
		.pollInterval(10, TimeUnit.MILLISECONDS)//
		.until(() -> {
		    try {
			// verify(subscriber2, times(1)).onNext(9);
			verify(subscriber2, times(1)).onNext(any());
			return true;
		    } catch (Throwable e) {
			return false;
		    }
		});
    }

    @Test
    public void testGetLastMessagesWithBufferSize() throws InterruptedException {
	Subscriber<Integer> subscriber1 = createSubscriber();
	Subscriber<Integer> subscriber2 = createSubscriber();

	MessageBroker store = MessageBroker.getStore();
	store.subscribe(topicWithBuffer, subscriber1);

	for (int i = 0; i < 10; i++) {
	    store.publish(topicWithBuffer, i);
	}

	Awaitility.await("Wait for delivery of messages.") //
		.atMost(1, TimeUnit.SECONDS)//
		.pollInterval(10, TimeUnit.MILLISECONDS)//
		.until(() -> {
		    try {
			verify(subscriber1, times(10)).onNext(any());
			return true;
		    } catch (Throwable e) {
			return false;
		    }
		});

	for (int i = 0; i < 10; i++) {
	    verify(subscriber1, times(1)).onNext(i);
	}

	store.subscribe(topicWithBuffer, subscriber2);
	Awaitility.await("Wait for delivery of buffered message.") //
		.atMost(1, TimeUnit.SECONDS)//
		.pollInterval(10, TimeUnit.MILLISECONDS)//
		.until(() -> {
		    try {
			verify(subscriber2, times(1)).onNext(7);
			verify(subscriber2, times(1)).onNext(8);
			verify(subscriber2, times(1)).onNext(9);
			verify(subscriber2, times(3)).onNext(any());
			return true;
		    } catch (Throwable e) {
			return false;
		    }
		});
    }

}