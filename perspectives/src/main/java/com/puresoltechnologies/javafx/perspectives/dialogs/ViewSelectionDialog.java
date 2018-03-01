package com.puresoltechnologies.javafx.perspectives.dialogs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

import com.puresoltechnologies.javafx.perspectives.Part;
import com.puresoltechnologies.javafx.perspectives.ViewerPart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ViewSelectionDialog extends Dialog<Part> {

    public ViewSelectionDialog() {
	super();
	setTitle("Views");
	setHeaderText("Choose a part to be opened.");
	setResizable(true);

	ListView<Part> listView = new ListView<>();
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

    private void fillListView(ListView<Part> listView) {
	ServiceLoader<Part> perspectives = ServiceLoader.load(Part.class);
	List<Part> items = new ArrayList<>();
	perspectives.forEach(p -> {
	    if (ViewerPart.class.isAssignableFrom(p.getClass())) {
		items.add(p);
	    }
	});
	Collections.sort(items, (l, r) -> l.getName().compareTo(r.getName()));
	listView.setItems(FXCollections.observableArrayList(items));
	listView.setCellFactory(new Callback<>() {
	    @Override
	    public ListCell<Part> call(ListView<Part> p) {
		ListCell<Part> cell = new ListCell<>() {
		    @Override
		    protected void updateItem(Part t, boolean bln) {
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
