package com.puresoltechnologies.javafx.test.workspace;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.puresoltechnologies.javafx.testing.AbstractOpenJFXTest;
import com.puresoltechnologies.javafx.testing.ReplayTimings;
import com.puresoltechnologies.javafx.testing.ReplayTimings.Speed;
import com.puresoltechnologies.javafx.testing.select.Selection;
import com.puresoltechnologies.javafx.workspaces.Workspace;
import com.puresoltechnologies.javafx.workspaces.menu.ExitApplicationMenuItem;
import com.puresoltechnologies.javafx.workspaces.menu.RestartApplicationMenuItem;
import com.puresoltechnologies.javafx.workspaces.menu.SwitchWorkspaceMenu;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

@Tag("workspace")
public class WorkspaceTermIT extends AbstractOpenJFXTest {

    @BeforeAll
    public static void initialize() {
        ReplayTimings.setSpeed(Speed.MEDIUM);
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
    public void testLiveSwitchingOfTerms() throws InterruptedException {
        Selection<?> fileMenu = findMenuByText("_File");
        fileMenu.click();

        await().until(() -> findMenuByText("Switch _Workspace") != null);

        fileMenu.click();

        Workspace.setWorkspaceTerm("Project");

        fileMenu.click();

        await().until(() -> findMenuByText("Switch _Project") != null);

        await().until(() -> findMenuByText("E_xit"), node -> node != null).click();
    }

    private static Stream<Arguments> provideWorkspaceTerms() {
        return Stream.of( //
                Arguments.arguments("Workspace"), //
                Arguments.arguments("Project"), //
                Arguments.arguments("Directory") //
        );
    }

    @MethodSource("provideWorkspaceTerms")
    @ParameterizedTest(name = "{index}: test term {0}")
    public void testDifferentTerms(String term) {
        System.out.println("Term " + term);
        Workspace.setWorkspaceTerm(term);
        Selection<?> fileMenu = findMenuByText("_File");
        fileMenu.click();
        await().until(() -> findMenuByText("Switch _" + term), node -> node != null).click();
        await().until(() -> findMenuByText("Other _" + term + "..."), node -> node != null).click();

        Selection<Parent> dialog = await().until(() -> findDialogByTitle(term + " Selection"), dlg -> dlg != null);
        DialogPane dialogPane = (DialogPane) dialog.getNode();
        assertTrue(dialogPane.getHeaderText().contains(term.toLowerCase()));
        await().until(() -> dialog.findButtonByText("Cancel"), dlg -> dlg != null).click();

        fileMenu.click();
        await().until(() -> findMenuByText("E_xit"), node -> node != null).click();
    }

}
