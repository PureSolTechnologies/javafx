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

    private static final long serialVersionUID = -8205338293180406512L;

    private static final double DRAG_EDGE_FRACTION = 0.2;

    private static final ObjectProperty<ContentDisplay> toolBarContentDisplay = Preferences
	    .getProperty(PerspectiveProperties.partHeaderToolbarContentDisplay);

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
	    double width = borderPane.getWidth();
	    double height = borderPane.getHeight();
	    double leftInnerPosition = width * DRAG_EDGE_FRACTION;
	    double rightInnerPosition = width * (1.0 - DRAG_EDGE_FRACTION);
	    double topInnerPosition = height * DRAG_EDGE_FRACTION;
	    double lowerInnerPosition = height * (1.0 - DRAG_EDGE_FRACTION);
	    // shapes
	    Polygon top = new Polygon(0.0, 0.0, width, 0.0, rightInnerPosition, topInnerPosition, leftInnerPosition,
		    topInnerPosition);
	    Polygon right = new Polygon(width, 0.0, width, height, rightInnerPosition, lowerInnerPosition,
		    rightInnerPosition, topInnerPosition);
	    Polygon lower = new Polygon(0.0, height, leftInnerPosition, lowerInnerPosition, rightInnerPosition,
		    lowerInnerPosition, width, height);
	    Polygon left = new Polygon(0.0, 0.0, leftInnerPosition, topInnerPosition, leftInnerPosition,
		    lowerInnerPosition, 0.0, height);
	    Rectangle innerRectangle = new Rectangle(leftInnerPosition, topInnerPosition,
		    (1.0 - 2.0 * DRAG_EDGE_FRACTION) * width, (1.0 - 2.0 * DRAG_EDGE_FRACTION) * height);
	    PartDragData dragData = (PartDragData) event.getDragboard().getContent(PartDragDataFormat.get());
	    if (top.contains(event.getX(), event.getY())) {
		getPerspectiveHandler().movePartToNewTop(dragData.getPartStackId(), dragData.getPartId(), getId());
	    } else if (right.contains(event.getX(), event.getY())) {
		getPerspectiveHandler().movePartToNewRight(dragData.getPartStackId(), dragData.getPartId(), getId());
	    } else if (lower.contains(event.getX(), event.getY())) {
		getPerspectiveHandler().movePartToNewLower(dragData.getPartStackId(), dragData.getPartId(), getId());
	    } else if (left.contains(event.getX(), event.getY())) {
		getPerspectiveHandler().movePartToNewLeft(dragData.getPartStackId(), dragData.getPartId(), getId());
	    } else if (innerRectangle.contains(event.getX(), event.getY())) {
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
	// drawDragAreas(gc, width, height, leftInnerPosition, rightInnerPosition,
	// topInnerPosition, lowerInnerPosition);
	drawDragResults(gc, event, width, height);
    }

    private void drawDragAreas(GraphicsContext gc, double width, double height) {
	double leftInnerPosition = width * DRAG_EDGE_FRACTION;
	double rightInnerPosition = width * (1.0 - DRAG_EDGE_FRACTION);
	double topInnerPosition = height * DRAG_EDGE_FRACTION;
	double lowerInnerPosition = height * (1.0 - DRAG_EDGE_FRACTION);
	gc.setGlobalAlpha(0.25);
	// edges
	gc.setFill(Color.RED);
	gc.fillPolygon(new double[] { 0.0, width, rightInnerPosition, leftInnerPosition },
		new double[] { 0.0, 0.0, topInnerPosition, topInnerPosition }, 4);
	gc.setFill(Color.GREEN);
	gc.fillPolygon(new double[] { width, width, rightInnerPosition, rightInnerPosition },
		new double[] { 0.0, height, lowerInnerPosition, topInnerPosition }, 4);
	gc.setFill(Color.BLUE);
	gc.fillPolygon(new double[] { 0.0, leftInnerPosition, rightInnerPosition, width },
		new double[] { height, lowerInnerPosition, lowerInnerPosition, height }, 4);
	gc.setFill(Color.YELLOW);
	gc.fillPolygon(new double[] { 0.0, leftInnerPosition, leftInnerPosition, 0.0 },
		new double[] { 0.0, topInnerPosition, lowerInnerPosition, height }, 4);
	// box
	gc.setFill(Color.MOCCASIN);
	gc.fillRect(leftInnerPosition, topInnerPosition, (1.0 - 2.0 * DRAG_EDGE_FRACTION) * width,
		(1.0 - 2.0 * DRAG_EDGE_FRACTION) * height);
    }

    private void drawDragResults(GraphicsContext gc, DragEvent event, double width, double height) {
	gc.setGlobalAlpha(0.5);
	gc.setStroke(Color.GRAY);
	gc.setLineWidth(2.0);
	gc.setLineDashes(4.0, 4.0);
	double leftInnerPosition = width * DRAG_EDGE_FRACTION;
	double rightInnerPosition = width * (1.0 - DRAG_EDGE_FRACTION);
	double topInnerPosition = height * DRAG_EDGE_FRACTION;
	double lowerInnerPosition = height * (1.0 - DRAG_EDGE_FRACTION);
	// shapes
	Polygon top = new Polygon(0.0, 0.0, width, 0.0, rightInnerPosition, topInnerPosition, leftInnerPosition,
		topInnerPosition);
	Polygon right = new Polygon(width, 0.0, width, height, rightInnerPosition, lowerInnerPosition,
		rightInnerPosition, topInnerPosition);
	Polygon lower = new Polygon(0.0, height, leftInnerPosition, lowerInnerPosition, rightInnerPosition,
		lowerInnerPosition, width, height);
	Polygon left = new Polygon(0.0, 0.0, leftInnerPosition, topInnerPosition, leftInnerPosition, lowerInnerPosition,
		0.0, height);
	Rectangle innerRectangle = new Rectangle(leftInnerPosition, topInnerPosition,
		(1.0 - 2.0 * DRAG_EDGE_FRACTION) * width, (1.0 - 2.0 * DRAG_EDGE_FRACTION) * height);
	gc.setGlobalAlpha(0.5);
	gc.setStroke(Color.GRAY);
	if (top.contains(event.getX(), event.getY())) {
	    gc.strokeRect(0.0, 0.0, width, height / 2.0);
	} else if (right.contains(event.getX(), event.getY())) {
	    gc.strokeRect(width / 2.0, 0.0, width / 2.0, height);
	} else if (lower.contains(event.getX(), event.getY())) {
	    gc.strokeRect(0.0, height / 2.0, width, height / 2.0);
	} else if (left.contains(event.getX(), event.getY())) {
	    gc.strokeRect(0.0, 0.0, width / 2.0, height);
	} else if (innerRectangle.contains(event.getX(), event.getY())) {
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
    public final void addElement(PerspectiveElement e) {
	if (!Part.class.isAssignableFrom(e.getClass())) {
	    throw new IllegalArgumentException(
		    "Part stacks can only contain parts. Type '" + e.getClass().getName() + "' is not supported.");
	}
	Part part = (Part) e;
	PartHeader button = new PartHeader(this, part);
	headerButtons.put(part.getId(), button);
	parts.add(part);
	FXThreads.proceedOnFXThread(() -> {
	    ObservableList<Node> items = toolBar.getItems();
	    items.add(items.size() - 1, button);
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
