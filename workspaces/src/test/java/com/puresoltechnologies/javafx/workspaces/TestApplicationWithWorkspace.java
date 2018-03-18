package com.puresoltechnologies.javafx.workspaces;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestApplicationWithWorkspace extends Application {

    @Override
    public void init() throws Exception {
	super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
	// super.start(stage);
	// stage.setTitle("Test Application");
	BorderPane root = new BorderPane();
	root.setCenter(new Label("Test"));
	Scene scene = new Scene(root, 640, 480);
	stage.setScene(scene);
	stage.show();
    }

    @Override
    public void stop() throws Exception {
	super.stop();
    }

    public static void main(String[] args) throws InterruptedException {
	// Application.launch(TestApplicationWithWorkspace.class, args);
	Workspace.launchApplicationInWorkspace(TestApplicationWithWorkspace.class, args);
    }
}
