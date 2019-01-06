package com.puresoltechnologies.javafx.perspectives;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import com.puresoltechnologies.javafx.perspectives.parts.Part;

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
    UUID getId();

    /**
     * This method returns the elements parent.
     *
     * @return An {@link PerspectiveElement} object is returned representing the
     *         parent of this element.
     */
    PerspectiveElement getParent();

    /**
     * This method returns the content node of the element.
     *
     * @return A {@link Node} object is returned for the content.
     */
    Node getContent();

    /**
     * This method returns all child elements.
     *
     * @return A {@link List} of {@link PerspectiveElement} is returned.
     */
    List<PerspectiveElement> getElements();

    /**
     * This method adds a new {@link PerspectiveElement} element to this element.
     * What happens with the new child is up to the implementation. After the
     * element was instantiated with the default constructor
     * {@link Part#initialize()} is called.
     *
     * @param clazz is the new element.
     */

    default void addElement(Class<? extends PerspectiveElement> clazz) {
	try {
	    PerspectiveElement element = clazz.getDeclaredConstructor().newInstance();
	    if (Part.class.isAssignableFrom(clazz)) {
		((Part) element).initialize();
	    }
	    addElement(element);
	} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
		| NoSuchMethodException | SecurityException e) {
	    throw new RuntimeException("Cannot instantiate class '" + clazz.getName() + "'.", e);
	}
    }

    /**
     * This method adds a new {@link PerspectiveElement} element to this element.
     * What happens with the new child is up to the implementation. It is expected
     * that the this element was initialized already.
     *
     * @param element is the new element.
     */
    void addElement(PerspectiveElement element);

    /**
     * This method adds a new {@link PerspectiveElement} element to this element. It
     * is expected that the this element was initialized already.
     *
     * @param element is the new element.
     * @param index   is the index of the position where to add the element.
     */
    void addElement(int index, PerspectiveElement element);

    /**
     * This method removes the child with the corresponding id.
     *
     * @param id is the id of the child to be removed.
     */
    void removeElement(UUID id);

    /**
     * This method removes the element given as parameter.
     *
     * @param element is the child to be removed.
     */
    void removeElement(PerspectiveElement element);

}
