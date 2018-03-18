package com.puresoltechnologies.javafx.workspaces;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public class ApplicationWithWorkspaceIT extends FxRobot {

    private Application application = null;

    @Before
    public void before() throws Exception {
	FxToolkit.registerPrimaryStage();
	application = FxToolkit.setupApplication(TestApplicationWithWorkspace.class);
    }

    @After
    public void after() throws Exception {
	release(new KeyCode[0]);
	release(new MouseButton[0]);
	FxToolkit.cleanupApplication(application);
    }

    @Test
    public void testPerspectiveDialog() throws InterruptedException {
	Thread.sleep(1000);
    }

}
