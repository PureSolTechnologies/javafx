package com.puresoltechnologies.javafx.workspaces.dialogs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.puresoltechnologies.javafx.utils.Settings;
import com.puresoltechnologies.javafx.workspaces.WorkspaceSettings;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;

public class WorkspaceSelectionPane extends GridPane {

    public static final String CONTENT_STRING = "Select a directory as workspace.";

    private static final String BASE_PROPERTY_NAME = "com.puresoltechnologies.javafx.workspaces.selections";

    private static final String LAST_DIRECTORY_PROPERTY = BASE_PROPERTY_NAME + ".last";
    private static final String DEFAULT_SET_PROPERTY = BASE_PROPERTY_NAME + ".use_default";
    private static final String FORMER_SELECTIONS_PROPERTY_BASE = BASE_PROPERTY_NAME + ".former";

    private static final String WORKSPACE_SELECTION_PROPERTIES_FILENAME = "workspace-selection.properties";

    private final List<File> formerDirectories = new ArrayList<>();
    private final ComboBox<File> directoryComboBox;
    private final Button directoryChooseButton;
    private final CheckBox defineAsDefaultCheckBox;
    private final WorkspaceSettings workspaceSettings;

    public WorkspaceSelectionPane(WorkspaceSettings workspaceSettings) {
	this.workspaceSettings = workspaceSettings;
	setHgap(10.0);
	setVgap(10.0);

	Label directoryLabel = new Label("Workspace:");
	setConstraints(directoryLabel, 0, 0);
	getChildren().add(directoryLabel);

	directoryComboBox = new ComboBox<>();
	directoryComboBox.setItems(FXCollections.observableList(workspaceSettings.getFormerDirectories()));
	directoryComboBox.setValue(workspaceSettings.getDirectory());
	directoryComboBox.setEditable(true);
	directoryComboBox.valueProperty().addListener(o -> {
	    Object object = directoryComboBox.getValue();
	    if (object instanceof File) {
		workspaceSettings.setDirectory((File) object);
	    } else {
		workspaceSettings.setDirectory(new File(object.toString()));
	    }
	});
	setConstraints(directoryComboBox, 1, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
	getChildren().add(directoryComboBox);

	directoryChooseButton = new Button("Browse...");
	directoryChooseButton.setOnAction(event -> selectDirectory(event));
	setConstraints(directoryChooseButton, 2, 0);
	getChildren().add(directoryChooseButton);

	defineAsDefaultCheckBox = new CheckBox("Set as default");
	defineAsDefaultCheckBox.setSelected(workspaceSettings.isDefault());
	defineAsDefaultCheckBox
		.setOnAction(event -> workspaceSettings.setDefault(defineAsDefaultCheckBox.isSelected()));
	setConstraints(defineAsDefaultCheckBox, 0, 1, 3, 1);
	getChildren().add(defineAsDefaultCheckBox);
    }

    private File getCurrentDirectory() {
	Object object = directoryComboBox.getValue();
	File currentDirectory;
	if (object instanceof String) {
	    currentDirectory = new File((String) object);
	} else {
	    currentDirectory = directoryComboBox.getValue();
	}
	return currentDirectory;
    }

    private File getPropertiesFile() throws IOException {
	File directory = Settings.getDirectory();
	return new File(directory, WORKSPACE_SELECTION_PROPERTIES_FILENAME);
    }

    private void selectDirectory(ActionEvent event) {
	DirectoryChooser chooser = new DirectoryChooser();
	File directory = chooser.showDialog(null);
	if (directory != null) {
	    directoryComboBox.setValue(directory);
	    workspaceSettings.setDirectory(directory);
	}
	event.consume();
    }

}
