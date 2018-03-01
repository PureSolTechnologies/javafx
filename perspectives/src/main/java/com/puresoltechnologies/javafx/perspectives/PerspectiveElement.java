package com.puresoltechnologies.javafx.perspectives;

import java.util.List;

import javafx.scene.Node;

public interface PerspectiveElement {

    public String getId();

    public Node getContent();

    public List<PerspectiveElement> getElements();

    public void addElement(PerspectiveElement e);

    public void removeElement(String id);

    public void removeElement(PerspectiveElement element);

}
