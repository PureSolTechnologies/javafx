package com.puresoltechnologies.javafx.perspectives;

public abstract class AbstractPart implements Part {

    private static final long serialVersionUID = 1609387557187949696L;

    private final String name;
    private final boolean singleton;

    public AbstractPart(String name, boolean singleton) {
	super();
	this.name = name;
	this.singleton = singleton;
    }

    @Override
    public final String getName() {
	return name;
    }

    @Override
    public final boolean isSingleton() {
	return singleton;
    }
}
