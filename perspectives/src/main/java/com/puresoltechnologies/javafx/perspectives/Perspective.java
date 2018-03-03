package com.puresoltechnologies.javafx.perspectives;

import com.puresoltechnologies.javafx.perspectives.parts.Part;

/**
 * This is the interface for a Perspective.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public interface Perspective extends PerspectiveElement {

    public String getName();

    public void reset();

    public PerspectiveElement getRootElement();

    public void showPart(Part part);

}
