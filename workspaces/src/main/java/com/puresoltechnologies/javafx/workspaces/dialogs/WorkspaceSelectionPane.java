package com.puresoltechnologies.javafx.workspaces.dialogs;

import java.io.File;

import com.puresoltechnologies.javafx.workspaces.Workspace;
import com.puresoltechnologies.javafx.workspaces.WorkspaceSettings;

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

    private final ComboBox<File> directoryComboBox;
    private final Button directoryChooseButton;
    private final CheckBox defineAsDefaultCheckBox;
    private final WorkspaceSettings workspaceSettings;

    public WorkspaceSelectionPane(WorkspaceSettings workspaceSettings) {
	this.workspaceSettings = workspaceSettings;
	setHgap(10.0);
	setVgap(10.0);

	Label directoryLabel = new Label(Workspace.getWorkspaceTerm() + ":");
	setConstraints(directoryLabel, 0, 0);
	getChildren().add(directoryLabel);

	directoryComboBox = new ComboBox<>();
	directoryComboBox.setItems(workspaceSettings.getFormerDirectories());
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
