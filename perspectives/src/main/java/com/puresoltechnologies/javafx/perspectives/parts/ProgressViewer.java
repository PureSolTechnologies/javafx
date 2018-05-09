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
public class ProgressViewer extends AbstractViewer implements Consumer<TaskInfo> {

    private BorderPane borderPane;
    private Map<Task<?>, TaskProgressPane<?>> taskPanes = new HashMap<>();
    private VBox vbox;
    private Disposable disposable;

    public ProgressViewer() {
	super("Progress", PartOpenMode.AUTO_AND_MANUAL);
    }

    @Override
    public Optional<PartHeaderToolBar> getToolBar() {
	return Optional.empty();
    }

    @Override
    public void initialize() {
	borderPane = new BorderPane();
	vbox = new VBox();

	borderPane.setCenter(vbox);
	disposable = ReactiveFX.getStore().subscribe(TasksTopics.TASK_INFO, this);
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
	if (taskInfo.getTask().getState() == State.SCHEDULED) {
	    TaskProgressPane<?> pane;
	    if (taskInfo.getImage().isPresent()) {
		pane = new TaskProgressPane<>(taskInfo.getImage().get(), taskInfo.getTask());
	    } else {
		pane = new TaskProgressPane<>(taskInfo.getTask());
	    }
	    pane.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID,
		    new CornerRadii(0.0, 0.0, 0.0, 0.0, false), //
		    new BorderWidths(1.0, 0.0, 1.0, 0.0, false, false, false, false), //
		    new Insets(5, 5, 5, 5))));
	    FXThreads.runOnFXThread(() -> {
		vbox.getChildren().add(pane);
		taskPanes.put(task, pane);
	    });
	} else if ((taskInfo.getTask().getState() == State.SUCCEEDED) || (taskInfo.getTask().getState() == State.FAILED)
		|| (taskInfo.getTask().getState() == State.CANCELLED)) {
	    TaskProgressPane<?> pane = taskPanes.get(task);
	    FXThreads.runOnFXThread(() -> {
		vbox.getChildren().remove(pane);
		taskPanes.remove(task);
	    });
	}
    }

}
