package com.puresoltechnologies.javafx.perspectives.parts;

import com.puresoltechnologies.javafx.perspectives.PerspectiveElement;

import javafx.scene.image.Image;

public interface Part extends PerspectiveElement, AutoCloseable {

    public String getName();

    public Image getImage();

    public boolean isSingleton();

    @Override
    public void close();

}
