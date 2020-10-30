package com.puresoltechnologies.javafx.test.charts;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.puresoltechnologies.javafx.charts.ChartView;
import com.puresoltechnologies.javafx.preferences.Preferences;
import com.puresoltechnologies.javafx.testing.AbstractUITest;
import com.puresoltechnologies.javafx.testing.ReplayTimings;
import com.puresoltechnologies.javafx.testing.ReplayTimings.Speed;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChartsViewIT extends AbstractUITest {

    @BeforeAll
    public static void initialize() throws IOException {
        Preferences.initialize();
        ReplayTimings.setSpeed(Speed.MEDIUM);
        ReplayTimings.setNodeRetrievalDelay(0);
    }

    @AfterAll
    public static void shutdown() {
        Preferences.shutdown();
    }

    @Override
    protected Stage start() {
        ChartView chartView = new ChartView("Chart Test");

        Scene scene = new Scene(chartView);
        Stage stage = new Stage();
        stage.setTitle("Wizard Test");
        stage.setScene(scene);
        return stage;
    }

    @Override
    protected void stop() {
        // Intentionally left blank
    }

    @Test
    public void test() throws InterruptedException {
        Thread.sleep(1000);
    }
}
