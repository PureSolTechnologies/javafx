package com.puresoltechnologies.javafx.extensions.dialogs.wizard;

import java.util.Optional;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;

/**
 * This interface is used to define a single wizard page for
 * {@link WizardDialog}.
 *
 * @author Rick-Rainer Ludwig
 *
 * @param <T> is the return type for the {@link WizardDialog}.
 */
public interface WizardPage<T> extends AutoCloseable {

    /**
     * This method is used to set the reference to the result object for
     * {@link WizardDialog}.
     *
     * @param data is the reference to the data set.
     */
    void setData(T data);

    /**
     * This method returns the title for this wizard page.
     *
     * @return A {@link String} is returned containing the title.
     */
    String getTitle();

    /**
     * This method returns an {@link Optional} description.
     *
     * @return A an {@link Optional} {@link String} is returned.
     */
    Optional<String> getDescription();

    /**
     * An optional image is returned for this wizard page.
     *
     * @return An {@link Optional} {@link Image} is returned.
     */
    Optional<Image> getImage();

    /**
     * This method returns the node which provides the actual wizard page.
     *
     * @return A {@link Node} is returned containing the content of the page.
     */
    Node getNode();

    boolean canProceed();

    /**
     * Returns a property which signal whether the {@link WizardDialog} can proceed
     * to next step or not.
     *
     * @return A {@link BooleanProperty} is returned.
     */
    BooleanProperty canProceedProperty();

    boolean canFinish();

    /**
     * Returns a property which signal whether the {@link WizardDialog} can finish
     * or not.
     *
     * @return A {@link BooleanProperty} is returned.
     */
    BooleanProperty canFinishProperty();

    /**
     * This method is called after this page was added to {@link WizardDialog}. The
     * reference to the data was provided before.
     */
    void initialize();

    /**
     * This method is called just before the {@link WizardDialog} is closed to free
     * resources.
     */
    @Override
    void close();

    /**
     * This method is called by {@link WizardDialog} every time the page is shown.
     */
    void onArrival();

    /**
     * This method is called by {@link WizardDialog} every time the page is left.
     */
    void onLeave();
}
