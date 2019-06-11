package com.puresoltechnologies.javafx.testing;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.puresoltechnologies.javafx.testing.ReplayTimings.Speed;
import com.puresoltechnologies.javafx.testing.select.NodeSelection;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SimpleTest extends OpenJFXTest {

    @BeforeAll
    public static void setupTest() {
	ReplayTimings.setSpeed(Speed.MEDIUM);
    }

    @Override
    protected Stage start() {
	VBox vBox = new VBox();
	Label label = new Label("Test");
	label.setId("test.label");
	Button button = new Button("Click me!");
	button.setId("test.button");
	button.setOnAction(event -> {
	    System.out.println("button click!");
	});
	vBox.getChildren().addAll(label, button);

	Stage stage = new Stage(StageStyle.DECORATED);
	Scene scene = new Scene(vBox, 320, 200);
	stage.setScene(scene);

	return stage;
    }

    @Override
    protected void stop() {
	// TODO Auto-generated method stub
    }

    @Test
    public void test() throws InterruptedException {

	NodeSelection<Parent> nodeSelection = nodeSelection();
	Label label = (Label) nodeSelection.findNodeById("test.label");
	assertNotNull(label);
	Button button = (Button) nodeSelection.findNodeById("test.button");
	assertNotNull(button);
	nodeSelection.selectById(Button.class, "test.button").click(MouseButton.PRIMARY);
	Thread.sleep(3000);
    }

}
