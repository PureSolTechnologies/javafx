package com.puresoltechnologies.javafx.perspectives.parts;

import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.PartHeaderToolBar;
import com.puresoltechnologies.javafx.perspectives.PerspectiveElement;

import javafx.scene.image.Image;

public interface Part extends PerspectiveElement, AutoCloseable {

    public String getName();

    public Optional<Image> getImage();

    public Optional<PartHeaderToolBar> getToolBar();

    public boolean isSingleton();

    public void initialize();

    @Override
    public void close();

}
