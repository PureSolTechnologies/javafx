package com.puresoltechnologies.javafx.perspectives.parts;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import com.puresoltechnologies.javafx.perspectives.PartHeaderToolBar;
import com.puresoltechnologies.javafx.perspectives.Perspective;
import com.puresoltechnologies.javafx.reactive.MessageBroker;
import com.puresoltechnologies.javafx.tasks.TaskInfo;
import com.puresoltechnologies.javafx.tasks.TaskProgressPane;
import com.puresoltechnologies.javafx.tasks.TasksTopics;
import com.puresoltechnologies.javafx.utils.FXThreads;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

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
public class TaskProgressViewer extends AbstractViewer implements Subscriber<TaskInfo> {

    private final Map<Task<?>, TaskProgressPane<?>> taskPanes = new HashMap<>();
    private Subscription subscription;

    private final BorderPane borderPane = new BorderPane();
    private final VBox vBox = new VBox();
    private final ScrollPane scrollPane = new ScrollPane(vBox);

    public TaskProgressViewer() {
	super("Progress", PartOpenMode.AUTO_AND_MANUAL);
	try {
	    setImage(ResourceUtils.getImage(Perspective.class, "icons/FatCow_Icons16x16/progressbar.png"));
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public Optional<PartHeaderToolBar> getToolBar() {
	return Optional.empty();
    }

    @Override
    public void initialize() {
	borderPane.setCenter(scrollPane);
	scrollPane.setFitToWidth(true);
	MessageBroker.getStore().subscribe(TasksTopics.TASK_STATUS_UPDATE, this);
    }

    @Override
    public void close() {
	if (subscription != null) {
	    subscription.cancel();
	}
    }

    @Override
    public Node getContent() {
	return borderPane;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
	if (this.subscription != null) {
	    this.subscription.cancel();
	}
	this.subscription = subscription;
	subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(TaskInfo taskInfo) {
	Task<?> task = taskInfo.getTask();
	FXThreads.runOnFXThread(() -> {
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
		    vBox.getChildren().add(pane);
		    taskPanes.put(task, pane);
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
	});
    }

    @Override
    public void onError(Throwable throwable) {
	// TODO
    }

    @Override
    public void onComplete() {
	// Intentionally left blank
    }

}
