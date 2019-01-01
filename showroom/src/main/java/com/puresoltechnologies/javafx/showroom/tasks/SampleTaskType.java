package com.puresoltechnologies.javafx.showroom.tasks;

public enum SampleTaskType {

    NO_FEEDBACK("No Task Progress Feedback."), WITH_PROGRESS("Complete Task Feedback");

    private final String description;

    SampleTaskType(String description) {
	this.description = description;
    }

    public String getDescription() {
	return description;
    }

    public static SampleTaskType byDescription(String string) {
	for (SampleTaskType type : values()) {
	    if (type.getDescription().equals(string)) {
		return type;
	    }
	}
	return null;
    }

}
