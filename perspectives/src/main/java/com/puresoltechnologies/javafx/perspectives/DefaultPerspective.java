package com.puresoltechnologies.javafx.perspectives;

import java.util.function.Supplier;

import javafx.scene.image.Image;

public class DefaultPerspective extends AbstractPerspective {

    private static final long serialVersionUID = -3297051920552496687L;

    private final String id;
    private final Supplier<PerspectiveElement> rootElementSupplier;

    public DefaultPerspective(String id, String name, Supplier<PerspectiveElement> rootElementSupplier) {
	super(name);
	this.id = id;
	this.rootElementSupplier = rootElementSupplier;
    }

    @Override
    public final String getId() {
	return id;
    }

    @Override
    public Image getImage() {
	return null;
    }

    @Override
    protected PerspectiveElement createContent() {
	return rootElementSupplier.get();
    }

}
