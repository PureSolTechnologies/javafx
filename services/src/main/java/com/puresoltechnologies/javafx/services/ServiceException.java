package com.puresoltechnologies.javafx.services;

public class ServiceException extends Exception {

    private static final long serialVersionUID = -7353765481984591096L;

    public ServiceException(String message, Throwable cause) {
	super(message, cause);
    }

    public ServiceException(String message) {
	super(message);
    }

}
