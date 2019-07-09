package com.puresoltechnologies.javafx.reactive.flux;

import java.util.function.Consumer;

public class Dispatcher {

    public <A, D> Token register(Consumer<Payload<A, D>> callback) {
	return null;
    }

    public void unregister(Token token) {

    }

    public <A, D> void dispatch(Payload<A, D> payload) {

    }

    public boolean isDispatching() {
	return false;
    }
}
