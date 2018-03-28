package com.puresoltechnologies.javafx.perspectives.dialogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

import com.puresoltechnologies.javafx.perspectives.parts.Part;
import com.puresoltechnologies.javafx.perspectives.parts.PartOpenMode;
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

public class PartSelectionDialog extends Dialog<Part> {

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

    public PartSelectionDialog() {
	super();
	setTitle("Parts");
	setHeaderText("Choose a part to be opened.");
	setGraphic(new ImageView(iconBig));
	Stage stage = (Stage) getDialogPane().getScene().getWindow();
	stage.getIcons().addAll(iconSmall, iconBig);
	setResizable(true);

	ListView<Part> listView = new ListView<>();
	listView.setEditable(false);
	listView.setCellFactory(p -> {
	    ListCell<Part> cell = new ListCell<>() {
		@Override
		protected void updateItem(Part t, boolean bln) {
		    super.updateItem(t, bln);
		    if (t != null) {
			setText(t.getTitle());
			Optional<Image> image = t.getImage();
			if (image.isPresent()) {
			    setGraphic(new ImageView(image.get()));
			}
		    }
		}
	    };
	    cell.setOnMouseClicked(event -> {
		if (event.getClickCount() == 2 && (!cell.isEmpty())) {
		    Part part = cell.getItem();
		    setResult(part);
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

    private void fillListView(ListView<Part> listView) {
	FXThreads.runAsync(() -> {
	    ServiceLoader<Part> perspectives = ServiceLoader.load(Part.class);
	    List<Part> items = new ArrayList<>();
	    perspectives.stream() //
		    .filter(p -> p.get().getOpenMode() == PartOpenMode.AUTO_AND_MANUAL) //
		    .forEach(p -> {
			items.add(p.get());
		    });
	    Collections.sort(items, (l, r) -> l.getTitle().compareTo(r.getTitle()));
	    FXThreads.runOnFXThread(() -> listView.setItems(FXCollections.observableArrayList(items)));
	});
    }

}
