package com.puresoltechnologies.javafx.test.extensions.dialogs;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.puresoltechnologies.javafx.extensions.dialogs.Dialog;
import com.puresoltechnologies.javafx.test.extensions.dialogs.wizard.TestWizardResult;
import com.puresoltechnologies.javafx.testing.AbstractOpenJFXTest;
import com.puresoltechnologies.javafx.testing.ReplayTimings;
import com.puresoltechnologies.javafx.testing.ReplayTimings.Speed;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class DialogIT extends AbstractOpenJFXTest {

    @BeforeAll
    public static void initialize() {
        ReplayTimings.setSpeed(Speed.MEDIUM);
        ReplayTimings.setNodeRetrievalDelay(0);
    }

    @Override
    protected Stage start() {
        Button buttonShow = new Button("Start Dialog with show()...");
        buttonShow.setId("dialog.start.show");
        buttonShow.setOnAction(event -> {
            Dialog<TestWizardResult> dialog = new Dialog<>();
            dialog.show();
            event.consume();
        });
        Button buttonShowAndWait = new Button("Start Dialog with showAndWait()...");
        buttonShowAndWait.setId("dialog.start.showAndWait");
        buttonShowAndWait.setOnAction(event -> {
            Dialog<String> dialog = new Dialog<>();
            dialog.showAndWait();
            event.consume();
        });
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.getChildren().addAll(buttonShow, buttonShowAndWait);
        Scene scene = new Scene(flowPane);
        Stage stage = new Stage();
        stage.setTitle("Wizard Test");
        stage.setScene(scene);
        return stage;
    }

    @Override
    protected void stop() {
        // TODO Auto-generated method stub

    }

    @Test
    public void testShow() throws InterruptedException {
        findButtonById("dialog.start.show").click();
        Thread.sleep(3000);
        findButtonByText("OK").click();
    }

    @Test
    public void testShowAndWait() throws InterruptedException {
        findButtonById("dialog.start.showAndWait").click();
        Thread.sleep(3000);
        findButtonByText("OK").click();
    }
}
