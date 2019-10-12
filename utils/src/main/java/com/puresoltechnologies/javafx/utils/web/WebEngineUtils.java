package com.puresoltechnologies.javafx.utils.web;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.html.HTMLAnchorElement;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.concurrent.Worker.State;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * This class contains utilities around {@link WebEngine}.
 *
 * @author Rick-RainerLudwig
 *
 */
public class WebEngineUtils {

    /**
     * This methods changes the WebEngine to open links in the external default
     * browser of the OS.
     *
     * @param engine      is the {@link WebEngine} to be changed.
     * @param application is the application which is using the WebEngine (better
     *                    {@link WebView}. It is used to get the needed
     *                    {@link HostServices}.
     */
    public static void configOpenLinksExternally(WebEngine engine, Application application) {
	engine.getLoadWorker().stateProperty().addListener((o, oldValue, newValue) -> {
	    if (newValue == State.SUCCEEDED) {
		Document document = engine.getDocument();
		NodeList nodeList = document.getElementsByTagName("a");
		for (int i = 0; i < nodeList.getLength(); i++) {
		    Node node = nodeList.item(i);
		    EventTarget eventTarget = (EventTarget) node;
		    eventTarget.addEventListener("click", evt -> {
			EventTarget target = evt.getCurrentTarget();
			HTMLAnchorElement anchorElement = (HTMLAnchorElement) target;
			String href = anchorElement.getHref();
			application.getHostServices().showDocument(href);
			evt.preventDefault();
		    }, false);
		}
	    }
	});

    }

    /**
     * Private default constructor to avoid instantiation.
     */
    private WebEngineUtils() {
	// intentionally left empty
    }
}
