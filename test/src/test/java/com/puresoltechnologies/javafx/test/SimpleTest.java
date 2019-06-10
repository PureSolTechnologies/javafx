package com.puresoltechnologies.javafx.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puresoltechnologies.javafx.showroom.ShowRoom;
import com.puresoltechnologies.javafx.testing.OpenJFXTest;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SimpleTest extends OpenJFXTest {

    private static ShowRoom application;
    private static Stage stage;

    @Override
    protected Stage start() {
	application = new ShowRoom();
	try {
	    application.init();
	    Stage stage = new Stage(StageStyle.DECORATED);
	    SimpleTest.stage = stage;
	    application.start(stage);
	    return stage;
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    protected void stop() {
	application.stop();
    }

    @BeforeEach
    public void wait3sec() throws InterruptedException {
	Thread.sleep(1000);
    }

    @Test
    public void test() {
    }

}
