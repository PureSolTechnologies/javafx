package com.puresoltechnologies.javafx.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puresoltechnologies.javafx.showroom.ShowRoom;
import com.puresoltechnologies.javafx.testing.OpenJFXRobot;
import com.puresoltechnologies.javafx.testing.OpenJFXTest;
import com.puresoltechnologies.javafx.testing.ReplayTimings;
import com.puresoltechnologies.javafx.testing.ReplayTimings.Speed;
import com.puresoltechnologies.javafx.utils.FXThreads;

import javafx.scene.input.KeyCode;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SimpleTest extends OpenJFXTest {

    private static ShowRoom application;
    private static Stage stage;

    @BeforeAll
    public static void setup() {
	ReplayTimings.setSpeed(Speed.MEDIUM);
    }

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
	Thread.sleep(2000);
    }

    @Test
    public void test() throws InterruptedException {
	System.out.println("=== FileMenu ===");
	click("#menu.main.file");
	System.out.println("=== ExitItem ===");
	click("#menu.main.file.exit");
    }

    @Test
    public void testKeys() throws InterruptedException {
	CountDownLatch latch = new CountDownLatch(1);
	FXThreads.runOnFXThread(() -> {
	    try {
		Robot robot = OpenJFXRobot.getRobot();
		Thread.sleep(100);
		robot.keyPress(KeyCode.ALT);
		Thread.sleep(100);
		robot.keyType(KeyCode.F);
		Thread.sleep(100);
		robot.keyRelease(KeyCode.ALT);
		Thread.sleep(1000);
		robot.keyType(KeyCode.X);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } finally {
		latch.countDown();
	    }
	});
	latch.await(1, TimeUnit.MINUTES);
    }

}
