package com.puresoltechnologies.javafx.perspectives;

import java.io.Serializable;

import javafx.scene.layout.Pane;

public interface Part extends Serializable {

    public String getId();

    public String getName();

    public Pane getContent();

    public boolean isSingleton();
}
