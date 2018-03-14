package com.puresoltechnologies.javafx.extensions.dialogs;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import com.puresoltechnologies.javafx.extensions.fonts.FontSelector;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class FontSelectorIT extends ApplicationTest {

    private FontSelector fontSelector = null;

    @Override
    public void start(Stage stage) {
	fontSelector = new FontSelector();
	Scene scene = new Scene(fontSelector);
	stage.setScene(scene);
	stage.show();
    }

    @Test
    public void testDialog() throws InterruptedException {

	Thread.sleep(250);
    }

}
