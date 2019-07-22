package com.puresoltechnologies.javafx.extensions.status;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BooleanLEDRenderer implements StatusRenderer<Boolean> {

    @Override
    public void render(StatusIndicator<Boolean> indicator, Boolean status) {
	Canvas canvas = (Canvas) indicator.getGraphic();

	double width = canvas.getWidth();
	double height = canvas.getHeight();

	GraphicsContext gc = canvas.getGraphicsContext2D();
	gc.clearRect(0.0, 0.0, width, height);

	double fontSize = indicator.getFont().getSize() * 1.5;

	double size = Math.min(width, height);
	size = Math.min(size, fontSize);
	double posX = (width - size) / 2.0;
	double posY = (height - size) / 2.0;
	if ((status != null) && status) {
	    gc.setFill(Color.GREEN);
	} else {
	    gc.setFill(Color.RED);
	}
	gc.fillOval(posX, posY, size, size);

	gc.setStroke(Color.DARKGRAY);
	gc.strokeOval(posX, posY, size, size);
    }

}
