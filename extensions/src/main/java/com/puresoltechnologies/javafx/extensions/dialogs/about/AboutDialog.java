package com.puresoltechnologies.javafx.extensions.dialogs.about;

import java.io.IOException;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.puresoltechnologies.javafx.extensions.StatusBar;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * This class provides a standardized way to show an About Dialog. It is also
 * using Java's {@link ServiceLoader} to load additional
 * {@link AboutDialogContribution}s which are shown in additional tabs.
 *
 * @author Rick-Rainer Ludwig
 */
public class AboutDialog extends Dialog<Void> {

    private static final Logger logger = LoggerFactory.getLogger(AboutDialog.class);

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

    /**
     * Default constructor.
     */
    public AboutDialog() {
	setTitle("About");
	Stage stage = (Stage) getDialogPane().getScene().getWindow();
	stage.getIcons().addAll(iconSmall, iconBig);
	setResizable(true);

	TabPane tabPane = new TabPane();
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

	addFrameworkTab(tabPane);

	ButtonType buttonTypeClose = new ButtonType("Close", ButtonData.CANCEL_CLOSE);
	ObservableList<ButtonType> buttonTypes = getDialogPane().getButtonTypes();
	buttonTypes.addAll(buttonTypeClose);

    }

    /**
     * This method adds an additional tab for the framework information.
     *
     * @param tabPane
     */
    private void addFrameworkTab(TabPane tabPane) {
	String path = "icons/FatCow_Icons16x16/information.png";
	Image i = null;
	try {
	    i = ResourceUtils.getImage(StatusBar.class, path);
	} catch (IOException e) {
	    logger.warn("Could not read about icon '" + path + "'.", e);
	}

	Tab javaFX = new Tab();
	javaFX.setGraphic(new ImageView(i));
	javaFX.setClosable(false);
	WebView webView = new WebView();

	WebEngine engine = webView.getEngine();
	engine.load(AboutDialog.class.getResource("framework.html").toString());
//	TextArea textArea = new TextArea();
//	textArea.setText("PureSol Technologies' JavaFX\n" //
//		+ "(c) 2018 PureSol Technologies (http://puresol-technologies.com)\n\n" //
//		+ "License: Apache License, Version 2.0\n\n" //
//		+ "This software incorporates FatCow \"Farm-Fresh Web Icons\" (http://www.fatcow.com/free-icons).");
	javaFX.setContent(webView);

	tabPane.getTabs().add(javaFX);
    }

}
