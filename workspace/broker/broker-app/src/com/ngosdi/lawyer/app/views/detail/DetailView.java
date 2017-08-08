package com.ngosdi.lawyer.app.views.detail;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.ngosdi.lawyer.app.IApplicationEvent;
import com.ngosdi.lawyer.app.PartViewService;

public class DetailView {

	@Inject
	@Optional
	private PartViewService partViewService;

	private Composite parent;
	private ScrolledComposite sc;

	@Inject
	public DetailView() {
	}

	@PostConstruct
	public void postConstruct(final Composite parent) {
		this.parent = parent;
		parent.setLayout(new FillLayout());
		parent.setBackgroundMode(SWT.INHERIT_FORCE);
	}

	@Inject
	@Optional
	private void itemSelected(@UIEventTopic(IApplicationEvent.ITEM_SELECTED) final Object item) {
		if (sc != null) {
			sc.dispose();
		}
		if (item == null) {
			return;
		}

		partViewService.getDetailCompositeProvider(item).ifPresent(provider -> displayDetails(item, provider));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void displayDetails(final Object item, final IDetailCompositeProvider detailCompositeProvider) {
		sc = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		final Composite content = new Composite(sc, SWT.NONE);
		content.setLayout(new GridLayout());
		sc.setContent(content);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);

		final Composite detailsComposite = detailCompositeProvider.createComposite(content, item);

		sc.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(final ControlEvent e) {
				refreshScroll(detailsComposite);
			}
		});

		parent.layout();

		refreshScroll(detailsComposite);
	}

	private void refreshScroll(final Composite detailArea) {
		final Rectangle r = detailArea.getClientArea();
		sc.setMinSize(detailArea.computeSize(r.width, r.height));
	}
}