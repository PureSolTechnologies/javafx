package com.puresoltechnologies.javafx.perspectives.parts;

import java.util.Optional;

import com.puresoltechnologies.javafx.perspectives.PartHeaderToolBar;
import com.puresoltechnologies.javafx.perspectives.PerspectiveElement;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

/**
 * This is the interface for a part of the perspective which provides the actual
 * content. These parts can handles to be replaced, closed and altered.
 * 
 * @author Rick-Rainer Ludwig
 */
public interface Part extends PerspectiveElement, AutoCloseable {

    /**
     * This method returns the title of the part.
     */
    public String getTitle();

    public void setTitle(String title);

    public StringProperty titleProperty();

    public Optional<Image> getImage();

    public void setImage(Image image);

    public ObjectProperty<Image> imageProperty();

    public Optional<PartHeaderToolBar> getToolBar();

    /**
     * This method tells whether or not this part is allowed to be opened by a user
     * interaction like the Show View Dialog.
     * 
     * @return
     */
    public PartOpenMode getOpenMode();

    public void initialize();

    /**
     * This method is called after the part was opened by a user interaction to
     * implement some user interaction to retrieve needed input parameters.
     */
    public void manualInitialization();

    @Override
    public void close();

}
