package com.puresoltechnologies.javafx.perspectives;

import java.io.Serializable;
import java.util.List;

import javafx.scene.Node;

public interface PerspectiveElement extends Serializable {

    public String getId();

    public PerspectiveElement getParent();

    public Node getContent();

    public List<PerspectiveElement> getElements();

    public void addElement(PerspectiveElement e);

    public void removeElement(String id);

    public void removeElement(PerspectiveElement element);

    public boolean isSplit();

}
