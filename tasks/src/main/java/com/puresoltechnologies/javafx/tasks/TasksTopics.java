package com.puresoltechnologies.javafx.tasks;

import com.puresoltechnologies.javafx.reactive.Topic;

public class TasksTopics {

    public static final Topic<TaskInfo> TASK_INFO = new Topic<>("com.puresoltechnologies.javafx.tasks.status",
	    TaskInfo.class);
    public static final Topic<TasksSummery> TASKS_SUMMARY = new Topic<>("com.puresoltechnologies.javafx.tasks.summary",
	    TasksSummery.class);

}
