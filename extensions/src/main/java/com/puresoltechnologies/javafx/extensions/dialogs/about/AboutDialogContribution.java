package com.puresoltechnologies.javafx.extensions.dialogs.about;

import java.util.Optional;

import javafx.scene.Node;
import javafx.scene.image.Image;

/**
 * This interface is used together with SPI to provide additional tabs to the
 * {@link AboutDialog}.
 *
 * @author Rick-Rainer Ludwig
 */
public interface AboutDialogContribution {

    /**
     * This method returns the name to be presented in the tab header.
     *
     * @return A simple {@link String} is returned.
     */
    String getName();

    /**
     * This method is returning an {@link Optional} {@link Image} to be presented
     * inside the {@link AboutDialog}.
     *
     * @return An {@link Optional} of {@link Image} is returned.
     */
    Optional<Image> getImage();

    /**
     * This method returns a {@link Node} which is presented inside a tab of the
     * about dialog.
     *
     * @return A {@link Node} object is returned to be direclty put into the tab of
     *         the about dialog.
     */
    Node getContent();

}
