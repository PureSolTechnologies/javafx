package com.puresoltechnologies.javafx.perspectives;

public interface Part extends PerspectiveElement, AutoCloseable {

    public String getName();

    public boolean isSingleton();

    @Override
    public void close();

}
