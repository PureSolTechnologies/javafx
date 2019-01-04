package com.puresoltechnologies.javafx.perspectives;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.puresoltechnologies.javafx.perspectives.parts.Part;
import com.puresoltechnologies.javafx.perspectives.parts.PartDragData;
import com.puresoltechnologies.javafx.perspectives.parts.PartDragDataFormat;
import com.puresoltechnologies.javafx.perspectives.parts.PartHeader;
import com.puresoltechnologies.javafx.preferences.Preferences;
import com.puresoltechnologies.javafx.utils.FXThreads;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Labeled;
import javafx.scene.control.ToolBar;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class PartStack extends AbstractPerspectiveElement {

    private static final double DRAG_EDGE_FRACTION = 0.2;

    private static final ObjectProperty<ContentDisplay> toolBarContentDisplay = Preferences
	    .getProperty(PerspectiveProperties.partHeaderToolbarContentDisplay);

    class DropAreas {

	private final Polygon top;
	private final Polygon right;
	private final Polygon lower;
	private final Polygon left;
	private final Rectangle innerRectangle;

	DropAreas(PartStack partStack) {
	    double width = borderPane.getWidth();
	    double height = borderPane.getHeight();
	    double leftInnerPosition = width * DRAG_EDGE_FRACTION;
	    double rightInnerPosition = width * (1.0 - DRAG_EDGE_FRACTION);
	    double topInnerPosition = height * DRAG_EDGE_FRACTION;
	    double lowerInnerPosition = height * (1.0 - DRAG_EDGE_FRACTION);
	    // shapes
	    top = new Polygon(0.0, 0.0, width, 0.0, rightInnerPosition, topInnerPosition, leftInnerPosition,
		    topInnerPosition);
	    right = new Polygon(width, 0.0, width, height, rightInnerPosition, lowerInnerPosition, rightInnerPosition,
		    topInnerPosition);
	    lower = new Polygon(0.0, height, leftInnerPosition, lowerInnerPosition, rightInnerPosition,
		    lowerInnerPosition, width, height);
	    left = new Polygon(0.0, 0.0, leftInnerPosition, topInnerPosition, leftInnerPosition, lowerInnerPosition,
		    0.0, height);
	    innerRectangle = new Rectangle(leftInnerPosition, topInnerPosition,
		    (1.0 - (2.0 * DRAG_EDGE_FRACTION)) * width, (1.0 - (2.0 * DRAG_EDGE_FRACTION)) * height);
	}

	public Polygon getTop() {
	    return top;
	}

	public Polygon getRight() {
	    return right;
	}

	public Polygon getLower() {
	    return lower;
	}

	public Polygon getLeft() {
	    return left;
	}

	public Rectangle getInnerRectangle() {
	    return innerRectangle;
	}

	public boolean isLeft(DragEvent dragEvent) {
	    return left.contains(dragEvent.getX(), dragEvent.getY());
	}

	public boolean isRight(DragEvent dragEvent) {
	    return right.contains(dragEvent.getX(), dragEvent.getY());
	}

	public boolean isTop(DragEvent dragEvent) {
	    return top.contains(dragEvent.getX(), dragEvent.getY());
	}

	public boolean isLower(DragEvent dragEvent) {
	    return lower.contains(dragEvent.getX(), dragEvent.getY());
	}

	public boolean isInnerRectangle(DragEvent dragEvent) {
	    return innerRectangle.contains(dragEvent.getX(), dragEvent.getY());
	}
    }

    private final PartHeaderToolBar toolBar;
    private final HBox headerToolBar;
    private final List<Part> parts = new ArrayList<>();
    private final Map<UUID, PartHeader> headerButtons = new HashMap<>();
    private final BorderPane borderPane;

    public PartStack() {
	super();
	borderPane = new BorderPane();
	borderPane.setId(UUID.randomUUID().toString());
	headerToolBar = new HBox();
	headerToolBar.setAlignment(Pos.CENTER_RIGHT);
	toolBar = new PartHeaderToolBar(headerToolBar);
	borderPane.setTop(toolBar);
	borderPane.setOnDragEntered(event -> {
	    onDragEntered(event);
	});
	borderPane.setOnDragOver(event -> {
	    onDragOver(event);
	});
	borderPane.setOnDragExited(event -> {
	    onDragExisted(event);
	});
	borderPane.setOnDragDropped(event -> {
	    onDragDropped(event);
	});
    }

    private void onDragEntered(DragEvent event) {
	if (event.getGestureSource() == this) {
	    event.consume();
	    return;
	}
	if (event.getDragboard().hasContent(PartDragDataFormat.get())) {
	    event.acceptTransferModes(TransferMode.MOVE);
	    double width = borderPane.getWidth();
	    double height = borderPane.getHeight();
	    Canvas canvas = new Canvas(width, height);
	    borderPane.getChildren().add(canvas);
	    drawDragCanvas(canvas, event);
	}
	event.consume();
    }

    private void onDragOver(DragEvent event) {
	if (event.getGestureSource() == this) {
	    event.consume();
	    return;
	}
	if (event.getDragboard().hasContent(PartDragDataFormat.get())) {
	    event.acceptTransferModes(TransferMode.MOVE);
	    ObservableList<Node> children = borderPane.getChildren();
	    Node canvas = children.get(children.size() - 1);
	    if (canvas instanceof Canvas) {
		drawDragCanvas((Canvas) canvas, event);
	    }

	}
	event.consume();
    }

    private void onDragExisted(DragEvent event) {
	ObservableList<Node> children = borderPane.getChildren();
	children.remove(children.size() - 1);
	event.consume();
    }

    private void onDragDropped(DragEvent event) {
	if (event.getGestureSource() == this) {
	    event.consume();
	    return;
	}
	/* data dropped */
	ObservableList<Node> children = borderPane.getChildren();
	children.remove(children.size() - 1);
	boolean success = false;
	if (event.getDragboard().hasContent(PartDragDataFormat.get())) {
	    DropAreas dropAreas = new DropAreas(this);

	    PartDragData dragData = (PartDragData) event.getDragboard().getContent(PartDragDataFormat.get());
	    if (dropAreas.isTop(event)) {
		getPerspectiveHandler().movePartToNewTop(dragData.getPartStackId(), dragData.getPartId(), getId());
	    } else if (dropAreas.isRight(event)) {
		getPerspectiveHandler().movePartToNewRight(dragData.getPartStackId(), dragData.getPartId(), getId());
	    } else if (dropAreas.isLower(event)) {
		getPerspectiveHandler().movePartToNewLower(dragData.getPartStackId(), dragData.getPartId(), getId());
	    } else if (dropAreas.isLeft(event)) {
		getPerspectiveHandler().movePartToNewLeft(dragData.getPartStackId(), dragData.getPartId(), getId());
	    } else if (dropAreas.isInnerRectangle(event)) {
		getPerspectiveHandler().movePartToStack(dragData.getPartStackId(), dragData.getPartId(), getId());
	    }

	}
	event.setDropCompleted(success);
	event.consume();
    }

    private void drawDragCanvas(Canvas canvas, DragEvent event) {
	double width = canvas.getWidth();
	double height = canvas.getHeight();
	GraphicsContext gc = canvas.getGraphicsContext2D();
	gc.clearRect(0.0, 0.0, width, height);
	DropAreas dropAreas = new DropAreas(this);
	drawDragResults(gc, event, width, height, dropAreas);
    }

    private void drawDragResults(GraphicsContext gc, DragEvent event, double width, double height,
	    DropAreas dropAreas) {
	gc.setGlobalAlpha(0.5);
	gc.setStroke(Color.GRAY);
	gc.setLineWidth(2.0);
	gc.setLineDashes(4.0, 4.0);

	gc.setGlobalAlpha(0.5);
	gc.setStroke(Color.GRAY);
	if (dropAreas.isTop(event)) {
	    gc.strokeRect(0.0, 0.0, width, height / 2.0);
	} else if (dropAreas.isRight(event)) {
	    gc.strokeRect(width / 2.0, 0.0, width / 2.0, height);
	} else if (dropAreas.isLower(event)) {
	    gc.strokeRect(0.0, height / 2.0, width, height / 2.0);
	} else if (dropAreas.isLeft(event)) {
	    gc.strokeRect(0.0, 0.0, width / 2.0, height);
	} else if (dropAreas.isInnerRectangle(event)) {
	    gc.strokeRect(0.0, 0.0, width, height);
	}
    }

    @Override
    public List<PerspectiveElement> getElements() {
	return Collections.emptyList();
    }

    public final void openPart(Part part) {
	part.initialize();
	addElement(part);
    }

    public final void closePart(Part part) {
	removeElement(part);
	part.close();
    }

    final List<Part> getParts() {
	return parts;
    }

    @Override
    public final BorderPane getContent() {
	return borderPane;
    }

    @Override
    public final void addElement(PerspectiveElement element) {
	ObservableList<Node> items = toolBar.getItems();
	addElement(items.size() - 1, element);
    }

    @Override
    public void addElement(int index, PerspectiveElement element) {
	if (!Part.class.isAssignableFrom(element.getClass())) {
	    throw new IllegalArgumentException("Part stacks can only contain parts. Type '"
		    + element.getClass().getName() + "' is not supported.");
	}
	Part part = (Part) element;
	PartHeader button = new PartHeader(this, part);
	headerButtons.put(part.getId(), button);
	parts.add(part);
	FXThreads.proceedOnFXThread(() -> {
	    part.initialize();
	    ObservableList<Node> items = toolBar.getItems();
	    items.add(index, button);
	    setActive(part.getId());
	});
    }

    @Override
    public final void removeElement(UUID partId) {
	PartHeader header = headerButtons.get(partId);
	toolBar.getItems().remove(header);
	headerButtons.remove(partId);
	Iterator<Part> iterator = parts.iterator();
	boolean currentActiveEffected = false;
	while (iterator.hasNext()) {
	    Part part = iterator.next();
	    if (partId.equals(part.getId())) {
		iterator.remove();
		if (part.getContent() == borderPane.getCenter()) {
		    currentActiveEffected = true;
		}
	    }
	}
	if (currentActiveEffected) {
	    if (parts.size() > 0) {
		Part part = parts.get(parts.size() - 1);
		setActive(part.getId());
	    } else {
		setActive(null);
	    }
	}
    }

    @Override
    public final void removeElement(PerspectiveElement element) {
	removeElement(element.getId());
    }

    public final void setActive(UUID partId) {
	headerToolBar.getChildren().clear();
	if (partId != null) {
	    Iterator<Part> iterator = parts.iterator();
	    while (iterator.hasNext()) {
		Part part = iterator.next();
		if (partId.equals(part.getId())) {
		    headerButtons.forEach((id, header) -> header.setActive(false));
		    headerButtons.get(partId).setActive(true);
		    borderPane.setCenter(part.getContent());
		    Optional<PartHeaderToolBar> partToolBar = part.getToolBar();
		    if (partToolBar.isPresent()) {
			ToolBar ptb = partToolBar.get();
			ptb.getItems().forEach(item -> {
			    if (Labeled.class.isAssignableFrom(item.getClass())) {
				((Labeled) item).setContentDisplay(toolBarContentDisplay.get());
			    }
			    headerToolBar.getChildren().add(item);

			});
		    }
		    break;
		}
	    }
	} else {
	    headerButtons.forEach((id, header) -> header.setActive(false));
	    borderPane.setCenter(null);
	    getPerspectiveHandler().removeEmptyElements();
	}
    }

    public boolean hasParts() {
	return !parts.isEmpty();
    }

}
