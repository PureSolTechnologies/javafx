package com.puresoltechnologies.javafx.perspectives;

import java.util.List;
import java.util.UUID;

import javafx.scene.Node;

/**
 * This is the central interface for all nodes, which make up the perspective.
 * This can be a perspective, a general part, a viewer, an editor or a stack
 * element.
 * 
 * @author Rick-Rainer Ludwig
 */
public interface PerspectiveElement {

    /**
     * This method returns a unique id.
     * 
     * @return A {@link String} containing a unique id is returned.
     */
    public UUID getId();

    /**
     * This method returns the elements parent.
     * 
     * @return An {@link PerspectiveElement} object is returned representing the
     *         parent of this element.
     */
    public PerspectiveElement getParent();

    /**
     * This method returns the content node of the element.
     * 
     * @return A {@link Node} object is returned for the content.
     */
    public Node getContent();

    /**
     * This method returns all child elements.
     * 
     * @return A {@link List} of {@link PerspectiveElement} is returned.
     */
    public List<PerspectiveElement> getElements();

    /**
     * This method adds a new {@link PerspectiveElement} element to this element.
     * What happens with the new child is up to the implementation.
     * 
     * @param element
     *            is the new element.
     */
    public void addElement(PerspectiveElement element);

    /**
     * This method removes the child with the corresponding id.
     * 
     * @param id
     *            is the id of the child to be removed.
     */
    public void removeElement(UUID id);

    /**
     * This method removes the element given as parameter.
     * 
     * @param element
     *            is the child to be removed.
     */
    public void removeElement(PerspectiveElement element);

}
