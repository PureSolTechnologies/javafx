package com.puresoltechnologies.javafx.test.extensions.dialogs.wizard;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.puresoltechnologies.javafx.extensions.dialogs.wizard.WizardDialog;
import com.puresoltechnologies.javafx.testing.OpenJFXTest;
import com.puresoltechnologies.javafx.testing.ReplayTimings;
import com.puresoltechnologies.javafx.testing.ReplayTimings.Speed;
import com.puresoltechnologies.javafx.testing.select.Selection;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WizardDialogUIT extends OpenJFXTest {

    @BeforeAll
    public static void initialize() {
	ReplayTimings.setSpeed(Speed.MEDIUM);
	ReplayTimings.setNodeRetrievalDelay(0);
    }

    @Override
    protected Stage start() {
	Button button = new Button("Start Wizard...");
	button.setId("wizard.start");
	button.setOnAction(event -> {
	    WizardDialog<TestWizardResult> dialog = new WizardDialog<>(TestWizardResult::new);
	    dialog.setTitle("Test Wizard");
	    dialog.addPage(new TestWizardPage1());
	    dialog.addPage(new TestWizardPage2());
	    dialog.showAndWait();
	    event.consume();
	});
	Scene scene = new Scene(button);
	Stage stage = new Stage();
	stage.setTitle("Wizard Test");
	stage.setScene(scene);
	return stage;
    }

    @Override
    protected void stop() {
	// Intentionally left blank
    }

    @Test
    public void test() throws InterruptedException {
	findButtonById("wizard.start").click();

	Selection<Parent> dialog = Awaitility.await() //
		.atMost(10, TimeUnit.SECONDS) //
		.pollDelay(100, TimeUnit.MILLISECONDS) //
		.until(() -> findDialogByTitle("Wizard Page 1"), selection -> selection.getNode() != null);
	System.err.println("-------------- CANCEL -------------");
	Thread.sleep(1000);
	Selection<Button> cancelButton = Awaitility.await() //
		.atMost(10, TimeUnit.SECONDS) //
		.pollDelay(100, TimeUnit.MILLISECONDS) //
		.until(() -> findButtonByText("Cancel"), selection -> selection.getNode() != null);

	cancelButton.click();
    }

}
