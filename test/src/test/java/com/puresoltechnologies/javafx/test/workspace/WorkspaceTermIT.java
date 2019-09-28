package com.puresoltechnologies.javafx.test.workspace;

import static org.awaitility.Awaitility.await;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.puresoltechnologies.javafx.testing.OpenJFXTest;
import com.puresoltechnologies.javafx.testing.ReplayTimings;
import com.puresoltechnologies.javafx.testing.ReplayTimings.Speed;
import com.puresoltechnologies.javafx.testing.select.Selection;
import com.puresoltechnologies.javafx.workspaces.Workspace;
import com.puresoltechnologies.javafx.workspaces.menu.ExitApplicationMenuItem;
import com.puresoltechnologies.javafx.workspaces.menu.RestartApplicationMenuItem;
import com.puresoltechnologies.javafx.workspaces.menu.SwitchWorkspaceMenu;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class WorkspaceTermIT extends OpenJFXTest {

    @BeforeAll
    public static void initialize() {
	ReplayTimings.setSpeed(Speed.SLOW);
    }

    @Override
    protected Stage start() {
	BorderPane pane = new BorderPane();
	Scene scene = new Scene(pane);
	Stage stage = new Stage();
	stage.setScene(scene);

	MenuBar menuBar = new MenuBar();

	Menu fileMenu = new Menu("_File");
	MenuItem exitItem = new ExitApplicationMenuItem(stage);
	MenuItem restartItem = new RestartApplicationMenuItem(stage);
	SwitchWorkspaceMenu switchWorkspaceMenu = new SwitchWorkspaceMenu(stage);

	fileMenu.getItems().addAll( //
		switchWorkspaceMenu, //
		restartItem, //
		exitItem //
	);

	menuBar.getMenus().addAll(fileMenu);
	pane.setTop(menuBar);

	return stage;
    }

    @Override
    protected void stop() {
	// TODO Auto-generated method stub

    }

    @Test
    public void testSwitchTerm() throws InterruptedException {
	Selection<?> fileMenu = findMenuByText("_File");
	fileMenu.click();

	await().until(() -> findMenuByText("Switch _Workspace") != null);

	fileMenu.click();

	Workspace.setWorkspaceTerm("Project");

	fileMenu.click();

	await().until(() -> findMenuByText("Switch _Project") != null);

	await().until(() -> findMenuByText("E_xit"), node -> node != null).click();
    }
}
