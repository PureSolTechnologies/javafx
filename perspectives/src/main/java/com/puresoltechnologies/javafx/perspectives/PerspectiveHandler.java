package com.puresoltechnologies.javafx.perspectives;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.puresoltechnologies.javafx.perspectives.parts.Part;
import com.puresoltechnologies.javafx.utils.FXThreads;

import javafx.geometry.Orientation;

class PerspectiveHandler {

    private static class AddElement {
	private final int index;
	private final PerspectiveElement element;

	public AddElement(int index, PerspectiveElement element) {
	    super();
	    this.index = index;
	    this.element = element;
	}

	public int getIndex() {
	    return index;
	}

	public PerspectiveElement getElement() {
	    return element;
	}

    }

    private final AbstractPerspective perspective;

    public PerspectiveHandler(AbstractPerspective perspective) {
	super();
	this.perspective = perspective;
    }

    private Part findPart(UUID partId) {
	return findPart(perspective, partId);
    }

    private Part findPart(PerspectiveElement parent, UUID partId) {
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

    private Part findPart(PartStack partStack, UUID partId) {
	for (Part part : partStack.getParts()) {
	    if (partId.equals(part.getId())) {
		return part;
	    }
	}
	return null;
    }

    private PerspectiveElement findElement(UUID stackId) {
	return findPartStack(perspective, stackId);
    }

    private PerspectiveElement findPartStack(PerspectiveElement parent, UUID stackId) {
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

    public void movePartToStack(UUID oldStackId, UUID partId, UUID newStackId) {
	PerspectiveElement oldPartStack = findElement(oldStackId);
	Part part = findPart(partId);
	PerspectiveElement newPartStack = findElement(newStackId);
	FXThreads.proceedOnFXThread(() -> {
	    oldPartStack.removeElement(part);
	    newPartStack.addElement(part);
	    removeEmptyElements();
	    printElements();
	});
    }

    void removeEmptyElements() {
	removeEmptyElements(perspective);
    }

    private void removeEmptyElements(PerspectiveElement perspectiveElement) {
	List<Runnable> removeIds = new ArrayList<>();
	List<AddElement> addElements = new ArrayList<>();
	for (PerspectiveElement elements : perspectiveElement.getElements()) {
	    if (elements instanceof PartSplit) {
		PartSplit partSplit = (PartSplit) elements;
		removeEmptyElements(partSplit);
		List<PerspectiveElement> items = partSplit.getElements();
		if (items.isEmpty()) {
		    removeIds.add(() -> partSplit.getParent().removeElement(partSplit.getId()));
		} else if (items.size() == 1) {
		    int index = ((PartSplit) partSplit.getParent()).getIndex(partSplit.getId());
		    removeIds.add(() -> {
			partSplit.getParent().removeElement(partSplit.getId());
		    });
		    PerspectiveElement child = items.get(0);
		    addElements.add(new AddElement(index, child));
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
	    if (e.getElement() instanceof PartSplit) {
		perspectiveElement.addElement(e.getIndex(), e.getElement());
	    } else if (e.getElement() instanceof PartStack) {
		perspectiveElement.addElement(e.getIndex(), e.getElement());
	    }
	});
    }

    private void printElements() {
	System.out.println("=== " + LocalDateTime.now() + " ===");
	printElements(perspective.getRootElement(), 0);
    }

    private void printElements(PerspectiveElement parent, int depth) {
	for (PerspectiveElement element : parent.getElements()) {
	    if (element instanceof PartSplit) {
		PartSplit partSplit = (PartSplit) element;
		printElements(partSplit, depth + 1);
		System.out.println(depth + ": " + partSplit);
	    } else if (element instanceof PartStack) {
		PartStack partStack = (PartStack) element;
		System.out.println(depth + ": " + partStack);
		for (Part part : partStack.getParts()) {
		    System.out.println("   - " + part.getTitle());
		}
	    }
	}
    }

    public void movePartToNewTop(UUID partStackId, UUID partId, UUID id) {
	movePartToNew(partStackId, partId, id, Orientation.VERTICAL, true);
    }

    public void movePartToNewRight(UUID partStackId, UUID partId, UUID id) {
	movePartToNew(partStackId, partId, id, Orientation.HORIZONTAL, false);
    }

    public void movePartToNewLower(UUID partStackId, UUID partId, UUID id) {
	movePartToNew(partStackId, partId, id, Orientation.VERTICAL, false);
    }

    public void movePartToNewLeft(UUID partStackId, UUID partId, UUID id) {
	movePartToNew(partStackId, partId, id, Orientation.HORIZONTAL, true);
    }

    private void movePartToNew(UUID oldStackId, UUID partId, UUID newStackId, Orientation orientation, boolean first) {
	printElements();
	PerspectiveElement oldPartStack = findElement(oldStackId);
	Part part = findPart(partId);
	if (part == null) {
	    throw new IllegalStateException("Part with id '" + partId.toString() + "' to be moved could not be found.");
	}
	PerspectiveElement selectedPartStack = findElement(newStackId);
	PerspectiveElement selectedPartSplit = selectedPartStack.getParent();

	FXThreads.proceedOnFXThread(() -> {
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
	    newPartStack.addElement(part);

	    removeEmptyElements();
	    printElements();
	});
    }

}
