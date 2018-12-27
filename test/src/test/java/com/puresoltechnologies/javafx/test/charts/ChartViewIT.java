package com.puresoltechnologies.javafx.test.charts;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import com.puresoltechnologies.javafx.charts.ChartView;
import com.puresoltechnologies.javafx.preferences.Preferences;

import javafx.scene.Scene;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class ChartViewIT extends ApplicationTest {

    ChartView chartView;

    @BeforeAll
    public static void initialize() throws IOException {
	Preferences.initialize();
    }

    @AfterAll
    public static void shutdown() {
	Preferences.shutdown();
    }

    @Override
    public void start(Stage stage) {
	chartView = new ChartView();
	Scene scene = new Scene(chartView, 800, 600);
	stage.setScene(scene);
	stage.show();
    }

    @Test
    public void testPerspectiveDialog() throws InterruptedException {
	Thread.sleep(1000);
    }

}
