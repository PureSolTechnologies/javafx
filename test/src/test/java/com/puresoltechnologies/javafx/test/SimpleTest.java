package com.puresoltechnologies.javafx.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puresoltechnologies.javafx.showroom.ShowRoom;
import com.puresoltechnologies.javafx.testing.AbstractOpenJFXTest;
import com.puresoltechnologies.javafx.testing.OpenJFXRobot;
import com.puresoltechnologies.javafx.testing.ReplayTimings;
import com.puresoltechnologies.javafx.testing.ReplayTimings.Speed;
import com.puresoltechnologies.javafx.utils.FXThreads;

import javafx.scene.input.KeyCode;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SimpleTest extends AbstractOpenJFXTest {

    @BeforeAll
    public static void setup() {
        ReplayTimings.setSpeed(Speed.MEDIUM);
    }

    private final ShowRoom application = new ShowRoom();

    @Override
    protected Stage start() {
        try {
            Stage stage = new Stage(StageStyle.DECORATED);
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
    public void wait2sec() throws InterruptedException {
        Thread.sleep(1000);
    }

    @Test
    public void test() throws InterruptedException {
        System.out.println("=== FileMenu ===");
        click("#menu.main.file");
        System.out.println("=== ExitItem ===");
        click("#menu.main.file.exit");
    }

    @Test
    public void test2() throws InterruptedException {
        System.out.println("=== FileMenu ===");
        findMenuByText("_File").click();
        System.out.println("=== ExitItem ===");
        findMenuByText("E_xit").click();
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
