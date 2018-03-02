package com.puresoltechnologies.javafx.perspectives;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;

public class PartSplit extends AbstractPerspectiveElement {

    private static final long serialVersionUID = -699720713406202843L;

    private List<PerspectiveElement> elements = new ArrayList<>();
    private final SplitPane splitPane;

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
    public String getId() {
	return splitPane.getId();
    }

    @Override
    public List<PerspectiveElement> getElements() {
	return elements;
    }

    private void rearrangeItems() {
	ObservableList<Node> items = splitPane.getItems();
	items.clear();
	elements.forEach(e -> items.add(e.getContent()));
    }

    @Override
    public void addElement(PerspectiveElement element) {
	if (elements.size() >= 2) {
	    throw new IllegalStateException("More than two elements are not allowed in a split.");
	}
	setContext(element);
	elements.add(element);
	rearrangeItems();
    }

    @Override
    public void removeElement(PerspectiveElement element) {
	removeElement(element.getId());
    }

    @Override
    public void removeElement(String id) {
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
    protected void setPerspectiveHandler(PerspectiveHandler perspectiveHandler) {
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
    public SplitPane getContent() {
	return splitPane;
    }

    public double getDividerPosition() {
	return splitPane.getDividerPositions()[0];
    }

    public void setDividerPosition(double positions) {
	splitPane.setDividerPosition(0, positions);
    }

    @Override
    public boolean isSplit() {
	return true;
    }

}
