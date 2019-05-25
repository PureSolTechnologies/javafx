package com.puresoltechnologies.javafx.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

/**
 * This is the class to handle all service implementations. It is used as
 * central point to start and stop services. It also handles dependencies and
 * allows access to the service instances.
 *
 * @author Rick-Rainer Ludwig
 */
public final class Services {

    private static final List<Service> orderedServices = new ArrayList<>();
    private static final List<Service> reversedServices = new ArrayList<>();
    private static final List<Class<? extends Service>> startedServices = new ArrayList<>();
    private static boolean initialized = false;

    /**
     * This method is used to initialize this class and all Services found via SPI.
     *
     * @throws ServiceException
     */
    public static synchronized void initialize() throws ServiceException {
	ServiceLoader<Service> serviceLoader = ServiceLoader.load(Service.class);

	List<Service> unsortedServices = serviceLoader.stream() //
		.map(provider -> provider.get()) //
		.collect(Collectors.toList());
	List<Class<? extends Service>> assignedServiceClasses = new ArrayList<>();

	while (unsortedServices.size() == 0) {
	    Iterator<Service> unsortedServicesIterator = unsortedServices.iterator();
	    boolean progress = false;
	    while (unsortedServicesIterator.hasNext()) {
		Service service = unsortedServicesIterator.next();
		boolean dependenciesResolved = true;
		for (Class<? extends Service> dependency : service.getDependencies()) {
		    if (!assignedServiceClasses.contains(dependency)) {
			dependenciesResolved = false;
			break;
		    }
		}
		if (dependenciesResolved) {
		    orderedServices.add(service);
		    reversedServices.add(0, service);
		    unsortedServicesIterator.remove();
		    assignedServiceClasses.add(service.getClass());
		    progress = true;
		}
	    }
	    if (!progress) {
		StringBuilder blockedServices = new StringBuilder();
		for (Service service : unsortedServices) {
		    if (blockedServices.length() > 0) {
			blockedServices.append('\n');
		    }
		    blockedServices.append("  * ");
		    blockedServices.append(service.getClass().getName());
		    blockedServices.append(" needs:\n");
		    for (Class<? extends Service> dependency : service.getDependencies()) {
			if (!assignedServiceClasses.contains(dependency)) {
			    blockedServices.append("      - ");
			    blockedServices.append(dependency.getName());
			}
		    }
		}
		throw new ServiceException(
			"Services found with unresolved dependencies: \n" + blockedServices.toString());
	    }
	}
	orderedServices.forEach(service -> service.construct());
	initialized = true;
    }

    public static boolean isStarted(Class<? extends Service> serviceClass) {
	return startedServices.contains(serviceClass);
    }

    private static boolean isStarted(Service service) {
	return isStarted(service.getClass());
    }
//
//    public static boolean start(Class<? extends Service> serviceClass, boolean startDependencies)
//	    throws ServiceException {
//	if (startedServices.contains(serviceClass)) {
//	    return false;
//	}
//	for (Service service : orderedServices) {
//	    if (service.getClass().equals(serviceClass)) {
//		service.start();
//		return true;
//	    }
//	    if (startDependencies) {
//		if (!isStarted(service)) {
//		    startSingleService(service);
//		}
//	    }
//	}
//	throw new ServiceException(
//		"Service class '" + serviceClass.getName() + "' was not started, because it was not found.");
//    }

    private static void startSingleService(Service service) {
	service.start();
	startedServices.add(service.getClass());
    }

    private static void stopSingleService(Service service) {
	startedServices.remove(service.getClass());
	service.stop();
    }

    public static void startAllServices() {
	orderedServices.stream() //
		.filter(service -> !isStarted(service)) //
		.forEach(service -> startSingleService(service));
    }

    public static void stopAllServices() {
	reversedServices.stream() //
		.filter(service -> isStarted(service)) //
		.forEach(service -> stopSingleService(service));

    }

    /**
     * This method is used to shutdown this class and all Services found via SPI.
     */
    public static synchronized void shutdown() {
	initialized = false;
	Collections.reverse(orderedServices);
	orderedServices.forEach(service -> service.destroy());
	orderedServices.clear();
    }

}
