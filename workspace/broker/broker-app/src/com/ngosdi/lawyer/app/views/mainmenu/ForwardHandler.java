
package com.ngosdi.lawyer.app.views.mainmenu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.AboutToHide;
import org.eclipse.e4.ui.di.AboutToShow;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.renderers.swt.MenuManagerRenderer;
import org.eclipse.swt.widgets.Display;

import com.ngosdi.lawyer.app.PartViewService;
import com.ngosdi.lawyer.app.views.NavigationManager;

public class ForwardHandler {

	@Inject
	protected MApplication application;

	@Inject
	protected EModelService modelService;

	@Inject
	@Optional
	protected PartViewService partViewService;

	@Execute
	public void execute(final MDirectToolItem forwardToolItem) {
		processForward(partViewService.getNavigationManager().forward());
	}

	@Execute
	public void execute(final MDirectMenuItem menuItem) {
		final int itemIndex = menuItem.getParent().getChildren().indexOf(menuItem);
		final NavigationManager navigationManager = partViewService.getNavigationManager();
		for (int i = 0; i < itemIndex - 1; i++) {
			navigationManager.forward();
		}
		processForward(navigationManager.forward());
	}

	@AboutToShow
	public void aboutToShow(final List<MMenuElement> items) {
		final MMenu menuModel = ((MDirectToolItem) modelService.find(NavigationMenuUtil.FORWARD_ITEM_ID, application)).getMenu();
		final MenuManagerRenderer menuManagerRenderer = (MenuManagerRenderer) menuModel.getRenderer();
		menuManagerRenderer.removeDynamicMenuContributions(menuManagerRenderer.getManager(menuModel), menuModel,
				new ArrayList<>(menuModel.getChildren().stream().filter(item -> item instanceof MDirectMenuItem).collect(Collectors.toList())));

		final NavigationManager navigationManager = partViewService.getNavigationManager();
		items.addAll(navigationManager.getForward().stream().map(partId -> NavigationMenuUtil.createMenuItem(partId, application, modelService, getClass())).collect(Collectors.toList()));
	}

	@AboutToHide
	public void aboutToHide(final List<MMenuElement> items) {
	}

	private void processForward(final String partId) {
		final NavigationManager navigationManager = partViewService.getNavigationManager();
		final MDirectToolItem forwardToolItem = (MDirectToolItem) modelService.find(NavigationMenuUtil.FORWARD_ITEM_ID, application);
		Display.getCurrent().asyncExec(() -> forwardToolItem.setEnabled(navigationManager.canGoForward()));
		partViewService.showView(partId);
		final MDirectToolItem backwardToolItem = (MDirectToolItem) modelService.find(NavigationMenuUtil.BACKWARD_ITEM_ID, application);
		Display.getCurrent().asyncExec(() -> backwardToolItem.setEnabled(navigationManager.canGoBackward()));
	}

}