package com.puresoltechnologies.javafx.perspectives.parts;

import java.util.Collections;
import java.util.List;

import com.puresoltechnologies.javafx.perspectives.PartStack;
import com.puresoltechnologies.javafx.perspectives.PerspectiveElement;

public abstract class AbstractPart implements Part {

    private static final long serialVersionUID = 1609387557187949696L;

    private PartStack parent = null;
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

    @Override
    public PartStack getParent() {
	return parent;
    }

    public void setParent(PartStack parent) {
	this.parent = parent;
    }

    @Override
    public List<PerspectiveElement> getElements() {
	return Collections.emptyList();
    }

    @Override
    public void addElement(PerspectiveElement e) {
	throw new IllegalArgumentException("Parts cannot contain perspective elements.");
    }

    @Override
    public void removeElement(String id) {
	throw new IllegalArgumentException("Parts cannot contain perspective elements.");
    }

    @Override
    public void removeElement(PerspectiveElement element) {
	throw new IllegalArgumentException("Parts cannot contain perspective elements.");
    }

    @Override
    public boolean isSplit() {
	return false;
    }
}
