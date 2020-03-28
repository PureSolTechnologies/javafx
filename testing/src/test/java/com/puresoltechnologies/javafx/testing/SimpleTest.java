package com.puresoltechnologies.javafx.testing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.puresoltechnologies.javafx.testing.ReplayTimings.Speed;
import com.puresoltechnologies.javafx.testing.select.Selection;
import com.puresoltechnologies.javafx.testing.utils.ComponentTreePrinter;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	new ComponentTreePrinter(System.out).print();
	Selection<Label> label = findNodeById(Label.class, "test.label");
	assertTrue(label.isPresent());
	Selection<Button> button = findButtonById("test.button");
	assertTrue(button.isPresent());
	click("#test.button");
	Thread.sleep(3000);
    }

}
