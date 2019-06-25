package com.puresoltechnologies.javafx.testing;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javafx.scene.robot.Robot;

public final class OpenJFXRobot {

    private static Robot robot = null;

    public static void initialize() {
	assertNull(robot, "Robot was already initialized.");
	robot = new Robot();
	System.out.println("OpenJFX Robot was initialized.");
    }

    public static void shutdown() {
	assertNotNull(robot, "Robot was not initilaized,  yet.");
	robot = null;
	System.out.println("OpenJFX Robot was shut down.");
    }

    public static Robot getRobot() {
	return robot;
    }

}
