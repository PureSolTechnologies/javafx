package com.puresoltechnologies.javafx.extensions.dialogs;

import java.util.Optional;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import com.puresoltechnologies.javafx.extensions.fonts.FontDefinition;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FontSelectionDialogIT extends ApplicationTest {

    @Override
    public void start(Stage stage) {
	Scene scene = new Scene(new Label("FontSelectionDialogIT test running."), 800, 600);
	stage.setScene(scene);
	stage.show();
    }

    @Test
    public void testDialog() throws InterruptedException {
	ObjectProperty<FontSelectionDialog> dialogProperty = new SimpleObjectProperty<>();
	Platform.runLater(() -> {
	    FontSelectionDialog dialog = new FontSelectionDialog();
	    dialogProperty.set(dialog);
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
	int count = 0;
	while ((dialogProperty.get() == null) || ((!dialogProperty.get().isShowing()) && (count < 10))) {
	    count++;
	    Thread.sleep(500);
	}

	Thread.sleep(1000);
	clickOn("OK", MouseButton.PRIMARY);
	Thread.sleep(250);
    }

}
