package com.puresoltechnologies.javafx.test.charts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import com.puresoltechnologies.javafx.charts.ChartView;

import javafx.scene.Scene;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
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
