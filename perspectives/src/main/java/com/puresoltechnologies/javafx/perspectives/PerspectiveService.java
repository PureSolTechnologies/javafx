package com.puresoltechnologies.javafx.perspectives;

import com.puresoltechnologies.javafx.perspectives.parts.Part;

/**
 * This is the central service for perspectives in JavaFX.
 *
 * @author Rick-Rainer Ludwig
 */
public class PerspectiveService {

    private static PerspectiveService instance = null;

    public static void initialize() {
	if (instance != null) {
	    throw new IllegalStateException("PerspectiveService was initialized already.");
	}
	instance = new PerspectiveService();
    }

    public static void shutdown() {
	if (instance == null) {
	    throw new IllegalStateException("PerspectiveService is not initialized.");
	}
	instance.mainContainer.removeAllPerspectives();
	instance = null;
    }

    public static PerspectiveService getInstance() {
	return instance;
    }

    public static PerspectivePane getMainContainer() {
	return instance.mainContainer;
    }

    public static void openPart(Part part) {
	Perspective currentPerspective = instance.mainContainer.getCurrentPerspective();
	currentPerspective.openPart(part);
    }

    public static void openPerspective(Perspective perspective) {
	instance.mainContainer.addPerspective(perspective);
    }

    public static void resetPerspective() {
	Perspective currentPerspective = instance.mainContainer.getCurrentPerspective();
	currentPerspective.reset();
    }

    public static void closePerspective() {
	Perspective currentPerspective = instance.mainContainer.getCurrentPerspective();
	instance.mainContainer.removePerspective(currentPerspective);
    }

    public static void closeAllPerspectives() {
	instance.mainContainer.removeAllPerspectives();
    }

    private final PerspectivePane mainContainer;

    private PerspectiveService() {
	this.mainContainer = new PerspectivePane();
    }

}
