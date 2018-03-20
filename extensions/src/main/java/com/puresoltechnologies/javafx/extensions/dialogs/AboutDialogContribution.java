package com.puresoltechnologies.javafx.extensions.dialogs;

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

    public String getName();

    public Optional<Image> getImage();

    public Node getContent();

}
