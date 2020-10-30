package com.puresoltechnologies.javafx.test.extensions.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.puresoltechnologies.javafx.extensions.status.BooleanLEDRenderer;
import com.puresoltechnologies.javafx.extensions.status.StatusIndicator;
import com.puresoltechnologies.javafx.testing.AbstractUITest;
import com.puresoltechnologies.javafx.testing.ReplayTimings;
import com.puresoltechnologies.javafx.testing.ReplayTimings.Speed;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class StatusIndicatorIT extends AbstractUITest {

    private StatusIndicator<Boolean> statusIndicator;

    @BeforeAll
    public static void initialize() {
        ReplayTimings.setSpeed(Speed.MEDIUM);
        ReplayTimings.setNodeRetrievalDelay(0);
    }

    @Override
    protected Stage start() {
        statusIndicator = new StatusIndicator<>(new BooleanLEDRenderer());
        statusIndicator.setText("Boolean LED");
        Scene scene = new Scene(statusIndicator);
        Stage stage = new Stage();
        stage.setTitle("Wizard Test");
        stage.setScene(scene);
        return stage;
    }

    @Override
    protected void stop() {
        // intentionally left empty
    }

    @Test
    public void test() throws InterruptedException {
        Thread.sleep(1000);
        statusIndicator.setStatus(true);
        Thread.sleep(1000);
        statusIndicator.setStatus(false);
        Thread.sleep(1000);
    }
}
