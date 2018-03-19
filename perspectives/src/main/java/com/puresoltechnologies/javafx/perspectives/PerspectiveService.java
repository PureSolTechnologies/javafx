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
	    throw new IllegalStateException("CryptoCurrenciesClient was initialized already.");
	}
	instance = new PerspectiveService();
    }

    public static void shutdown() {
	instance.container.removeAllPerspectives();
	if (instance == null) {
	    throw new IllegalStateException("CryptoCurrenciesClient is not initialized.");
	}
	instance = null;
    }

    public static PerspectiveService getInstance() {
	return instance;
    }

    public static PerspectiveContainer getContainer() {
	return instance.container;
    }

    public static void openPart(Part part) {
	Perspective currentPerspective = instance.container.getCurrentPerspective();
	currentPerspective.openPart(part);
    }

    public static void openPerspective(Perspective perspective) {
	instance.container.addPerspective(perspective);
    }

    public static void resetPerspective() {
	Perspective currentPerspective = instance.container.getCurrentPerspective();
	currentPerspective.reset();
    }

    public static void closePerspective() {
	Perspective currentPerspective = instance.container.getCurrentPerspective();
	instance.container.removePerspective(currentPerspective);
    }

    public static void closeAllPerspectives() {
	instance.container.removeAllPerspectives();
    }

    private final PerspectiveContainer container;

    public PerspectiveService() {
	this.container = new PerspectiveContainer();
    }

}
