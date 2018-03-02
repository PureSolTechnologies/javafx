package com.puresoltechnologies.javafx.preferences.dialogs;

public class PreferencesPageNode {

    private final String nodeName;
    private PreferencesPage preferencesPage;

    public PreferencesPageNode(String nodeName) {
	super();
	this.nodeName = nodeName;
    }

    public PreferencesPageNode(String nodeName, PreferencesPage preferencesPage) {
	super();
	this.nodeName = nodeName;
	this.preferencesPage = preferencesPage;
    }

    public PreferencesPage getPreferencesPage() {
	return preferencesPage;
    }

    public void setPreferencesPage(PreferencesPage preferencesPage) {
	this.preferencesPage = preferencesPage;
    }

    public String getNodeName() {
	return nodeName;
    }

}
