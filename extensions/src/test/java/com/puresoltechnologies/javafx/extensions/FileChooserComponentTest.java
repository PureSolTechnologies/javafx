package com.puresoltechnologies.javafx.extensions;

import org.junit.jupiter.api.Test;

import com.puresoltechnologies.javafx.testing.OpenJFXTest;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class FileChooserComponentTest extends OpenJFXTest {

    private final FileChooserComponent fileChooserComponent = new FileChooserComponent("File");

    @Override
    protected Stage start() {
	Stage stage = new Stage();
	Scene scene = new Scene(fileChooserComponent);
	stage.setScene(scene);
	return stage;
    }

    @Override
    protected void stop() {
	// TODO Auto-generated method stub
    }

    @Test
    public void test() throws InterruptedException {
	Thread.sleep(3000);
    }

}
