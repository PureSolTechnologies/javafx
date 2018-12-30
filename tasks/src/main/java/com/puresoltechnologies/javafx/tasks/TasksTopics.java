package com.puresoltechnologies.javafx.tasks;

import com.puresoltechnologies.javafx.reactive.Topic;

public final class TasksTopics {

    /**
     * This topic is used to subscribe to task state changes.
     */
    public static final Topic<TaskInfo> TASK_STATUS_UPDATE = new Topic<>("com.puresoltechnologies.javafx.tasks.status",
	    TaskInfo.class);
    /**
     * This topic is used to send information for all running tasks in a
     * {@link TasksSummery} object.
     */
    public static final Topic<TasksSummery> TASKS_SUMMARY = new Topic<>("com.puresoltechnologies.javafx.tasks.summary",
	    TasksSummery.class);

}
