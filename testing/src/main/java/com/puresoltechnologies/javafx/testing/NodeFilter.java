package com.puresoltechnologies.javafx.testing;

import javafx.scene.Node;

@FunctionalInterface
public interface NodeFilter {

    boolean keep(Node node);

}
