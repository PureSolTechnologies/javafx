package com.puresoltechnologies.javafx.perspectives;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;

public class PartSplit extends AbstractPerspectiveElement {

    private final List<PerspectiveElement> elements = new ArrayList<>();
    private final SplitPane splitPane;

    /**
     * This is the default constructor. The default orientation is
     * {@link Orientation#HORIZONTAL}
     */
    public PartSplit() {
	this(Orientation.HORIZONTAL);
    }

    public PartSplit(Orientation orientation) {
	super();
	splitPane = new SplitPane();
	splitPane.setId(UUID.randomUUID().toString());
	splitPane.setOrientation(orientation);
    }

    @Override
    public final List<PerspectiveElement> getElements() {
	return elements;
    }

    private void rearrangeItems() {
	ObservableList<Node> items = splitPane.getItems();
	items.clear();
	elements.forEach(e -> items.add(e.getContent()));
    }

    @Override
    public final void addElement(PerspectiveElement element) {
	if (elements.size() >= 2) {
	    throw new IllegalStateException("More than two elements are not allowed in a split.");
	}
	setContext(element);
	elements.add(element);
	rearrangeItems();
    }

    @Override
    public void addElement(int index, PerspectiveElement element) {
	if (elements.size() >= 2) {
	    throw new IllegalStateException("More than two elements are not allowed in a split.");
	}
	if ((index < 0) || (index > 1)) {
	    throw new IllegalArgumentException("Index '" + index
		    + "' is invalid. Splits only contain two elements max and index can only be 0 or 1.");
	}
	setContext(element);
	elements.add(index, element);
	rearrangeItems();
    }

    @Override
    public final void removeElement(PerspectiveElement element) {
	removeElement(element.getId());
    }

    @Override
    public final void removeElement(UUID id) {
	Iterator<PerspectiveElement> items = elements.iterator();
	while (items.hasNext()) {
	    PerspectiveElement item = items.next();
	    if (item.getId() == id) {
		items.remove();
	    }
	}
	rearrangeItems();
    }

    @Override
    protected final void setPerspectiveHandler(PerspectiveHandler perspectiveHandler) {
	super.setPerspectiveHandler(perspectiveHandler);
	for (PerspectiveElement element : elements) {
	    if (element instanceof PartSplit) {
		((PartSplit) element).setPerspectiveHandler(perspectiveHandler);
	    } else if (element instanceof PartStack) {
		((PartStack) element).setPerspectiveHandler(perspectiveHandler);
	    }
	}
    }

    @Override
    public final SplitPane getContent() {
	return splitPane;
    }

    public int getIndex(UUID id) {
	for (PerspectiveElement element : elements) {
	    if (element.getId().equals(id)) {
		return elements.indexOf(element);
	    }
	}
	throw new NoSuchElementException("No element with id '" + id.toString() + "' was found.");
    }

    public final double getDividerPosition() {
	return splitPane.getDividerPositions()[0];
    }

    public final void setDividerPosition(double positions) {
	splitPane.setDividerPosition(0, positions);
    }

}
