package com.siapri.broker.app.views.common.action;

import org.eclipse.swt.graphics.Image;

public class ContextualActionPathElement {

	private final String name;
	private final Image image;

	public ContextualActionPathElement(final String name, final Image image) {
		this.name = name;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public Image getImage() {
		return image;
	}
}
