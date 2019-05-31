package com.puresoltechnologies.javafx.extensions.dialogs.wizard;

import java.util.Optional;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;

public interface WizardPage<T> {

    void setData(T data);

    String getTitle();

    Optional<String> getDescription();

    Optional<Image> getImage();

    Node getNode();

    BooleanProperty canProceedProperty();

    BooleanProperty canFinishProperty();

}
