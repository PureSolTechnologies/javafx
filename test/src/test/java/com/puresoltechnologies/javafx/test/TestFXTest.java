package com.puresoltechnologies.javafx.test;

import static org.testfx.assertions.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class TestFXTest extends ApplicationTest {

    private Button button;

    @Start
    void onStart(Stage stage) {
	button = new Button("click me!");
	button.setId("myButton");
	button.setOnAction(actionEvent -> button.setText("clicked!"));
	stage.setScene(new Scene(new StackPane(button), 100, 100));
	stage.show();
    }

    @Test
    void should_contain_button() {
	// expect:
	assertThat(button).hasText("click me!");
	// or (lookup by css id):
	assertThat(lookup("#myButton").queryAs(Button.class)).hasText("click me!");
	// or (lookup by css class):
	assertThat(lookup(".button").queryAs(Button.class)).hasText("click me!");
	// or (query specific type):
	assertThat(lookup(".button").queryButton()).hasText("click me!");
    }

    @Test
    void should_click_on_button(FxRobot robot) {
	// when:
	robot.clickOn(".button");

	// then:
	assertThat(button).hasText("clicked!");
	// or (lookup by css id):
	assertThat(lookup("#myButton").queryAs(Button.class)).hasText("clicked!");
	// or (lookup by css class):
	assertThat(lookup(".button").queryAs(Button.class)).hasText("clicked!");
	// or (query specific type)
	assertThat(lookup(".button").queryButton()).hasText("clicked!");
    }
}