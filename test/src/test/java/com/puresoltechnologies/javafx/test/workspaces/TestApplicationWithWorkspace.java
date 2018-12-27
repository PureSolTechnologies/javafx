package com.puresoltechnologies.javafx.test.workspaces;

import com.puresoltechnologies.javafx.workspaces.Workspace;
import com.puresoltechnologies.javafx.workspaces.menu.ExitApplicationMenuItem;
import com.puresoltechnologies.javafx.workspaces.menu.RestartApplicationMenuItem;
import com.puresoltechnologies.javafx.workspaces.menu.SwitchWorkspaceMenu;
import com.puresoltechnologies.javafx.workspaces.menu.SwitchWorkspaceMenuItem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestApplicationWithWorkspace extends Application {

    @Override
    public void init() throws Exception {
	super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
	MenuBar menuBar = new MenuBar();
	Menu fileMenu = new Menu("File");
	SwitchWorkspaceMenu switchMenu = new SwitchWorkspaceMenu(stage);
	SwitchWorkspaceMenuItem switchMenuItem = new SwitchWorkspaceMenuItem(stage);
	RestartApplicationMenuItem restartMenuItem = new RestartApplicationMenuItem(stage);
	SeparatorMenuItem separator = new SeparatorMenuItem();
	ExitApplicationMenuItem quiteMenuItem = new ExitApplicationMenuItem(stage);
	fileMenu.getItems().addAll(switchMenu, switchMenuItem, restartMenuItem, separator, quiteMenuItem);
	menuBar.getMenus().add(fileMenu);

	BorderPane root = new BorderPane();
	root.setTop(menuBar);
	root.setCenter(new Label("Test"));

	Scene scene = new Scene(root, 640, 480);
	stage.setScene(scene);
	stage.setTitle("Test Application");
	stage.show();
    }

    @Override
    public void stop() throws Exception {
	super.stop();
    }

    public static void main(String[] args) throws InterruptedException {
	// Application.launch(TestApplicationWithWorkspace.class, args);
	Workspace.launchApplicationInWorkspace(TestApplicationWithWorkspace.class, args);
    }
}
