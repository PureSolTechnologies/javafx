module com.puresoltechnologies.javafx.rcp {

    requires transitive com.puresoltechnologies.javafx.extensions;
    requires transitive com.puresoltechnologies.javafx.i18n;
    requires transitive com.puresoltechnologies.javafx.perspectives;
    requires transitive com.puresoltechnologies.javafx.preferences;
    requires transitive com.puresoltechnologies.javafx.reactive;
    requires transitive com.puresoltechnologies.javafx.services;
    requires transitive com.puresoltechnologies.javafx.tasks;
    requires transitive com.puresoltechnologies.javafx.utils;
    requires transitive com.puresoltechnologies.javafx.workspaces;
    requires com.puresoltechnologies.graphs.trees;

    requires javafx.controls;

    provides com.puresoltechnologies.javafx.preferences.dialogs.PreferencesPage
	    with com.puresoltechnologies.javafx.rcp.preferences.I18nPreferencesPage;

    provides com.puresoltechnologies.javafx.perspectives.Perspective
	    with com.puresoltechnologies.javafx.rcp.perspectives.LinguistPerspective;
    provides com.puresoltechnologies.javafx.perspectives.parts.Part
	    with com.puresoltechnologies.javafx.rcp.parts.LinguistPart;

}
