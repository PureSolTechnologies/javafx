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

    public static void showPart(Part part) {
	Perspective currentPerspective = instance.container.getCurrentPerspective();
	currentPerspective.showPart(part);
    }

    private final PerspectiveContainer container;

    public PerspectiveService() {
	this.container = new PerspectiveContainer();
    }

}
