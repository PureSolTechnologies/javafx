package com.puresoltechnologies.javafx.perspectives.preferences;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.puresoltechnologies.javafx.extensions.ContentDisplayComboBox;
import com.puresoltechnologies.javafx.perspectives.PerspectiveProperties;
import com.puresoltechnologies.javafx.preferences.Preferences;
import com.puresoltechnologies.javafx.preferences.dialogs.PreferencesPage;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class PerspectivesPreferencesPage implements PreferencesPage {

    private ContentDisplayComboBox containerToolbarContentComboBox;
    private ContentDisplayComboBox partHeaderToolbarContentComboBox;

    @Override
    public String getName() {
	return "Settings for Perspectives";
    }

    @Override
    public Image getImage() {
	try {
	    return ResourceUtils.getImage(this, "/icons/FatCow_Icons16x16/switch_windows.png");
	} catch (IOException e) {
	    return null;
	}
    }

    @Override
    public List<String> getPath() {
	return Arrays.asList("User Interface", "Perspectives");
    }

    @Override
    public Pane getPane() {
	GridPane gridPane = new GridPane();
	gridPane.setHgap(10.0);
	gridPane.setVgap(10.0);
	Label containerToolbarContentLabel = new Label("Perspective toolbar content:");
	GridPane.setConstraints(containerToolbarContentLabel, 0, 0);
	containerToolbarContentComboBox = new ContentDisplayComboBox();
	GridPane.setConstraints(containerToolbarContentComboBox, 1, 0);

	Label partHeaderToolbarContentLabel = new Label("Part header toolbar content:");
	GridPane.setConstraints(partHeaderToolbarContentLabel, 0, 1);
	partHeaderToolbarContentComboBox = new ContentDisplayComboBox();
	GridPane.setConstraints(partHeaderToolbarContentComboBox, 1, 1);

	gridPane.getChildren().addAll(containerToolbarContentLabel, containerToolbarContentComboBox,
		partHeaderToolbarContentLabel, partHeaderToolbarContentComboBox);

	return gridPane;
    }

    @Override
    public void reset() {
	containerToolbarContentComboBox.setValue(PerspectiveProperties.perspectiveToolbarContentDefault);
	partHeaderToolbarContentComboBox.setValue(PerspectiveProperties.partHeaderToolbarContentDefault);
    }

    @Override
    public void load(Preferences preferences) {
	containerToolbarContentComboBox
		.setValue(preferences.getValue(PerspectiveProperties.perspectiveToolbarContentDisplay));
	partHeaderToolbarContentComboBox
		.setValue(preferences.getValue(PerspectiveProperties.partHeaderToolbarContentDisplay));
    }

    @Override
    public void save(Preferences preferences) {
	preferences.setValue(PerspectiveProperties.perspectiveToolbarContentDisplay,
		containerToolbarContentComboBox.getValue());
	preferences.setValue(PerspectiveProperties.partHeaderToolbarContentDisplay,
		partHeaderToolbarContentComboBox.getValue());
    }

}
