package com.puresoltechnologies.javafx.perspectives.dialogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

import com.puresoltechnologies.javafx.perspectives.Perspective;
import com.puresoltechnologies.javafx.preferences.menu.PreferencesMenuItem;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PerspectiveSelectionDialog extends Dialog<Perspective> {

    private static final Image iconSmall;
    private static final Image iconBig;
    static {
	try {
	    iconSmall = ResourceUtils.getImage(PreferencesMenuItem.class,
		    "/icons/FatCow_Icons16x16/switch_windows.png");
	    iconBig = ResourceUtils.getImage(PreferencesMenuItem.class, "/icons/FatCow_Icons32x32/switch_windows.png");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public PerspectiveSelectionDialog() {
	super();
	setTitle("Perspectives");
	setHeaderText("Choose a perspective to be opened.");
	setGraphic(new ImageView(iconBig));
	Stage stage = (Stage) getDialogPane().getScene().getWindow();
	stage.getIcons().addAll(iconSmall, iconBig);
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
	listView.setCellFactory(p -> {
	    ListCell<Perspective> cell = new ListCell<Perspective>() {
		@Override
		protected void updateItem(Perspective t, boolean bln) {
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
		    Perspective perspesctive = cell.getItem();
		    setResult(perspesctive);
		    close();
		}
	    });
	    return cell;
	});
    }

}
