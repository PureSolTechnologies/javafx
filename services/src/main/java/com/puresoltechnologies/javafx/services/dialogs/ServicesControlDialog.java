package com.puresoltechnologies.javafx.services.dialogs;

import com.puresoltechnologies.javafx.services.ServiceInformation;
import com.puresoltechnologies.javafx.services.Services;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ServicesControlDialog extends Dialog<Void> {

    private final ObservableList<ServiceInformation> observableList;

    public ServicesControlDialog() {
	super();
	setTitle("Services Control");

	GridPane gridPane = new GridPane();

	TableView<ServiceInformation> serviceTable = new TableView<>();

	TableColumn<ServiceInformation, String> nameColumn = new TableColumn<>("Name");
	nameColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getName()));
	serviceTable.getColumns().add(nameColumn);

	TableColumn<ServiceInformation, String> statusColumn = new TableColumn<>("Status");
	statusColumn.setCellValueFactory(e -> {
	    boolean started = Services.isStarted(e.getValue().getClazz());
	    return new SimpleStringProperty(started ? "started" : "stopped");
	});
	serviceTable.getColumns().add(statusColumn);

	GridPane.setConstraints(serviceTable, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);

	gridPane.getChildren().addAll(serviceTable);

	DialogPane dialogPane = getDialogPane();
	dialogPane.getButtonTypes().add(ButtonType.CLOSE);
	dialogPane.setContent(gridPane);

	observableList = FXCollections.observableArrayList();
	Services.getServices().forEach(serivce -> observableList.add(serivce));
	serviceTable.setItems(observableList);
    }

}
