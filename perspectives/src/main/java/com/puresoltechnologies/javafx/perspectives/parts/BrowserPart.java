package com.puresoltechnologies.javafx.perspectives.parts;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.puresoltechnologies.javafx.perspectives.PartHeaderToolBar;
import com.puresoltechnologies.javafx.perspectives.Perspective;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class BrowserPart extends AbstractViewer {

    private static final Logger logger = LoggerFactory.getLogger(BrowserPart.class);

    private final GridPane gridPane = new GridPane();

    public BrowserPart() {
	super("Browser", PartOpenMode.AUTO_AND_MANUAL);
	try {
	    setImage(ResourceUtils.getImage(Perspective.class, "icons/FatCow_Icons16x16/web_layout.png"));
	} catch (IOException e) {
	    logger.warn("Icon was not loaded.", e);
	}
    }

    @Override
    public Optional<PartHeaderToolBar> getToolBar() {
	return Optional.empty();
    }

    @Override
    public void initialize() {
	TextField urlField = new TextField();
	Button button = new Button("GO");
	WebView webView = new WebView();

	button.setOnAction(event -> {
	    WebEngine webEngie = webView.getEngine();
	    webEngie.load(urlField.getText());
	    event.consume();
	});

	GridPane.setConstraints(urlField, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
	GridPane.setConstraints(button, 1, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER);
	GridPane.setConstraints(webView, 0, 1, 2, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
	gridPane.getChildren().addAll(urlField, button, webView);
    }

    @Override
    public void close() {
	// TODO Auto-generated method stub
    }

    @Override
    public Node getContent() {
	return gridPane;
    }

}
