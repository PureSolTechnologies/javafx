package com.puresoltechnologies.javafx.extensions.status;

/**
 * This interface implements a status renderer. The renderer is responsible to
 * render the status images like LEDs, Flags, Arrows and the like...
 *
 * @author Rick-Rainer Ludwig
 *
 */
public interface StatusRenderer<T> {

    /**
     * This method performs the actual rendering.
     *
     * @param indicator is the target to render in. Get the Canvas with
     *                  {@link StatusIndicator#getGraphic()} and draw in it.
     * @param status    is the status to be drawn.
     */
    void render(StatusIndicator<T> indicator, T status);

}
