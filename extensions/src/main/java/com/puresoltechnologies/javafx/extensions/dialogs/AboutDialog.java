package com.puresoltechnologies.javafx.extensions.dialogs;

import java.io.IOException;
import java.util.ServiceLoader;

import com.puresoltechnologies.javafx.extensions.StatusBar;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AboutDialog extends Dialog<Void> {

    private static final Image iconSmall;
    private static final Image iconBig;
    static {
	try {
	    iconSmall = ResourceUtils.getImage(StatusBar.class, "icons/FatCow_Icons16x16/information.png");
	    iconBig = ResourceUtils.getImage(StatusBar.class, "icons/FatCow_Icons32x32/information.png");
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
	tabPane.setSide(Side.TOP);
	getDialogPane().setContent(tabPane);

	ServiceLoader<AboutDialogContribution> loader = ServiceLoader.load(AboutDialogContribution.class);
	loader.forEach(contributor -> {
	    Tab tab = new Tab(contributor.getName());
	    if (contributor.getImage().isPresent()) {
		tab.setGraphic(new ImageView(contributor.getImage().get()));
	    }
	    tab.setContent(contributor.getContent());
	    tabPane.getTabs().add(tab);
	});

	Tab javaFX = new Tab("PST JavaFX");
	javaFX.setClosable(false);
	TextArea textArea = new TextArea();
	textArea.setText("PureSol Technolofies' JavaFX\n" //
		+ "(c) 2018 PureSol Technologies (http://puresol-technologies.com)\n\n" //
		+ "License: Apache License, Version 2.0\n\n" //
		+ "This software incorporates FatCow \"Farm-Fresh Web Icons\" (http://www.fatcow.com/free-icons).");
	javaFX.setContent(textArea);

	tabPane.getTabs().add(javaFX);

	ButtonType buttonTypeOk = new ButtonType("OK", ButtonData.OK_DONE);
	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	ObservableList<ButtonType> buttonTypes = getDialogPane().getButtonTypes();
	buttonTypes.addAll(buttonTypeOk, buttonTypeCancel);

    }

}
