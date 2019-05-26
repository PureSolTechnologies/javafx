package com.puresoltechnologies.javafx.showroom.services;

import java.util.HashSet;
import java.util.Set;

import com.puresoltechnologies.javafx.services.Service;

public class ShowRoomService implements Service {

    @Override
    public Set<Class<? extends Service>> getDependencies() {
	return new HashSet<>();
    }

    @Override
    public String getName() {
	return "Show Room Example Service";
    }

    @Override
    public void construct() {
	// TODO Auto-generated method stub

    }

    @Override
    public void start() {
	// TODO Auto-generated method stub

    }

    @Override
    public void stop() {
	// TODO Auto-generated method stub

    }

    @Override
    public void destroy() {
	// TODO Auto-generated method stub

    }

}
