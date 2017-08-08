package com.ngosdi.lawyer.app.views.mainmenu;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuFactory;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

import com.ngosdi.lawyer.app.Activator;

public class NavigationMenuUtil {

	public static final String BACKWARD_ITEM_ID = "lawyer-app.toolbar.nav.item.backward";
	public static final String FORWARD_ITEM_ID = "lawyer-app.toolbar.nav.item.forward";

	public static MDirectMenuItem createMenuItem(final String partId, final MApplication application, final EModelService modelService, final Class<?> uriClass) {
		final String viewId = partId.substring((Activator.MAIN_PART_STACK_ID + ".").length());
		final MDirectToolItem directToolItem = (MDirectToolItem) modelService.find(viewId, application);
		final MDirectMenuItem menuItem = MMenuFactory.INSTANCE.createDirectMenuItem();
		menuItem.setLabel(directToolItem.getLabel());
		menuItem.setContributionURI(Activator.BUNDLE_URI_PREFIX + uriClass.getName());
		return menuItem;
	}

}
