package com.puresoltechnologies.javafx.perspectives;

import com.puresoltechnologies.javafx.perspectives.parts.Part;

import javafx.scene.image.Image;

/**
 * This is the interface for a Perspective.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public interface Perspective extends PerspectiveElement {

    public String getName();

    public Image getImage();

    public void reset();

    public PerspectiveElement getRootElement();

    public void openPart(Part part);

}
