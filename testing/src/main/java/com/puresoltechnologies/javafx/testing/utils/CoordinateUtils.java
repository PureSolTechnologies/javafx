package com.puresoltechnologies.javafx.testing.utils;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

/**
 * This class contains all helper functionality to deal with coordinates.
 *
 * @author Rick-Rainer Ludwig
 */
public interface CoordinateUtils {

    /**
     * This method returns the screen coordinates of the given node as rectangle.
     *
     * @param node is the node whose coordinates are to be calculated.
     * @return A {@link Rectangle} is returned proving the screen coordinates.
     */
    static Rectangle getScreenCoordinates(Node node) {
	Bounds bounds = node.getBoundsInLocal();
	Bounds screenBounds = node.localToScreen(bounds);
	int x = (int) screenBounds.getMinX();
	int y = (int) screenBounds.getMinY();
	int width = (int) screenBounds.getWidth();
	int height = (int) screenBounds.getHeight();
	return new Rectangle(x, y, width, height);
    }

    /**
     * This method is used to calculate the screen center coordinates. It utilizes
     * {@link #getScreenCoordinates(Node)}.
     *
     * @param node is the node whose coordinates are to be calculated.
     * @return A {@link Point2D} is returned proving the screen center coordinates.
     */
    static Point2D getScreenCenterCoordinates(Node node) {
	Rectangle screenCoordinates = getScreenCoordinates(node);
	return new Point2D( //
		screenCoordinates.getX() + (screenCoordinates.getWidth() / 2.0), //
		screenCoordinates.getY() + (screenCoordinates.getHeight() / 2.0) //
	);
    }

}
