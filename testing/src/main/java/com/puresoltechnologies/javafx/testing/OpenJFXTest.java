package com.puresoltechnologies.javafx.testing;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public abstract class OpenJFXTest {

    private static Robot robot;
    private Stage stage;
    private Scene scene;

    protected abstract Parent getRootNode();

    @BeforeAll
    public static void startJavaFX() throws InterruptedException {
	CountDownLatch latch = new CountDownLatch(1);
	Platform.startup(() -> {
	    try {
		robot = new Robot();
	    } finally {
		latch.countDown();
	    }
	});
	latch.await(10, TimeUnit.SECONDS);
    }

    @AfterAll
    public static void stopJavaFX() {
	Platform.exit();
    }

    @BeforeEach
    public void setupStage() throws InterruptedException {
	CountDownLatch latch = new CountDownLatch(1);
	Platform.runLater(() -> {
	    try {
		stage = new Stage();
		scene = new Scene(getRootNode(), 320, 200);
		stage.setScene(scene);
		stage.show();
	    } finally {
		latch.countDown();
	    }
	});
	latch.await(10, TimeUnit.SECONDS);
    }

    @AfterEach
    public void destroyStage() {
	Platform.runLater(() -> {
	    stage.hide();
	    scene = null;
	    stage = null;
	});
    }

    protected List<Node> findAllNodes(Parent rootNode) {
	List<Node> nodes = new ArrayList<>();
	addAllChildren(rootNode, nodes);
	return nodes;
    }

    private void addAllChildren(Parent parent, List<Node> nodes) {
	for (Node node : parent.getChildrenUnmodifiable()) {
	    nodes.add(node);
	    if (node instanceof Parent) {
		addAllChildren((Parent) node, nodes);
	    }
	}
    }

    protected List<Node> findNodes(Parent rootNode, NodeFilter filter) {
	List<Node> nodes = new ArrayList<>();
	addAllChildren(rootNode, nodes, filter);
	return nodes;
    }

    private void addAllChildren(Parent parent, List<Node> nodes, NodeFilter filter) {
	for (Node node : parent.getChildrenUnmodifiable()) {
	    if (filter.keep(node)) {
		nodes.add(node);
	    }
	    if (node instanceof Parent) {
		addAllChildren((Parent) node, nodes, filter);
	    }
	}
    }

    protected Node findNode(Parent rootNode, NodeFilter filter) {
	for (Node node : rootNode.getChildrenUnmodifiable()) {
	    if (filter.keep(node)) {
		return node;
	    }
	    if (node instanceof Parent) {
		Node foundNode = findNode((Parent) node, filter);
		if (foundNode != null) {
		    return foundNode;
		}
	    }
	}
	return null;
    }

    protected Node findNodeById(String id) {
	return findNode(scene.getRoot(), node -> id.equals(node.getId()));
    }

    protected void clickMouse(Node node) {
	Point2D coordinates = getScreenCenterCoordinates(node);
	CountDownLatch latch = new CountDownLatch(1);
	Platform.runLater(() -> {
	    try {
		robot.mouseMove(coordinates.getX(), coordinates.getY());
		robot.mouseClick(MouseButton.PRIMARY);
	    } finally {
		latch.countDown();
	    }
	});
	try {
	    latch.await();
	} catch (InterruptedException e) {
	    fail("Await timed out.", e);
	}
    }

    private Rectangle getScreenCoordinates(Node node) {
	Bounds bounds = node.getBoundsInLocal();
	Bounds screenBounds = node.localToScreen(bounds);
	int x = (int) screenBounds.getMinX();
	int y = (int) screenBounds.getMinY();
	int width = (int) screenBounds.getWidth();
	int height = (int) screenBounds.getHeight();
	return new Rectangle(x, y, width, height);
    }

    private Point2D getScreenCenterCoordinates(Node node) {
	Rectangle screenCoordinates = getScreenCoordinates(node);
	return new Point2D( //
		screenCoordinates.getX() + (screenCoordinates.getWidth() / 2.0), //
		screenCoordinates.getY() + (screenCoordinates.getHeight() / 2.0) //
	);
    }
}
