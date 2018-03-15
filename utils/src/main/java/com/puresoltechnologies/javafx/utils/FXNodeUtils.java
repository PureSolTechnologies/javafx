package com.puresoltechnologies.javafx.utils;

import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * A utility class for JavaFX {@link Node} objects and it siblings.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public class FXNodeUtils {

    public static void setTextColor(Node node, Color color) {
	node.setStyle("-fx-text-fill: " + color.toString().replace("0x", "#") + ";");
    }

    /**
     * Private constructor to avoid instantiation.
     */
    private FXNodeUtils() {
    }

}
