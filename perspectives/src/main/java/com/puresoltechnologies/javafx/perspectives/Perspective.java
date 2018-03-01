package com.puresoltechnologies.javafx.perspectives;

import javafx.scene.Node;

/**
 * This is the interface for a Perspective.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public interface Perspective {

    public String getId();

    public String getName();

    public void reset();

    public PerspectiveElement getElement();

    public Node getContent();
}
