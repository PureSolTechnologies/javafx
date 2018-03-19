package com.puresoltechnologies.javafx.perspectives.dialogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

import com.puresoltechnologies.javafx.perspectives.parts.ViewerPart;
import com.puresoltechnologies.javafx.preferences.menu.PreferencesMenuItem;
import com.puresoltechnologies.javafx.utils.FXThreads;
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

public class ViewSelectionDialog extends Dialog<ViewerPart> {

    private static final Image iconSmall;
    private static final Image iconBig;
    static {
	try {
	    iconSmall = ResourceUtils.getImage(PreferencesMenuItem.class, "/icons/FatCow_Icons16x16/watch_window.png");
	    iconBig = ResourceUtils.getImage(PreferencesMenuItem.class, "/icons/FatCow_Icons32x32/watch_window.png");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public ViewSelectionDialog() {
	super();
	setTitle("Views");
	setHeaderText("Choose a view to be opened.");
	setGraphic(new ImageView(iconBig));
	Stage stage = (Stage) getDialogPane().getScene().getWindow();
	stage.getIcons().addAll(iconSmall, iconBig);
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
			Optional<Image> image = t.getImage();
			if (image.isPresent()) {
			    setGraphic(new ImageView(image.get()));
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
