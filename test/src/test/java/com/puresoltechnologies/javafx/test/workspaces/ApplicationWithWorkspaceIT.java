package com.puresoltechnologies.javafx.test.workspaces;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;

import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

@ExtendWith(ApplicationExtension.class)
public class ApplicationWithWorkspaceIT extends FxRobot {

    private Application application = null;

    @BeforeEach
    public void before() throws Exception {
	FxToolkit.registerPrimaryStage();
	application = FxToolkit.setupApplication(TestApplicationWithWorkspace.class);
    }

    @AfterEach
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
