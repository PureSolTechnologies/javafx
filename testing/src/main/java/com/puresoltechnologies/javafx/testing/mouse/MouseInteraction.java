package com.puresoltechnologies.javafx.testing.mouse;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.CountDownLatch;

import com.puresoltechnologies.javafx.testing.OpenJFXRobot;
import com.puresoltechnologies.javafx.testing.select.NodeSelection;
import com.puresoltechnologies.javafx.testing.utils.CoordinateUtils;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.robot.Robot;

/**
 * This interface is used as mix-in for {@link NodeSelection}s with mouse
 * interaction.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public interface MouseInteraction<N extends Node> {

    N getNode();

    default void click(MouseButton mouseButton) {
	N node = getNode();
	Point2D coordinates = CoordinateUtils.getScreenCenterCoordinates(node);
	CountDownLatch latch = new CountDownLatch(1);
	Platform.runLater(() -> {
	    try {
		Robot robot = OpenJFXRobot.getRobot();
		robot.mouseMove(coordinates.getX(), coordinates.getY());
		robot.mouseClick(mouseButton);
	    } finally {
		latch.countDown();
	    }
	});
	try {
	    latch.await();
	} catch (InterruptedException e) {
	    fail("Await timed out.", e);
	}
    }

}
