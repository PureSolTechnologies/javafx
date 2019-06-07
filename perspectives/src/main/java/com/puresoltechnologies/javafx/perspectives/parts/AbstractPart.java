package com.puresoltechnologies.javafx.perspectives.parts;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.puresoltechnologies.javafx.perspectives.PartStack;
import com.puresoltechnologies.javafx.perspectives.PerspectiveElement;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public abstract class AbstractPart implements Part {

    private PartStack parent = null;
    private final UUID id = UUID.randomUUID();
    private final StringProperty title = new SimpleStringProperty();
    private final ObjectProperty<Image> image = new SimpleObjectProperty<>();
    private final PartOpenMode openMode;

    public AbstractPart(String title, PartOpenMode openMode) {
	super();
	this.title.set(title);
	this.openMode = openMode;
    }

    @Override
    public final UUID getId() {
	return id;
    }

    @Override
    public final String getTitle() {
	return title.get();
    }

    @Override
    public final void setTitle(String title) {
	this.title.set(title);
    }

    @Override
    public final StringProperty titleProperty() {
	return title;
    }

    @Override
    public final Optional<Image> getImage() {
	return Optional.ofNullable(image.get());
    }

    @Override
    public final void setImage(Image image) {
	this.image.set(image);
    }

    @Override
    public final ObjectProperty<Image> imageProperty() {
	return image;
    }

    @Override
    public final PartOpenMode getOpenMode() {
	return openMode;
    }

    @Override
    public final PartStack getParent() {
	return parent;
    }

    public final void setParent(PartStack parent) {
	this.parent = parent;
    }

    @Override
    public final List<PerspectiveElement> getElements() {
	return Collections.emptyList();
    }

    @Override
    public final void addElement(PerspectiveElement e) {
	throw new IllegalArgumentException("Parts cannot contain perspective elements.");
    }

    @Override
    public void addElement(int index, PerspectiveElement element) {
	throw new IllegalArgumentException("Parts cannot contain perspective elements.");
    }

    @Override
    public final void removeElement(UUID id) {
	throw new IllegalArgumentException("Parts cannot contain perspective elements.");
    }

    @Override
    public final void removeElement(PerspectiveElement element) {
	throw new IllegalArgumentException("Parts cannot contain perspective elements.");
    }

    @Override
    public boolean manualInitialization() {
	// Intentionally left empty, as this is an implementation which should be
	// provided only in cases where it is needed.
	return true;
    }

}
