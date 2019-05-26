package com.puresoltechnologies.javafx.services;

public class ServiceInformation {

    private final Class<? extends Service> clazz;
    private final String name;

    public ServiceInformation(Service service) {
	this(service.getClass(), service.getName());
    }

    public ServiceInformation(Class<? extends Service> clazz, String name) {
	super();
	this.clazz = clazz;
	this.name = name;
    }

    public Class<? extends Service> getClazz() {
	return clazz;
    }

    public String getName() {
	return name;
    }
}
