package com.puresoltechnologies.javafx.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(ResourceUtils.class);

    /**
     * This method reads the image from the given resource path relative to the
     * given class.
     *
     * @param object        is the class to specify the classloader.
     * @param imageResource is the resource path of the image to be loaded.
     * @return A {@link Image} object is returned. containing the picture.
     * @throws IOException is thrown in case of I/O issues.
     */
    public static Image getImage(Object object, String imageResource) throws IOException {
	return getImage(object.getClass(), imageResource);
    }

    /**
     * This method reads the image from the given resource path relative to the
     * given class.
     *
     * @param clazz         is the class to specify the classloader.
     * @param imageResource is the resource path of the image to be loaded.
     * @return A {@link Image} object is returned. containing the picture.
     * @throws IOException is thrown in case of I/O issues.
     */
    public static Image getImage(Class<?> clazz, String imageResource) throws IOException {
	InputStream perspectivesImage = clazz.getResourceAsStream(imageResource);
	if (perspectivesImage == null) {
	    logger.warn("Could not load image '" + imageResource + "'.");
	    return null;
	}
	try {
	    return new Image(perspectivesImage);
	} finally {
	    perspectivesImage.close();
	}
    }

    /**
     * This method reads the image from the given resource path relative to the
     * given class.
     *
     * @param object        is the class to specify the classloader.
     * @param imageResource is the resource path of the image to be loaded.
     * @return A {@link ImageView} object is returned. containing the picture.
     * @throws IOException is thrown in case of I/O issues.
     */
    public static ImageView getImageView(Object object, String imageResource) throws IOException {
	return getImageView(object.getClass(), imageResource);
    }

    /**
     * This method reads the image from the given resource path relative to the
     * given class.
     *
     * @param clazz         is the class to specify the classloader.
     * @param imageResource is the resource path of the image to be loaded.
     * @return A {@link ImageView} object is returned. containing the picture.
     * @throws IOException is thrown in case of I/O issues.
     */
    public static ImageView getImageView(Class<?> clazz, String imageResource) throws IOException {
	InputStream imageStream = clazz.getResourceAsStream(imageResource);
	if (imageStream == null) {
	    throw new FileNotFoundException("Resource '" + imageResource + "' was not found.");
	}
	try {
	    return new ImageView(new Image(imageStream));
	} finally {
	    imageStream.close();
	}
    }

    /**
     * Private constructor to avoid instantiation.
     */
    private ResourceUtils() {
    }

}
