package com.puresoltechnologies.javafx.extensions.lifecycle;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * This interface is used to implement a dedicated lifecycle into the component.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public interface NodeLifecycle {

    /**
     * This method disposes all disposable children of a {@link Parent} component.
     * Disposable children are {@link Node}s which implement this
     * {@link NodeLifecycle} interface.
     *
     * @param <N>        is the actual type of the parent node.
     * @param parentNode is the parent node containing the disposable children.
     */
    static <N extends Parent> void disposeChildren(N parentNode) {
        ObservableList<Node> children = parentNode.getChildrenUnmodifiable();
        for (Node child : children) {
            if (Parent.class.isAssignableFrom(child.getClass())) {
                disposeChildren((Parent) child);
            }
            if (NodeLifecycle.class.isAssignableFrom(child.getClass())) {
                ((NodeLifecycle) child).dispose();
            }
        }
    }

    /**
     * This method needs to implemented to completely dispose the node. It is used
     * to remove listeners, unsubscribe from services and close connections.
     * <p>
     * In order to also dispose children of a parent node,
     * {@link #disposeChildren(Parent)} can be used.
     */
    void dispose();

}
