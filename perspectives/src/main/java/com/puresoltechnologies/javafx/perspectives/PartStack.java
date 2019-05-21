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
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Labeled;
import javafx.scene.control.ToolBar;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This perspective element is providing functionality to stack multiple
 * {@link Part} objects into an element with tabs.
 *
 * @author Rick-Rainer Ludwig
 */
public class PartStack extends AbstractPerspectiveElement {

    private static final ObjectProperty<ContentDisplay> toolBarContentDisplay = Preferences
	    .getProperty(PerspectiveProperties.partHeaderToolbarContentDisplay);

    class DropAreas {
	private final double width;
	private final double height;
	private final double toolBarHeight;
	private final double contentHeight;
	private final Polygon top;
	private final Polygon right;
	private final Polygon lower;
	private final Polygon left;
	private final Rectangle innerRectangle;

	DropAreas(PartStack partStack) {
	    width = borderPane.getWidth();
	    height = borderPane.getHeight();
	    toolBarHeight = toolBarBox.getHeight();
	    contentHeight = borderPane.getHeight() - toolBarHeight;

	    double leftInnerPosition;
	    double rightInnerPosition;
	    double topInnerPosition;
	    double lowerInnerPosition;
	    if (width > contentHeight) {
		topInnerPosition = toolBarHeight + (contentHeight * 0.5);
		lowerInnerPosition = topInnerPosition;
		leftInnerPosition = contentHeight * 0.5;
		rightInnerPosition = width - (contentHeight * 0.5);
	    } else {
		leftInnerPosition = width * 0.5;
		rightInnerPosition = leftInnerPosition;
		topInnerPosition = toolBarHeight + (width * 0.5);
		lowerInnerPosition = height - (width * 0.5);
	    }
	    // shapes
	    top = new Polygon(0.0, toolBarHeight, width, toolBarHeight, rightInnerPosition, topInnerPosition,
		    leftInnerPosition, topInnerPosition);
	    right = new Polygon(width, toolBarHeight, width, height, rightInnerPosition, lowerInnerPosition,
		    rightInnerPosition, topInnerPosition);
	    lower = new Polygon(0.0, height, leftInnerPosition, lowerInnerPosition, rightInnerPosition,
		    lowerInnerPosition, width, height);
	    left = new Polygon(0.0, toolBarHeight, leftInnerPosition, topInnerPosition, leftInnerPosition,
		    lowerInnerPosition, 0.0, height);
	    innerRectangle = new Rectangle(0, 0, width, toolBarHeight);
	}

	public double getWidth() {
	    return width;
	}

	public double getHeight() {
	    return height;
	}

	public double getToolBarHeight() {
	    return toolBarHeight;
	}

	public double getContentHeight() {
	    return contentHeight;
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

    private final HBox toolBarBox = new HBox();
    private final PartHeaderToolBar toolBar = new PartHeaderToolBar(toolBarBox);
    private final List<Part> parts = new ArrayList<>();
    private final Map<UUID, PartHeader> headerButtons = new HashMap<>();
    private final BorderPane borderPane = new BorderPane();

    public PartStack() {
	super();
	toolBarBox.setAlignment(Pos.CENTER_RIGHT);

	borderPane.setId(UUID.randomUUID().toString());
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
	Dragboard dragboard = event.getDragboard();
	if (dragboard.hasContent(PartDragDataFormat.get())) {
	    DropAreas dropAreas = new DropAreas(this);
	    PerspectiveHandler perspectiveHandler = getPerspectiveHandler();
	    PartDragData dragData = (PartDragData) dragboard.getContent(PartDragDataFormat.get());
	    UUID partStackId = dragData.getPartStackId();
	    UUID partId = dragData.getPartId();
	    UUID id = getId();
	    if (dropAreas.isTop(event)) {
		perspectiveHandler.movePartToNewTop(partStackId, partId, id);
		success = true;
	    } else if (dropAreas.isRight(event)) {
		perspectiveHandler.movePartToNewRight(partStackId, partId, id);
		success = true;
	    } else if (dropAreas.isLower(event)) {
		perspectiveHandler.movePartToNewLower(partStackId, partId, id);
		success = true;
	    } else if (dropAreas.isLeft(event)) {
		perspectiveHandler.movePartToNewLeft(partStackId, partId, id);
		success = true;
	    } else if (dropAreas.isInnerRectangle(event)) {
		perspectiveHandler.movePartToStack(partStackId, partId, id);
		success = true;
	    }
	}
	event.setDropCompleted(success);
	event.consume();
    }

    private void drawDragCanvas(Canvas canvas, DragEvent event) {
	GraphicsContext gc = canvas.getGraphicsContext2D();
	DropAreas dropAreas = new DropAreas(this);
	gc.clearRect(0.0, 0.0, dropAreas.getWidth(), dropAreas.getToolBarHeight() + dropAreas.getContentHeight());
	drawDropAreas(gc, event, dropAreas);
	drawDragResults(gc, event, dropAreas);
    }

    private void drawDropAreas(GraphicsContext gc, DragEvent event, DropAreas dropAreas) {
	gc.setGlobalAlpha(0.25);
	// edges
	gc.setFill(Color.RED);
	drawPolygon(gc, dropAreas.getLeft());
	gc.setFill(Color.GREEN);
	drawPolygon(gc, dropAreas.getRight());
	gc.setFill(Color.BLUE);
	drawPolygon(gc, dropAreas.getTop());
	gc.setFill(Color.YELLOW);
	drawPolygon(gc, dropAreas.getLower());
	// box
	gc.setFill(Color.MOCCASIN);
	Rectangle innerRectangle = dropAreas.getInnerRectangle();
	gc.fillRect(innerRectangle.getX(), innerRectangle.getY(), innerRectangle.getWidth(),
		innerRectangle.getHeight());
    }

    private void drawPolygon(GraphicsContext gc, Polygon polygon) {
	ObservableList<Double> points = polygon.getPoints();
	int numPoints = points.size() / 2;
	double[] x = new double[numPoints];
	double[] y = new double[numPoints];
	for (int i = 0; i < (numPoints * 2); i++) {
	    Double point = points.get(i);
	    if ((i % 2) == 0) {
		x[i / 2] = point;
	    } else {
		y[i / 2] = point;
	    }
	}
	gc.fillPolygon(x, y, numPoints);
    }

    private void drawDragResults(GraphicsContext gc, DragEvent event, DropAreas dropAreas) {
	gc.setGlobalAlpha(0.5);
	gc.setStroke(Color.GRAY);
	gc.setLineWidth(2.0);
	gc.setLineDashes(4.0, 4.0);

	gc.setGlobalAlpha(0.5);
	gc.setStroke(Color.GRAY);
	double width = dropAreas.getWidth();
	// double height = dropAreas.getHeight();
	double toolBarHeight = dropAreas.getToolBarHeight();
	double contentHeight = dropAreas.getContentHeight();
	if (dropAreas.isTop(event)) {
	    gc.strokeRect(0.0, toolBarHeight, width, contentHeight / 2.0);
	} else if (dropAreas.isRight(event)) {
	    gc.strokeRect(width / 2.0, toolBarHeight, width / 2.0, contentHeight);
	} else if (dropAreas.isLower(event)) {
	    gc.strokeRect(0.0, toolBarHeight + (contentHeight / 2.0), width, contentHeight / 2.0);
	} else if (dropAreas.isLeft(event)) {
	    gc.strokeRect(0.0, toolBarHeight, width / 2.0, contentHeight);
	} else if (dropAreas.isInnerRectangle(event)) {
	    gc.strokeRect(0.0, 0.0, width, toolBarHeight);
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
	PartHeader header = new PartHeader(this, part);
	headerButtons.put(part.getId(), header);
	parts.add(part);
	FXThreads.proceedOnFXThread(() -> {
	    ObservableList<Node> items = toolBar.getItems();
	    items.add(index, header);
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
	toolBarBox.getChildren().clear();
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
			    toolBarBox.getChildren().add(item);
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

    public void closeOtherParts(Part part) {
	parts.stream().filter(p -> p != part).forEach(p -> closePart(p));
    }

    public void closeAll() {
	parts.forEach(part -> closePart(part));
    }

    public void detach(Part part) {
	removeElement(part);
	PerspectiveContainerPane perspectiveContainerPane = new PerspectiveContainerPane();
	PartStack partStack = new PartStack();
	partStack.addElement(part);
	perspectiveContainerPane.setRootElement(partStack);

	Scene scene = new Scene(perspectiveContainerPane);

	Stage stage = new Stage(StageStyle.DECORATED);
	stage.setScene(scene);
	stage.show();
    }

}
