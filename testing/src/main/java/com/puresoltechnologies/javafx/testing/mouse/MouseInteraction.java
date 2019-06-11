package com.puresoltechnologies.javafx.testing.mouse;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.CountDownLatch;

import com.puresoltechnologies.javafx.testing.OpenJFXRobot;
import com.puresoltechnologies.javafx.testing.ReplayTimings;
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
	click(coordinates, mouseButton);
    }

    default void click(Point2D coordinates, MouseButton mouseButton) {
	moveTo(coordinates);
	CountDownLatch latch = new CountDownLatch(1);
	Platform.runLater(() -> {
	    try {
		Robot robot = OpenJFXRobot.getRobot();
		Thread.sleep(ReplayTimings.getMouseClickDelay());
		robot.mouseClick(mouseButton);
	    } catch (InterruptedException e) {
		fail(e);
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

    default void moveTo(Point2D coordinates) {
	CountDownLatch latch = new CountDownLatch(1);
	Platform.runLater(() -> {
	    try {
		Robot robot = OpenJFXRobot.getRobot();
		Point2D currentPosition = robot.getMousePosition();
		double deltaX = coordinates.getX() - currentPosition.getX();
		double deltaY = coordinates.getY() - currentPosition.getY();
		int steps = (int) Math.max(Math.abs(deltaX), Math.abs(deltaY));
		double stepX = deltaX / steps;
		double stepY = deltaY / steps;
		for (int i = 1; i < (steps - 1); i++) {
		    Thread.sleep(ReplayTimings.getMouseMoveDelay());
		    robot.mouseMove(currentPosition.getX() + (i * stepX), //
			    currentPosition.getY() + (i * stepY));
		}
		Thread.sleep(ReplayTimings.getMouseMoveDelay());
		robot.mouseMove(coordinates.getX(), coordinates.getY());
	    } catch (InterruptedException e) {
		fail(e);
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
