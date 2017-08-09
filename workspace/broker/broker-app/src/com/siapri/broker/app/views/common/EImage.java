package com.siapri.broker.app.views.common;

import java.net.MalformedURLException;
import java.net.URI;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public enum EImage {

	OK, NOK, CREATE, DELETE, EDIT, PRINT, FIND, DESKTOP, ARROW, ARROW_DOWN, SPLASH, FOLDER, FILE, COURT;

	// private static final Logger LOGGER =
	// LoggerFactory.getLogger(EImage.class);
	private static final String IMAGE_DIR_URI = "platform:/plugin/lawyer-app/icons/";

	public Image getSwtImage() {
		final URI uri = URI.create(IMAGE_DIR_URI + name().toLowerCase() + ".png");
		try {
			return ImageDescriptor.createFromURL(uri.toURL()).createImage();
		} catch (final MalformedURLException e) {
			// LOGGER.error("Unable to locate the image", e);
		}
		return null;
	}
}
