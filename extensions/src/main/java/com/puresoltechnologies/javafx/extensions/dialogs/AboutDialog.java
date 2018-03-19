package com.puresoltechnologies.javafx.extensions.dialogs;

import java.io.IOException;

import com.puresoltechnologies.javafx.extensions.menu.AboutMenuItem;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AboutDialog extends Dialog<Void> {

    private static final Image iconSmall;
    private static final Image iconBig;
    static {
	try {
	    iconSmall = ResourceUtils.getImage(AboutMenuItem.class, "/icons/FatCow_Icons16x16/information.png");
	    iconBig = ResourceUtils.getImage(AboutMenuItem.class, "/icons/FatCow_Icons32x32/information.png");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private TabPane tabPane;

    public AboutDialog() {
	setTitle("About");
	Stage stage = (Stage) getDialogPane().getScene().getWindow();
	stage.getIcons().addAll(iconSmall, iconBig);
	setResizable(true);

	tabPane = new TabPane();
	tabPane.setSide(Side.BOTTOM);
	getDialogPane().setContent(tabPane);

	Tab javaFX = new Tab("PureSol Technologies' JavaFX");
	javaFX.setClosable(false);
	TextArea textArea = new TextArea();
	textArea.setText("PureSol Technolofies' JavaFX\n" //
		+ "(c) 2018 PureSol Technologies (http://puresol-technologies.com)\n\n" //
		+ "License: Apache License, Version 2.0");
	javaFX.setContent(textArea);

	Tab thirdParty = new Tab("3rd Party Contributions");
	thirdParty.setClosable(false);
	TextArea textArea2 = new TextArea();
	thirdParty.setContent(textArea2);

	Tab licenseTexts = new Tab("Licenses");
	licenseTexts.setClosable(false);
	TabPane licensesPane = new TabPane();
	licensesPane.setSide(Side.LEFT);
	licensesPane.getTabs().addAll(new Tab("Apache License\nVersion 2.0"), new Tab("BSD License\n Version 3.0"));
	licenseTexts.setContent(licensesPane);

	tabPane.getTabs().addAll(javaFX, thirdParty, licenseTexts);

	ButtonType buttonTypeOk = new ButtonType("OK", ButtonData.OK_DONE);
	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	ObservableList<ButtonType> buttonTypes = getDialogPane().getButtonTypes();
	buttonTypes.addAll(buttonTypeOk, buttonTypeCancel);

    }

}
