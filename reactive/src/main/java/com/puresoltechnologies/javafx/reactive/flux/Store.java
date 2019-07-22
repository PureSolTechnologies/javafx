package com.puresoltechnologies.javafx.reactive.flux;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import com.puresoltechnologies.javafx.reactive.MessageBroker;

public abstract class Store<A extends Enum<A>, D> implements Subscriber<Payload>, AutoCloseable {

    private Subscription subscription = null;

    public Store() {
	super();
	MessageBroker.getBroker().subscribe(Flux.FLUX_TOPIC, this);
    }

    abstract protected void handle(Payload payload);

    @Override
    public void close() {
	if (subscription == null) {
	    throw new IllegalStateException("This store was already closed or not subscribed, yet.");
	}
	subscription.cancel();
	subscription = null;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
	this.subscription = subscription;
	subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Payload item) {
	handle(item);
    }

    @Override
    public void onError(Throwable throwable) {
	// TODO Auto-generated method stub
    }

    @Override
    public void onComplete() {
	// TODO Auto-generated method stub
    }

}
