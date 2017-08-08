package com.ngosdi.lawyer.app.views.overview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.ngosdi.lawyer.app.views.common.EImage;

public class OverviewGroupComposite extends Composite {

	public OverviewGroupComposite(final Composite parent, final IOverviewGroupProvider<?> overviewGroupProvider) {
		super(parent, SWT.NONE);
		setBackground(parent.getBackground());
		createGui(overviewGroupProvider);
	}

	private void createGui(final IOverviewGroupProvider<?> overviewGroupProvider) {
		setLayout(new GridLayout());

		for (final OverviewItem<?> overviewItem : overviewGroupProvider.getOverviewItems()) {
			final Control control = HyperlinkLabelCreator.create(this, EImage.ARROW.getSwtImage(), overviewItem, overviewGroupProvider.getItemLocator());
			final GridData gridData = new GridData();
			gridData.horizontalIndent = 20;
			control.setLayoutData(gridData);
		}
	}
}
