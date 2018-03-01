package com.puresoltechnologies.javafx.perspectives.dialogs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

import com.puresoltechnologies.javafx.perspectives.Perspective;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class PerspectiveSelectionDialog extends Dialog<Perspective> {

    public PerspectiveSelectionDialog() {
	super();
	setTitle("Perspectives");
	setHeaderText("Choose a perspective to be opened.");
	setResizable(true);

	ListView<Perspective> listView = new ListView<>();
	listView.setEditable(false);
	fillListView(listView);

	getDialogPane().setContent(listView);

	ButtonType buttonTypeOk = new ButtonType("OK", ButtonData.OK_DONE);
	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	ObservableList<ButtonType> buttonTypes = getDialogPane().getButtonTypes();
	buttonTypes.addAll(buttonTypeOk, buttonTypeCancel);

	setResultConverter(b -> {
	    if (b == buttonTypeOk) {
		return listView.getSelectionModel().getSelectedItem();
	    }
	    return null;
	});

    }

    private void fillListView(ListView<Perspective> listView) {
	ServiceLoader<Perspective> perspectives = ServiceLoader.load(Perspective.class);
	List<Perspective> items = new ArrayList<>();
	perspectives.forEach(p -> items.add(p));
	Collections.sort(items, (l, r) -> l.getName().compareTo(r.getName()));
	listView.setItems(FXCollections.observableArrayList(items));
	listView.setCellFactory(new Callback<>() {
	    @Override
	    public ListCell<Perspective> call(ListView<Perspective> p) {
		ListCell<Perspective> cell = new ListCell<>() {
		    @Override
		    protected void updateItem(Perspective t, boolean bln) {
			super.updateItem(t, bln);
			if (t != null) {
			    setText(t.getName());
			}
		    }
		};
		return cell;
	    }

	});
    }

}
