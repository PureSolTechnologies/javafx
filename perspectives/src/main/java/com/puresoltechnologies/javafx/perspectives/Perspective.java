package com.puresoltechnologies.javafx.perspectives;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

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
     * @return The name is returned as string.
     */
    String getName();

    /**
     * This is a mandatory icon image to be assigned to this perpective.
     *
     * @return An {@link Optional} {@link Image} is returned to represent this
     *         perspective graphically.
     */
    Optional<Image> getImage();

    void reset();

    PerspectiveElement getRootElement();

    void openPart(Part part);

    Set<Part> findPart(Predicate<Part> filter);

    default Part findPartById(UUID id) {
	Set<Part> parts = findPart(part -> part.getId().equals(id));
	Iterator<Part> partsIterator = parts.iterator();
	if (!partsIterator.hasNext()) {
	    return null;
	}
	Part part = partsIterator.next();
	if (!partsIterator.hasNext()) {
	    throw new IllegalStateException("Multiple parts with id '" + id + " were found.");
	}
	return part;
    }

}
