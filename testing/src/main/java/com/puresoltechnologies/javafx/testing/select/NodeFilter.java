package com.puresoltechnologies.javafx.testing.select;

import javafx.scene.Node;

@FunctionalInterface
public interface NodeFilter<N extends Node> {

    boolean keep(N node);

}
