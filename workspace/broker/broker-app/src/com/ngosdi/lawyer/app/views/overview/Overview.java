package com.ngosdi.lawyer.app.views.overview;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import com.ngosdi.lawyer.app.IApplicationEvent;
import com.ngosdi.lawyer.app.views.common.EImage;

@Singleton
public class Overview {

	@Inject
	private Composite parent;

	private ExpandBar expandBar;

	private final Map<IOverviewGroupProvider<?>, ExpandItem> overviewExpandItems = new HashMap<>();

	@Execute
	public void expandOrCollapseAll(final MDirectToolItem directToolItem) {
		final boolean tag = Boolean.parseBoolean(directToolItem.getTags().get(0));
		for (final ExpandItem item : expandBar.getItems()) {
			item.setExpanded(tag);
		}
	}

	@Inject
	@Optional
	private void createGui(@UIEventTopic(IApplicationEvent.E4_SERVICES_AVAILABLE) final Object obj) {
		parent.setBackgroundMode(SWT.INHERIT_FORCE);
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());

		expandBar = new ExpandBar(composite, SWT.V_SCROLL);
		expandBar.setSpacing(6);
		expandBar.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, true));
		expandBar.setBackground(parent.getBackground());

		final ExpandItem latestCasesItem = new ExpandItem(expandBar, SWT.NONE);
		latestCasesItem.setText("Dernières affaires jugées");
		latestCasesItem.setImage(EImage.DESKTOP.getSwtImage());

		for (final IOverviewGroupProvider<?> overviewGroupProvider : OverviewProvider.INSTANCE.getOverviewGroupProviders()) {
			final ExpandItem groupItem = new ExpandItem(expandBar, SWT.NONE);
			groupItem.setText(overviewGroupProvider.getTitle());
			groupItem.setImage(EImage.DESKTOP.getSwtImage());
			final OverviewGroupComposite overviewGroupComposite = new OverviewGroupComposite(expandBar, overviewGroupProvider);
			groupItem.setHeight(overviewGroupComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			groupItem.setControl(overviewGroupComposite);

			overviewExpandItems.put(overviewGroupProvider, groupItem);
		}

		final ExpandItem tasksItem = new ExpandItem(expandBar, SWT.NONE);
		tasksItem.setText("Taches plannifiées");
		tasksItem.setImage(EImage.DESKTOP.getSwtImage());

		parent.layout();
	}

	@Inject
	@Optional
	private void itemCreated(@UIEventTopic(IApplicationEvent.ITEM_CREATED) final Object item) {
		refreshOverview(item);
	}

	@Inject
	@Optional
	private void itemEdited(@UIEventTopic(IApplicationEvent.ITEM_EDITED) final Object item) {
		refreshOverview(item);
	}

	@Inject
	@Optional
	private void itemRemoved(@UIEventTopic(IApplicationEvent.ITEM_REMOVED) final Object item) {
		refreshOverview(item);
	}

	private void refreshOverview(final Object item) {
		overviewExpandItems.forEach((overviewGroupProvider, expandItem) -> {
			if (((ParameterizedType) overviewGroupProvider.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0].equals(item.getClass())) {
				expandItem.getControl().dispose();
				final OverviewGroupComposite overviewGroupComposite = new OverviewGroupComposite(expandItem.getParent(), overviewGroupProvider);
				expandItem.setHeight(overviewGroupComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
				expandItem.setControl(overviewGroupComposite);
				return;
			}
		});
	}
}