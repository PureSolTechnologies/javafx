package com.puresoltechnologies.javafx.perspectives.parts;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.PartHeaderToolBar;
import com.puresoltechnologies.javafx.reactive.ReactiveFX;
import com.puresoltechnologies.javafx.tasks.TaskInfo;
import com.puresoltechnologies.javafx.tasks.TaskProgressPane;
import com.puresoltechnologies.javafx.tasks.TasksTopics;
import com.puresoltechnologies.javafx.utils.FXThreads;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * This part is used to show all running tasks and their progresses.
 * 
 * @author Rick-Rainer Ludwig
 */
public class TaskProgressViewer extends AbstractViewer implements Consumer<TaskInfo> {

    private final Map<Task<?>, TaskProgressPane<?>> taskPanes = new HashMap<>();
    private Disposable disposable;

    private final BorderPane borderPane = new BorderPane();
    private final VBox vBox = new VBox();
    private final ScrollPane scrollPane = new ScrollPane(vBox);

    public TaskProgressViewer() {
	super("Progress", PartOpenMode.AUTO_AND_MANUAL);
    }

    @Override
    public Optional<PartHeaderToolBar> getToolBar() {
	return Optional.empty();
    }

    @Override
    public void initialize() {
	borderPane.setCenter(scrollPane);
	scrollPane.setFitToWidth(true);
	disposable = ReactiveFX.getStore().subscribe(TasksTopics.TASK_STATUS_UPDATE, this);
    }

    @Override
    public void close() {
	if (disposable != null) {
	    disposable.dispose();
	}
    }

    @Override
    public Node getContent() {
	return borderPane;
    }

    @Override
    public void accept(TaskInfo taskInfo) throws Exception {
	Task<?> task = taskInfo.getTask();
	State state = task.getState();
	if (state == State.READY) {
	    if (!taskPanes.containsKey(task)) {
		TaskProgressPane<?> pane;
		Optional<Image> image = taskInfo.getImage();
		if (image.isPresent()) {
		    pane = new TaskProgressPane<>(task, image.get());
		} else {
		    pane = new TaskProgressPane<>(task);
		}
		pane.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID,
			new CornerRadii(0.0, 0.0, 0.0, 0.0, false), //
			new BorderWidths(1.0, 0.0, 1.0, 0.0, false, false, false, false), //
			new Insets(5, 5, 5, 5))));
		FXThreads.runOnFXThread(() -> {
		    vBox.getChildren().add(pane);
		    taskPanes.put(task, pane);
		});
	    }
	} else if (state == State.SCHEDULED) {
	    // TODO
	} else if ((state == State.SUCCEEDED) || (state == State.FAILED) || (state == State.CANCELLED)) {
	    TaskProgressPane<?> pane = taskPanes.get(task);
	    FXThreads.runOnFXThread(() -> {
		vBox.getChildren().remove(pane);
		taskPanes.remove(task);
	    });
	}
    }

}
