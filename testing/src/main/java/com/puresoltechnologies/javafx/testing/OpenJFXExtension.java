package com.puresoltechnologies.javafx.testing;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OpenJFXExtension implements BeforeAllCallback, AfterAllCallback {

    private final Scene scene;
    private Stage stage;

    public OpenJFXExtension(Scene scene) {
	this.scene = scene;
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
	System.out.println("Showing stage...");
	Platform.runLater(() -> {
	    stage = new Stage();
	    stage.show();
	});
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
	System.out.println("Hiding stage...");
	Platform.runLater(() -> stage.hide());
    }

}
