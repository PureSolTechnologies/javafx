package com.puresoltechnologies.javafx.test.extensions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import com.puresoltechnologies.javafx.extensions.fonts.FontSelector;

import javafx.scene.Scene;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
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
