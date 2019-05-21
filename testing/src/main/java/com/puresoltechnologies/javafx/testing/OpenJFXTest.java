package com.puresoltechnologies.javafx.testing;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class OpenJFXTest {

    private Stage stage;
    private Scene scene;

    protected abstract Parent getRootNode();

    @BeforeAll
    public static void startJavaFX() {
	Platform.startup(() -> {
	});
    }

    @BeforeEach
    public void setupStage() {
	Platform.runLater(() -> {
	    stage = new Stage();
	    scene = new Scene(getRootNode(), 320, 200);
	    stage.setScene(scene);
	    stage.show();
	});
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
	return findNode(getRootNode(), node -> node.getId().equals(id));
    }

}
