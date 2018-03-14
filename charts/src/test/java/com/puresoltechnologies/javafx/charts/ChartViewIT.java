package com.puresoltechnologies.javafx.charts;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChartViewIT extends ApplicationTest {

    ChartView chartView;

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
