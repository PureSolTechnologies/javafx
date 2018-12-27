package com.puresoltechnologies.javafx.extensions.dialogs;

import java.io.IOException;

import com.puresoltechnologies.javafx.extensions.StatusBar;
import com.puresoltechnologies.javafx.extensions.fonts.FontDefinition;
import com.puresoltechnologies.javafx.extensions.fonts.FontFamilyListView;
import com.puresoltechnologies.javafx.extensions.fonts.FontPostureComboBox;
import com.puresoltechnologies.javafx.extensions.fonts.FontSizeComboBox;
import com.puresoltechnologies.javafx.extensions.fonts.FontWeightListView;
import com.puresoltechnologies.javafx.utils.FXNodeUtils;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class FontSelectionDialog extends Dialog<FontDefinition> {

    private static final String SAMPLE_TEXT;
    static {
	StringBuilder builder = new StringBuilder();
	for (char i = 32; i < 128; i++) {
	    if ((i % 32 == 31) && (i != 127)) {
		builder.append("\n");
	    }
	    builder.append(i);
	    if (i % 32 != 31) {
		builder.append(" ");
	    }
	}
	SAMPLE_TEXT = builder.toString();
    }

    private static final Image iconSmall;
    private static final Image iconBig;
    static {
	try {
	    iconSmall = ResourceUtils.getImage(StatusBar.class, "icons/FatCow_Icons16x16/font.png");
	    iconBig = ResourceUtils.getImage(StatusBar.class, "icons/FatCow_Icons32x32/font.png");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private final FontFamilyListView fontFamilyListView = new FontFamilyListView("SansSerif");
    private final FontSizeComboBox fontSizeComboBox = new FontSizeComboBox(10.0);
    private final FontWeightListView fontWeightListView = new FontWeightListView(FontWeight.NORMAL);
    private final FontPostureComboBox fontPostureComboBox = new FontPostureComboBox(FontPosture.REGULAR);
    private final ColorPicker fontColorPicker = new ColorPicker(Color.BLACK);
    private final TextArea sampleTextArea = new TextArea(SAMPLE_TEXT);

    private final GridPane gridPane = new GridPane();

    public FontSelectionDialog() {
	setTitle("Font Selection");
	setHeaderText("Select a font.");
	setGraphic(new ImageView(iconBig));
	Stage stage = (Stage) getDialogPane().getScene().getWindow();
	stage.getIcons().addAll(iconSmall, iconBig);
	setResizable(true);

	Label fontFamilyLabel = new Label("Font:");
	fontFamilyLabel.setLabelFor(fontFamilyListView);
	Label fontWeightLabel = new Label("Weight:");
	fontWeightLabel.setLabelFor(fontWeightListView);

	GridPane.setConstraints(fontFamilyLabel, 0, 0);
	GridPane.setConstraints(fontWeightLabel, 1, 0);

	Label fontSizeLabel = new Label("Size:");
	fontSizeLabel.setLabelFor(fontSizeComboBox);
	Label fontPostureLabel = new Label("Posture:");
	fontPostureLabel.setLabelFor(fontPostureComboBox);
	Label fontColorLabel = new Label("Color:");
	fontColorLabel.setLabelFor(fontColorPicker);

	GridPane.setConstraints(fontSizeLabel, 2, 0);
	GridPane.setConstraints(fontPostureLabel, 2, 2);
	GridPane.setConstraints(fontColorLabel, 2, 4);

	fontFamilyListView.selectionModelProperty().get().selectedItemProperty()
		.addListener(event -> updateSampleText());
	fontWeightListView.selectionModelProperty().get().selectedItemProperty()
		.addListener(event -> updateSampleText());
	fontSizeComboBox.selectionModelProperty().get().selectedItemProperty().addListener(event -> updateSampleText());
	fontSizeComboBox.setOnAction(event -> updateSampleText());
	fontPostureComboBox.selectionModelProperty().get().selectedItemProperty()
		.addListener(event -> updateSampleText());
	fontColorPicker.valueProperty().addListener(event -> updateSampleText());

	GridPane.setConstraints(fontFamilyListView, 0, 1, 1, 6);
	GridPane.setConstraints(fontWeightListView, 1, 1, 1, 6);

	GridPane.setConstraints(fontSizeComboBox, 2, 1);
	GridPane.setConstraints(fontPostureComboBox, 2, 3);
	GridPane.setConstraints(fontColorPicker, 2, 5);
	TextArea scaling = new TextArea();
	GridPane.setConstraints(scaling, 2, 6, 1, 1, HPos.CENTER, VPos.CENTER, Priority.NEVER, Priority.ALWAYS);

	Label sampleTextLabel = new Label("Sample Text:");
	sampleTextLabel.setLabelFor(sampleTextArea);
	sampleTextArea.setEditable(false);
	GridPane.setConstraints(sampleTextLabel, 0, 7, 3, 1);
	GridPane.setConstraints(sampleTextArea, 0, 8, 3, 1);

	gridPane.setHgap(5.0);
	gridPane.setVgap(5.0);
	gridPane.getChildren().addAll(fontFamilyLabel, fontSizeLabel, fontWeightLabel, fontPostureLabel,
		fontFamilyListView, fontSizeComboBox, fontWeightListView, fontPostureComboBox, fontColorLabel,
		fontColorPicker, sampleTextLabel, sampleTextArea);
	getDialogPane().setContent(gridPane);

	ButtonType buttonTypeOk = new ButtonType("OK", ButtonData.OK_DONE);
	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	ObservableList<ButtonType> buttonTypes = getDialogPane().getButtonTypes();
	buttonTypes.addAll(buttonTypeOk, buttonTypeCancel);

	setResultConverter(b -> {
	    if (b == buttonTypeOk) {
		return createFontDefinition();
	    }
	    return null;
	});
    }

    private FontDefinition createFontDefinition() {
	String family = fontFamilyListView.getSelectionModel().getSelectedItem();
	FontWeight weight = fontWeightListView.getSelectionModel().getSelectedItem();
	Object object = fontSizeComboBox.getValue();
	double size;
	if (object instanceof String) {
	    size = Double.parseDouble((String) object);
	} else {
	    size = (Double) object;
	}
	FontPosture posture = fontPostureComboBox.getSelectionModel().getSelectedItem();
	Color color = fontColorPicker.getValue();
	return new FontDefinition(family, weight, size, posture, color);
    }

    private Object updateSampleText() {
	FontDefinition fontDefinition = createFontDefinition();
	Color color = fontDefinition.getColor();
	sampleTextArea.setFont(fontDefinition.toFont());
	FXNodeUtils.setTextColor(sampleTextArea, color);
	return null;
    }

    public void setFont(FontDefinition fontDefinition) {
	fontFamilyListView.getSelectionModel().select(fontDefinition.getFamily());
	fontWeightListView.getSelectionModel().select(fontDefinition.getWeight());
	fontSizeComboBox.getSelectionModel().select(fontDefinition.getSize());
	fontPostureComboBox.getSelectionModel().select(fontDefinition.getPosture());
	fontColorPicker.setValue(fontDefinition.getColor());
    }
}
