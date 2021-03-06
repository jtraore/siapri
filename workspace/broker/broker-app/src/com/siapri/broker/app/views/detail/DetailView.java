package com.siapri.broker.app.views.detail;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.IApplicationEvent;
import com.siapri.broker.app.PartViewService;

@Singleton
public class DetailView {
	
	@Inject
	private MApplication application;
	
	@Inject
	private EModelService modelService;
	
	@Inject
	@Optional
	private PartViewService partViewService;
	
	private Composite parent;

	private ScrolledComposite sc;
	
	@Inject
	public DetailView() {
	}
	
	@PostConstruct
	private void postConstruct(final Composite parent) {
		this.parent = parent;
		parent.setLayout(new FillLayout());
		parent.setBackgroundMode(SWT.INHERIT_FORCE);
	}
	
	@Inject
	@Optional
	private void itemSelected(@UIEventTopic(IApplicationEvent.ITEM_SELECTED) final Object item) {
		
		final MPartStack partStack = (MPartStack) modelService.find("broker-app.partstack.detail", application);
		partStack.getChildren().forEach(part -> {
			if (part.getRenderer() == null) {
				part.getTransientData().put("item", item);
			}
		});
		
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
		content.setLayout(new FillLayout());
		sc.setContent(content);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		
		final Composite detailsComposite = detailCompositeProvider.createComposite(content, item);
		if (detailsComposite == null) {
			return;
		}
		
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
		// final Rectangle r = detailArea.getClientArea();
		// sc.setMinSize(detailArea.computeSize(r.width, r.height));
	}
}