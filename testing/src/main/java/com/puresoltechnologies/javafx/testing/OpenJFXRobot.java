package com.puresoltechnologies.javafx.testing;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javafx.scene.robot.Robot;

public final class OpenJFXRobot {

    private static Robot robot = null;

    public static void initialize() {
	assertNull(robot, "Robot was already initilized.");
	robot = new Robot();
    }

    public static void shutdown() {
	assertNotNull(robot, "Robot was not initilized,  yet.");
	robot = null;
    }

    public static Robot getRobot() {
	return robot;
    }

}
