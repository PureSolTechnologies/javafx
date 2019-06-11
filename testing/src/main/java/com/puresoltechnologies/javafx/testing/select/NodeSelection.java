package com.puresoltechnologies.javafx.testing.select;

import com.puresoltechnologies.javafx.testing.mouse.MouseInteraction;

import javafx.scene.Node;
import javafx.scene.control.Labeled;

/**
 * This is the top interface for any Node Selection.
 *
 * @author Rick-Rainer Ludwig
 */
public interface NodeSelection<N extends Node> extends NodeSearch<N>, MouseInteraction<N> {

    /**
     * This method returns the reference to the acutal node.
     *
     * @return
     */
    @Override
    N getNode();

    default <S extends Node> NodeSelection<S> select(S node) {
	return new NodeSelectionImpl<>(node);
    }

    default <S extends Node> NodeSelection<S> selectById(Class<S> clazz, String id) {
	return select(findNodeById(clazz, id));
    }

    default <S extends Labeled> NodeSelection<S> selectByText(Class<S> clazz, String text) {
	return select(findNode(clazz, node -> node.getText().equals(text)));
    }
}
