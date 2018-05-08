package com.puresoltechnologies.javafx.tasks;

import java.io.IOException;

import com.puresoltechnologies.javafx.reactive.ReactiveFX;
import com.puresoltechnologies.javafx.utils.FXThreads;
import com.puresoltechnologies.javafx.utils.ResourceUtils;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * This class is used for instance in status bars to show the overall status of
 * all running tasks.
 * 
 * @author Rick-Rainer Ludwig
 */
public class TasksStatusBar extends GridPane implements AutoCloseable, Consumer<TasksSummery> {

    private final Label taskNumlabel;
    private final ProgressBar progressBar;
    private final Button openButton;
    private final Disposable disposable;

    public TasksStatusBar() {
	super();
	try {
	    taskNumlabel = new Label("0 tasks");
	    progressBar = new ProgressBar(0.0);
	    ImageView detailsView = ResourceUtils.getImageView(this,
		    "/icons/FatCow_Icons16x16/application_view_list.png");
	    progressBar.visibleProperty().bind(progressBar.progressProperty().greaterThan(0.0));
	    openButton = new Button("Overview...", detailsView);

	    setConstraints(taskNumlabel, 0, 0, 1, 1, HPos.RIGHT, VPos.BASELINE, Priority.NEVER, Priority.NEVER);
	    setConstraints(progressBar, 1, 0, 1, 1, HPos.CENTER, VPos.BASELINE, Priority.ALWAYS, Priority.NEVER);
	    setConstraints(openButton, 2, 0, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.SOMETIMES, Priority.NEVER);

	    getChildren().addAll(taskNumlabel, progressBar, openButton);

	    disposable = ReactiveFX.getStore().subscribe(TasksTopics.TASKS_SUMMARY, this);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void close() {
	if (disposable != null) {
	    disposable.dispose();
	}
    }

    @Override
    public void accept(TasksSummery summary) throws Exception {
	FXThreads.runOnFXThread(() -> {
	    taskNumlabel.setText(summary.getTaskNum() + " tasks");
	    progressBar.setProgress(summary.getProgress());
	    progressBar.setTooltip(new Tooltip(summary.getTaskNum() + " tasks"));
	});
    }

}
