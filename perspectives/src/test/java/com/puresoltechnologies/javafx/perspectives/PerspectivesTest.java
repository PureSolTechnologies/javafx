package com.puresoltechnologies.javafx.perspectives;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import com.puresoltechnologies.javafx.perspectives.parts.AbstractPart;
import com.puresoltechnologies.javafx.perspectives.parts.Part;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PerspectivesTest extends ApplicationTest {

    PerspectiveContainer perspectiveContainer;

    @Override
    public void start(Stage stage) {
	perspectiveContainer = new PerspectiveContainer();
	perspectiveContainer.setId("perspectiveContainer");
	Scene scene = new Scene(perspectiveContainer, 800, 600);
	stage.setScene(scene);
	stage.show();
    }

    @Test
    public void testPerspectiveDialog() throws InterruptedException {
	Thread.sleep(1000);
	clickOn("Open...", MouseButton.PRIMARY);
	Thread.sleep(1000);
	clickOn("OK", MouseButton.PRIMARY);
    }

    @Test
    public void testPerspective() throws InterruptedException {
	Thread.sleep(100);
	Perspective perspective1 = new DefaultPerspective("tp1", "Test Perpective 1", () -> {
	    PartSplit partSplit = new PartSplit(Orientation.HORIZONTAL);
	    PartStack partStack1 = new PartStack();
	    PartStack partStack2 = new PartStack();
	    partSplit.addElement(partStack1);
	    partSplit.addElement(partStack2);
	    Part part1 = new AbstractPart("Part 1", true) {
		private static final long serialVersionUID = -254105758704559520L;

		@Override
		public String getId() {
		    return "part1";
		}

		@Override
		public Pane getContent() {
		    BorderPane borderPane = new BorderPane();
		    borderPane.setCenter(new Label("Part 1"));
		    return borderPane;
		}

		@Override
		public void close() {
		    // intentionally left empty
		}
	    };
	    Part part2 = new AbstractPart("Part 2", true) {

		private static final long serialVersionUID = 3636096691641347159L;

		@Override
		public String getId() {
		    return "part2";
		}

		@Override
		public Pane getContent() {
		    BorderPane borderPane = new BorderPane();
		    borderPane.setCenter(new Label("Part 2"));
		    return borderPane;
		}

		@Override
		public void close() {
		    // intentionally left empty
		}

	    };
	    partStack1.addElement(part1);
	    partStack2.addElement(part2);
	    return partSplit;
	});
	perspectiveContainer.addPerspective(perspective1);
	Thread.sleep(1000);
	drag("Part 1", MouseButton.PRIMARY).dropTo("Part 2");
	Thread.sleep(1000);
    }
}
