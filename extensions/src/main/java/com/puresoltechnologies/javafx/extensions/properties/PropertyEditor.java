package com.puresoltechnologies.javafx.extensions.properties;

import javafx.scene.Node;

/**
 * This is the interface for a property editor definition.
 * 
 * @author Rick-Rainer Ludwig
 */
public interface PropertyEditor<T> {

    public Node getEditor();

    public T getValue();

    public void setValue(T value);

}
