package com.puresoltechnologies.javafx.utils;

import java.io.IOException;
import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is used to load resources easier and to provide them in a JavaFX
 * convenient form.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public class ResourceUtils {

    /**
     * This method reads the image from the given resource path relative to the
     * given class.
     * 
     * @param clazz
     *            is the class to specify the classloader.
     * @param imageResource
     *            is the resource path of the image to be loaded.
     * @return A {@link Image} object is returned. containing the picture.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public static Image getImage(Object clazz, String imageResource) throws IOException {
	try (InputStream perspectivesImage = clazz.getClass().getResourceAsStream(imageResource)) {
	    return new Image(perspectivesImage);
	}
    }

    /**
     * This method reads the image from the given resource path relative to the
     * given class.
     * 
     * @param clazz
     *            is the class to specify the classloader.
     * @param imageResource
     *            is the resource path of the image to be loaded.
     * @return A {@link ImageView} object is returned. containing the picture.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public static ImageView getImageView(Object clazz, String imageResource) throws IOException {
	try (InputStream perspectivesImage = clazz.getClass().getResourceAsStream(imageResource)) {
	    return new ImageView(new Image(perspectivesImage));
	}
    }

    /**
     * Private constructor to avoid instantiation.
     */
    private ResourceUtils() {
    }

}
