package com.puresoltechnologies.javafx.perspectives;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.puresoltechnologies.javafx.perspectives.parts.Part;

import javafx.application.Platform;
import javafx.geometry.Orientation;

class PerspectiveHandler {

    private final AbstractPerspective perspective;

    public PerspectiveHandler(AbstractPerspective perspective) {
	super();
	this.perspective = perspective;
    }

    private Part findPart(String partId) {
	return findPart(perspective, partId);
    }

    private Part findPart(PerspectiveElement parent, String partId) {
	for (PerspectiveElement element : parent.getElements()) {
	    Part part = null;
	    if (element instanceof PartSplit) {
		part = findPart(element, partId);
	    } else if (element instanceof PartStack) {
		part = findPart((PartStack) element, partId);
	    }
	    if (part != null) {
		return part;
	    }
	}
	return null;
    }

    private Part findPart(PartStack partStack, String partId) {
	for (Part part : partStack.getParts()) {
	    if (partId.equals(part.getId())) {
		return part;
	    }
	}
	return null;
    }

    private PerspectiveElement findElement(String stackId) {
	return findPartStack(perspective, stackId);
    }

    private PerspectiveElement findPartStack(PerspectiveElement parent, String stackId) {
	for (PerspectiveElement element : parent.getElements()) {
	    if (element instanceof PartSplit) {
		PerspectiveElement partStack = findPartStack(element, stackId);
		if (partStack != null) {
		    return partStack;
		}
	    } else if (element instanceof PartStack) {
		PartStack partStack = (PartStack) element;
		if (stackId.equals(partStack.getId())) {
		    return partStack;
		}
	    }
	}
	return null;
    }

    public void movePartToStack(String oldStackId, String partId, String newStackId) {
	PerspectiveElement oldPartStack = findElement(oldStackId);
	Part part = findPart(partId);
	PerspectiveElement newPartStack = findElement(newStackId);
	Platform.runLater(() -> {
	    oldPartStack.removeElement(part);
	    newPartStack.addElement(part);
	    removeEmptyElements();
	    printElements();
	});
    }

    private void removeEmptyElements() {
	removeEmptyElements(perspective);
    }

    private void removeEmptyElements(PerspectiveElement perspectiveElement) {
	List<Runnable> removeIds = new ArrayList<>();
	List<PerspectiveElement> addElements = new ArrayList<>();
	for (PerspectiveElement elements : perspectiveElement.getElements()) {
	    if (elements instanceof PartSplit) {
		PartSplit partSplit = (PartSplit) elements;
		removeEmptyElements(partSplit);
		List<PerspectiveElement> items = partSplit.getElements();
		if (items.isEmpty()) {
		    removeIds.add(() -> partSplit.getParent().removeElement(partSplit.getId()));
		} else if (items.size() == 1) {
		    removeIds.add(() -> {
			partSplit.getParent().removeElement(partSplit.getId());
		    });
		    PerspectiveElement child = items.get(0);
		    addElements.add(child);
		}
	    } else if (elements instanceof PartStack) {
		PartStack partStack = (PartStack) elements;
		if (!partStack.hasParts()) {
		    removeIds.add(() -> partStack.getParent().removeElement(partStack));
		}
	    }
	}
	removeIds.forEach(r -> r.run());
	addElements.forEach(e -> {
	    if (e instanceof PartSplit) {
		perspectiveElement.addElement(e);
	    } else if (e instanceof PartStack) {
		perspectiveElement.addElement(e);
	    }
	});
    }

    private void printElements() {
	System.out.println("=== " + LocalDateTime.now() + " ===");
	printElements(perspective.getRootElement(), 0);
    }

    private void printElements(PerspectiveElement parent, int depth) {
	List<String> ids = new ArrayList<>();
	for (PerspectiveElement element : parent.getElements()) {
	    if (element instanceof PartSplit) {
		PartSplit partSplit = (PartSplit) element;
		printElements(partSplit, depth + 1);
		System.out.println(depth + ": " + partSplit);
	    } else if (element instanceof PartStack) {
		PartStack partStack = (PartStack) element;
		System.out.println(depth + ": " + partStack);
	    }
	}
	ids.forEach(id -> parent.removeElement(id));
    }

    public void movePartToNewTop(String partStackId, String partId, String id) {
	movePartToNew(partStackId, partId, id, Orientation.VERTICAL, true);
    }

    public void movePartToNewRight(String partStackId, String partId, String id) {
	movePartToNew(partStackId, partId, id, Orientation.HORIZONTAL, false);
    }

    public void movePartToNewLower(String partStackId, String partId, String id) {
	movePartToNew(partStackId, partId, id, Orientation.VERTICAL, false);
    }

    public void movePartToNewLeft(String partStackId, String partId, String id) {
	movePartToNew(partStackId, partId, id, Orientation.HORIZONTAL, true);
    }

    private void movePartToNew(String oldStackId, String partId, String newStackId, Orientation orientation,
	    boolean first) {
	PerspectiveElement oldPartStack = findElement(oldStackId);
	Part part = findPart(partId);
	PerspectiveElement selectedPartStack = findElement(newStackId);
	PerspectiveElement selectedPartSplit = selectedPartStack.getParent();

	Platform.runLater(() -> {
	    PartSplit newPartSplit = new PartSplit(orientation);
	    PartStack newPartStack = new PartStack();
	    selectedPartSplit.removeElement(selectedPartStack);
	    if (first) {
		newPartSplit.addElement(newPartStack);
		newPartSplit.addElement(selectedPartStack);
	    } else {
		newPartSplit.addElement(selectedPartStack);
		newPartSplit.addElement(newPartStack);

	    }
	    selectedPartSplit.addElement(newPartSplit);
	    oldPartStack.removeElement(part);
	    newPartStack.addPart(part);

	    removeEmptyElements();
	    printElements();
	});
    }

}
