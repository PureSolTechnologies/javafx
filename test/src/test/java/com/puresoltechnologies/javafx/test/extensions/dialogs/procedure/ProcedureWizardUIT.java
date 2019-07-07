package com.puresoltechnologies.javafx.test.extensions.dialogs.procedure;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.puresoltechnologies.javafx.extensions.dialogs.procedure.ProcedureWizard;
import com.puresoltechnologies.javafx.testing.OpenJFXTest;
import com.puresoltechnologies.javafx.testing.ReplayTimings;
import com.puresoltechnologies.javafx.testing.ReplayTimings.Speed;
import com.puresoltechnologies.javafx.testing.select.Selection;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ProcedureWizardUIT extends OpenJFXTest {

    @BeforeAll
    public static void initialize() {
	ReplayTimings.setSpeed(Speed.MEDIUM);
	ReplayTimings.setNodeRetrievalDelay(0);
    }

    @Override
    protected Stage start() {
	Button button = new Button("Stard Wizard...");
	button.setId("wizard.start");
	button.setOnAction(event -> {
	    ProcedureWizard<ProcedureData> dialog = new ProcedureWizard<>(ProcedureData::new);
	    dialog.setTitle("Test Procedure Wizard");
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
	// TODO Auto-generated method stub
    }

    @Test
    public void test() throws InterruptedException {
	findButtonById("wizard.start").click();

	Selection<Button> cancelButton = Awaitility.await() //
		.atMost(10, TimeUnit.SECONDS) //
		.pollDelay(100, TimeUnit.MILLISECONDS) //
		.until(() -> findButtonByText("Cancel"), selection -> selection.getNode() != null);

	cancelButton.click();
    }

}
