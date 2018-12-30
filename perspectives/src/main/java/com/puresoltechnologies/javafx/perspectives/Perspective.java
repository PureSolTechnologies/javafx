package com.puresoltechnologies.javafx.perspectives;

import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.parts.Part;

import javafx.scene.image.Image;

/**
 * This is the interface for a Perspective.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public interface Perspective extends PerspectiveElement {

    /**
     * This method returns the name of the perspective as it is to be presented
     * inside the UI for the user.
     * 
     * @return
     */
    public String getName();

    /**
     * This is a mandatory icon image to be assigned to this perpective.
     * 
     * @return
     */
    public Optional<Image> getImage();

    public void reset();

    public PerspectiveElement getRootElement();

    public void openPart(Part part);

}
