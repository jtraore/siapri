package com.siapri.broker.app.views.common;

import java.net.MalformedURLException;
import java.net.URI;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import com.siapri.broker.app.Activator;

public enum EImage {
	
	OK, NOK, CREATE, DELETE, EDIT, PRINT, FIND, DESKTOP, ARROW, ARROW_DOWN, SPLASH, FOLDER, FILE, COURT;
	
	// private static final Logger LOGGER =
	// LoggerFactory.getLogger(EImage.class);
	private static final String IMAGE_DIR_URI = String.format("platform:/plugin/%s/icons/", Activator.APP_NAME);
	
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
