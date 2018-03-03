package com.puresoltechnologies.javafx.perspectives.parts;

import com.puresoltechnologies.javafx.perspectives.PerspectiveElement;

public interface Part extends PerspectiveElement, AutoCloseable {

    public String getName();

    public boolean isSingleton();

    @Override
    public void close();

}
