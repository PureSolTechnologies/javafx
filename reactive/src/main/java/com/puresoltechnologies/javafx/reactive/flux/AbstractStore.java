package com.puresoltechnologies.javafx.reactive.flux;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public abstract class AbstractStore<A extends Enum<A>, D>
	implements Store<A, D>, Subscriber<Payload<?, ?>>, AutoCloseable {

    private Subscription subscription = null;

    abstract void handle(Payload<?, ?> payload);

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
    public void onNext(Payload<?, ?> item) {
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
