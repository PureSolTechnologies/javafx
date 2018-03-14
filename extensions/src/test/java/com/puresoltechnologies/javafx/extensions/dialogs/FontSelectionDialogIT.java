package com.puresoltechnologies.javafx.extensions.dialogs;

import java.util.Optional;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import com.puresoltechnologies.javafx.extensions.fonts.FontDefinition;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FontSelectionDialogIT extends ApplicationTest {

    @Override
    public void start(Stage stage) {
	Scene scene = new Scene(new Label("FontSelectionDialogTest test running."), 800, 600);
	stage.setScene(scene);
	stage.show();
    }

    @Test
    public void testDialog() throws InterruptedException {
	Platform.runLater(() -> {
	    FontSelectionDialog dialog = new FontSelectionDialog();
	    Font font = Font.getDefault();
	    System.err.println(font.getFamily());
	    System.err.println(font.getName());
	    System.err.println(font.getStyle());
	    dialog.setFont(FontDefinition.of(font));
	    Optional<FontDefinition> selected = dialog.showAndWait();
	    font = selected.get().toFont();
	    System.err.println(font.getFamily());
	    System.err.println(font.getName());
	    System.err.println(font.getStyle());
	});
	Thread.sleep(250);
	clickOn("OK", MouseButton.PRIMARY);
	Thread.sleep(250);
    }

}
