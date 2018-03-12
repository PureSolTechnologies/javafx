package com.puresoltechnologies.javafx.perspectives.dialogs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

import com.puresoltechnologies.javafx.perspectives.parts.ViewerPart;
import com.puresoltechnologies.javafx.utils.FXThreads;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

public class ViewSelectionDialog extends Dialog<ViewerPart> {

    public ViewSelectionDialog() {
	super();
	setTitle("Views");
	setHeaderText("Choose a part to be opened.");
	setResizable(true);

	ListView<ViewerPart> listView = new ListView<>();
	listView.setEditable(false);
	listView.setCellFactory(p -> {
	    ListCell<ViewerPart> cell = new ListCell<>() {
		@Override
		protected void updateItem(ViewerPart t, boolean bln) {
		    super.updateItem(t, bln);
		    if (t != null) {
			setText(t.getName());
			if (t.getImage() != null) {
			    setGraphic(new ImageView(t.getImage()));
			}
		    }
		}
	    };
	    cell.setOnMouseClicked(event -> {
		if (event.getClickCount() == 2 && (!cell.isEmpty())) {
		    ViewerPart viewer = cell.getItem();
		    setResult(viewer);
		    close();
		}
	    });
	    return cell;
	});
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

    private void fillListView(ListView<ViewerPart> listView) {
	FXThreads.runAsync(() -> {
	    ServiceLoader<ViewerPart> perspectives = ServiceLoader.load(ViewerPart.class);
	    List<ViewerPart> items = new ArrayList<>();
	    perspectives.forEach(p -> {
		items.add(p);
	    });
	    Collections.sort(items, (l, r) -> l.getName().compareTo(r.getName()));
	    FXThreads.runOnFXThread(() -> listView.setItems(FXCollections.observableArrayList(items)));
	});
    }

}
