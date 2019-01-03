package com.puresoltechnologies.javafx.showroom;

import java.io.IOException;

import com.puresoltechnologies.javafx.extensions.StatusBar;
import com.puresoltechnologies.javafx.extensions.menu.AboutMenuItem;
import com.puresoltechnologies.javafx.extensions.splash.SplashScreen;
import com.puresoltechnologies.javafx.perspectives.PerspectivePane;
import com.puresoltechnologies.javafx.perspectives.PerspectiveService;
import com.puresoltechnologies.javafx.perspectives.menu.PerspectiveMenu;
import com.puresoltechnologies.javafx.perspectives.menu.ShowPartMenuItem;
import com.puresoltechnologies.javafx.preferences.Preferences;
import com.puresoltechnologies.javafx.preferences.menu.PreferencesMenuItem;
import com.puresoltechnologies.javafx.reactive.ReactiveFX;
import com.puresoltechnologies.javafx.showroom.perspectives.StartPerspective;
import com.puresoltechnologies.javafx.tasks.TasksStatusBar;
import com.puresoltechnologies.javafx.utils.FXThreads;
import com.puresoltechnologies.javafx.utils.ResourceUtils;
import com.puresoltechnologies.javafx.workspaces.Workspace;
import com.puresoltechnologies.javafx.workspaces.menu.ExitApplicationMenuItem;
import com.puresoltechnologies.javafx.workspaces.menu.RestartApplicationMenuItem;
import com.puresoltechnologies.javafx.workspaces.menu.SwitchWorkspaceMenu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class ShowRoom extends Application {

    private PerspectivePane perspectiveContainer;
    private SplashScreen splashScreen;

    @Override
    public void start(Stage stage) throws Exception {
	FXThreads.initialize();
	Image splashImage = ResourceUtils.getImage(ShowRoom.class, "splash/splash.png");
	splashScreen = new SplashScreen(stage, splashImage, applicationStage -> {
	    try {
		applicationStage.setTitle("Tool Shed");
		applicationStage.setResizable(true);
		applicationStage.centerOnScreen();

		Image chartUpColorSmall = ResourceUtils.getImage(this, "icons/FatCow_Icons16x16/setup_slide_show.png");
		Image chartUpColorBig = ResourceUtils.getImage(this, "icons/FatCow_Icons32x32/setup_slide_show.png");
		applicationStage.getIcons().addAll(chartUpColorSmall, chartUpColorBig);

		perspectiveContainer = PerspectiveService.getMainContainer();
		BorderPane root = new BorderPane();
		addMenu(applicationStage, root);
		root.setCenter(perspectiveContainer);
		StatusBar statusBar = new StatusBar();
		HBox stretch = new HBox();
		HBox.setHgrow(stretch, Priority.ALWAYS);
		statusBar.getChildren().addAll(stretch, new TasksStatusBar());
		root.setBottom(statusBar);

		PerspectiveService.openPerspective(new StartPerspective());

		Scene scene = new Scene(root, 800, 600);
		applicationStage.setScene(scene);
		applicationStage.show();
	    } catch (IOException e) {
		throw new RuntimeException(e);
	    }
	});
	splashScreen.addTask("Show startup message", () -> System.out.println("Starting...\n" //
		+ "      _                  _______  __  ____  _                     ____                       \n" //
		+ "     | | __ ___   ____ _|  ___\\ \\/ / / ___|| |__   _____      __ |  _ \\ ___   ___  _ __ ___  \n" //
		+ "  _  | |/ _` \\ \\ / / _` | |_   \\  /  \\___ \\| '_ \\ / _ \\ \\ /\\ / / | |_) / _ \\ / _ \\| '_ ` _ \\ \n" //
		+ " | |_| | (_| |\\ V / (_| |  _|  /  \\   ___) | | | | (_) \\ V  V /  |  _ < (_) | (_) | | | | | |\n" //
		+ "  \\___/ \\__,_| \\_/ \\__,_|_|   /_/\\_\\ |____/|_| |_|\\___/ \\_/\\_/   |_| \\_\\___/ \\___/|_| |_| |_|\n" //
		+ "\n" //
		+ "(c) PureSol Technologies\n" //
		+ "\n"));
	splashScreen.addTask("Initialize preferences", () -> {
	    Preferences.initialize();
	    return null;
	});
	splashScreen.addTask("Initialize perspectives", () -> PerspectiveService.initialize());
	splashScreen.addTask("Initialize reactive Java", () -> ReactiveFX.initialize());

	splashScreen.startApplication();

    }

    private void addMenu(Stage stage, BorderPane root) {
	// File Menu
	SwitchWorkspaceMenu switchWorkspaceMenu = new SwitchWorkspaceMenu(stage);
	RestartApplicationMenuItem restartApplicationMenuItem = new RestartApplicationMenuItem(stage);
	ExitApplicationMenuItem exitApplicationMenuItem = new ExitApplicationMenuItem(stage);
	Menu fileMenu = new Menu("_File");
	fileMenu.getItems().addAll(switchWorkspaceMenu, restartApplicationMenuItem, exitApplicationMenuItem);
	// Window Menu
	ShowPartMenuItem showViewItem = new ShowPartMenuItem();
	PreferencesMenuItem preferencesItem = new PreferencesMenuItem();
	Menu windowMenu = new Menu("_Window");
	windowMenu.getItems().addAll(showViewItem, new PerspectiveMenu(), new SeparatorMenuItem(), preferencesItem);
	// Help Menu
	AboutMenuItem aboutItem = new AboutMenuItem();
	Menu helpMenu = new Menu("_Help");
	helpMenu.getItems().addAll(aboutItem);
	// Menu Bar
	MenuBar menuBar = new MenuBar();
	menuBar.getMenus().addAll(fileMenu, windowMenu, helpMenu);
	root.setTop(menuBar);

    }

    @Override
    public void stop() {
	ReactiveFX.shutdown();
	PerspectiveService.shutdown();
	Preferences.shutdown();
	try {
	    FXThreads.shutdown();
	} catch (InterruptedException e) {
	    System.err.println("FXThreads were not cleanly shutdown.");
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) throws InterruptedException {
	Workspace.launchApplicationInWorkspace(ShowRoom.class, args);
    }
}
