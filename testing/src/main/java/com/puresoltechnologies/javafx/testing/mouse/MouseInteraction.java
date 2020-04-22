package com.puresoltechnologies.javafx.testing.mouse;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.puresoltechnologies.javafx.testing.OpenJFXRobot;
import com.puresoltechnologies.javafx.testing.ReplayTimings;
import com.puresoltechnologies.javafx.testing.select.NodeSearch;
import com.puresoltechnologies.javafx.testing.select.Selection;
import com.puresoltechnologies.javafx.testing.utils.CoordinateUtils;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.input.MouseButton;
import javafx.scene.robot.Robot;

/**
 * This interface is used as mix-in for mouse interaction.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public interface MouseInteraction extends NodeSearch {

    default boolean hasMouse() {
        Robot robot = OpenJFXRobot.getRobot();
        Point2D currentPosition = robot.getMousePosition();
        if ((currentPosition.getX() < 0.0) || (currentPosition.getY() < 0.0)) {
            System.err.println("No working mouse found.");
            return false;
        }
        if ((currentPosition.getX() > 10_000.0) || (currentPosition.getY() > 10_000.0)) {
            System.err.println("No working mouse found.");
            return false;
        }
        return true;
    }

    default void click(String id) {
        click(id, MouseButton.PRIMARY);
    }

    default void click(String id, MouseButton mouseButton) {
        if (id.startsWith("#")) {
            Selection<Node> node = findNodeById(Node.class, id.substring(1));
            click(node.getNode(), mouseButton);
        } else {
            throw new IllegalArgumentException("The provided id '" + id + "' does not start with a sharp '#'.");
        }
    }

    default void click(Node node) {
        click(node, MouseButton.PRIMARY);
    }

    default void click(Node node, MouseButton mouseButton) {
        if (hasMouse()) {
            Point2D coordinates = CoordinateUtils.getScreenCenterCoordinates(node);
            click(coordinates, mouseButton);
        } else {
            System.out.println("No working mouse found. Trying fall-back scenario...");
            if (ButtonBase.class.isAssignableFrom(node.getClass())) {
                System.out.println("Button base compatible node found. Invoke fire() directly...");
                ((ButtonBase) node).fire();
            } else {
                throw new IllegalStateException(
                        "Current system seems not to have a valid mouse and no fallback available.");
            }
        }
    }

    default void click(Point2D coordinates, MouseButton mouseButton) {
        try {
            moveTo(coordinates);
            Thread.sleep(ReplayTimings.getMouseClickDelay());
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> {
                try {
                    Robot robot = OpenJFXRobot.getRobot();
                    robot.mouseClick(mouseButton);
                } finally {
                    latch.countDown();
                }
            });
            assertTrue(latch.await(10, TimeUnit.SECONDS));
            Thread.sleep(ReplayTimings.getMouseClickDelay());
        } catch (InterruptedException e) {
            fail("Await timed out.", e);
        }
    }

    default void moveTo(Point2D coordinates) {
        if (!hasMouse()) {
            throw new IllegalStateException("Current system seems not to have a valid mouse.");
        }
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                Robot robot = OpenJFXRobot.getRobot();
                Point2D currentPosition = robot.getMousePosition();
                System.err.println("Mouse start position: " + currentPosition.getX() + ", " + currentPosition.getY());
                System.err.println("Mouse target position: " + coordinates.getX() + ", " + coordinates.getY());
                double deltaX = coordinates.getX() - currentPosition.getX();
                double deltaY = coordinates.getY() - currentPosition.getY();
                int steps = (int) Math.max(Math.abs(deltaX), Math.abs(deltaY));
                System.err.println("Mouse number of steps: " + steps);
                if (steps > 1920) {
                    steps = 1920;
                    System.err.println("Mouse number of adjusted steps: " + steps);
                }
                System.err.println("Mouse number of steps: " + steps);
                double stepX = deltaX / steps;
                double stepY = deltaY / steps;
                System.err.println("Mouse steps: " + stepX + ", " + stepY);
                for (int i = 1; i < (steps - 1); i++) {
                    Thread.sleep(ReplayTimings.getMouseMoveDelay());
                    robot.mouseMove(currentPosition.getX() + (i * stepX), //
                            currentPosition.getY() + (i * stepY));
                }
                Thread.sleep(ReplayTimings.getMouseMoveDelay());
                System.err.println("Mouse end position: " + coordinates.getX() + ", " + coordinates.getY());
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
