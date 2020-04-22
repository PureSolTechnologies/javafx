package com.puresoltechnologies.javafx.testing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.puresoltechnologies.javafx.testing.ReplayTimings.Speed;
import com.puresoltechnologies.javafx.testing.select.Selection;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class tests the screenshot feature of tests.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public class ScreenshotIT extends OpenJFXTest {

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
        Selection<Label> label = findNodeById(Label.class, "test.label");
        assertTrue(label.isPresent());
        Selection<Button> button = findButtonById("test.button");
        assertTrue(button.isPresent());
        click("#test.button");
        File snapshotFile = screenshot();
        assertTrue(snapshotFile.exists(), "Snapshot file was not created.");
    }

}
