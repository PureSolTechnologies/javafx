package com.puresoltechnologies.javafx.workspaces;

import java.io.File;
import java.util.Optional;

import com.puresoltechnologies.javafx.utils.FXThreads;
import com.puresoltechnologies.javafx.workspaces.dialogs.WorkspaceSelectionDialog;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class WorkspaceSelectionApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
	stage.setTitle("Workspace Selection");

	FXThreads.runOnFXThread(() -> {
	    WorkspaceSelectionDialog dialog = new WorkspaceSelectionDialog();
	    Optional<File> result = dialog.showAndWait();
	    Platform.exit();
	    if (!result.isPresent()) {
		System.exit(0);
	    }
	});
    }

    public static void main(String[] args) {
	Application.launch(args);
    }
}
