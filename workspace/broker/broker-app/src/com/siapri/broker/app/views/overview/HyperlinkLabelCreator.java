package com.siapri.broker.app.views.overview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;

public final class HyperlinkLabelCreator {

	private HyperlinkLabelCreator() {
	}

	@SuppressWarnings("rawtypes")
	public static Control create(final Composite parent, final Image icon, final OverviewItem overviewItem, final IOverviewItemLocator itemLocator) {
		final Hyperlink link;
		if (icon != null) {
			link = new ImageHyperlink(parent, SWT.NONE);
			((ImageHyperlink) link).setImage(icon);
			link.setData("org.eclipse.e4.ui.css.id", "ImageHyperlinkLabelStyle");
		} else {
			link = new Hyperlink(parent, SWT.NONE);
			link.setData("org.eclipse.e4.ui.css.id", "HyperlinkLabelStyle");
		}

		link.setUnderlined(false);
		link.setText(overviewItem.getText());

		link.addHyperlinkListener(new HyperlinkAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void linkActivated(final HyperlinkEvent event) {
				itemLocator.locate(overviewItem);
			}
		});

		return link;
	}
}