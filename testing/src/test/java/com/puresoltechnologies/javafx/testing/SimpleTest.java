package com.puresoltechnologies.javafx.testing;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SimpleTest extends OpenJFXTest {

    @Override
    protected Parent getRootNode() {
	VBox vBox = new VBox();
	Label label = new Label("Test");
	label.setId("test.label");
	Button button = new Button("Click me!");
	button.setId("test.button");
	button.setOnAction(event -> {
	    System.out.println("button click!");
	});
	vBox.getChildren().addAll(label, button);
	return vBox;
    }

    @Test
    public void test() throws InterruptedException {
	Label label = (Label) findNodeById("test.label");
	assertNotNull(label);
	Button button = (Button) findNodeById("test.button");
	assertNotNull(button);
	clickMouse(button);
	Thread.sleep(3000);
    }

}
