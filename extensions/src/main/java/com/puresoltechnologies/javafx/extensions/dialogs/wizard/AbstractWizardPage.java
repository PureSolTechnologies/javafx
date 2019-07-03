package com.puresoltechnologies.javafx.extensions.dialogs.wizard;

import java.util.Optional;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;

/**
 * This is a convenience class to help developers develop wizard pages.
 *
 * @author Rick-Rainer Ludwig
 */
public abstract class AbstractWizardPage<T> implements WizardPage<T> {

    private final String title;
    private final Optional<String> description;
    private final Optional<Image> image;
    private final SimpleBooleanProperty canProceedProperty = new SimpleBooleanProperty();
    private final SimpleBooleanProperty canFinishProperty = new SimpleBooleanProperty();

    private T data;

    public AbstractWizardPage(String title) {
	this(title, Optional.empty(), Optional.empty());
    }

    public AbstractWizardPage(String title, String description) {
	this(title, Optional.of(description), Optional.empty());
    }

    public AbstractWizardPage(String title, Image image) {
	this(title, Optional.empty(), Optional.of(image));
    }

    public AbstractWizardPage(String title, String description, Image image) {
	this(title, Optional.of(description), Optional.of(image));
    }

    private AbstractWizardPage(String title, Optional<String> description, Optional<Image> image) {
	super();
	this.title = title;
	this.description = description;
	this.image = image;
    }

    @Override
    public final void setData(T data) {
	this.data = data;
    }

    protected T getData() {
	return data;
    }

    @Override
    public final String getTitle() {
	return title;
    }

    @Override
    public final Optional<String> getDescription() {
	return description;
    }

    @Override
    public final Optional<Image> getImage() {
	return image;
    }

    @Override
    public final boolean canProceed() {
	return canProceedProperty.get();
    }

    protected final void setCanProceed(boolean canProcees) {
	canProceedProperty.set(canProcees);
    }

    @Override
    public final BooleanProperty canProceedProperty() {
	return canProceedProperty;
    }

    @Override
    public final boolean canFinish() {
	return canFinishProperty.get();
    }

    protected final void setCanFinish(boolean canFinish) {
	canFinishProperty.set(canFinish);
    }

    @Override
    public final BooleanProperty canFinishProperty() {
	return canFinishProperty;
    }

}
