package com.siapri.broker.app;

import java.util.Map.Entry;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledMenuItem;
import org.eclipse.e4.ui.workbench.UIEvents.UILifeCycle;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.event.Event;
import org.osgi.service.prefs.BackingStoreException;

import com.siapri.broker.app.views.NavigationManager;
import com.siapri.broker.app.views.PartView;
import com.siapri.broker.app.views.mainmenu.NavigationMenuUtil;

@Singleton
public class ShowPart {

	@Inject
	private MApplication application;

	@Inject
	private EModelService modelService;

	@Inject
	private EPartService partService;

	@Inject
	@Optional
	private PartViewService partViewService;

	@Inject
	@Preference
	private IEclipsePreferences preferences;

	@Inject
	@Optional
	private Display currentDisplay;

	@Inject
	private IEventBroker eventBroker;

	@Execute
	public void execute(final MDirectToolItem directToolItem) {
		Display.getCurrent().asyncExec(() -> showOrHidePart(true, directToolItem.isSelected(), directToolItem.getElementId(), directToolItem.getIconURI(), directToolItem.getLabel()));
	}

	@Execute
	public void execute(final MHandledMenuItem handledMenuItem) {
		Display.getCurrent().asyncExec(() -> showOrHidePart(false, handledMenuItem.isSelected(), handledMenuItem.getElementId(), handledMenuItem.getIconURI(), handledMenuItem.getLabel()));
	}

	private void showOrHidePart(final boolean directToolItemEvent, final boolean itemSelected, final String elementId, final String iconURI, final String label) {
		final MPartStack partStack = (MPartStack) modelService.find(Activator.MAIN_PART_STACK_ID, application);
		final String partId = partStack.getElementId() + "." + elementId;
		if (itemSelected) {
			final MPart newPart = partService.createPart(Activator.MAIN_PART_DESCRIPTOR_ID);
			newPart.setElementId(partId);
			newPart.setContributionURI(partViewService.getPartViewMapping().get(elementId));
			if (directToolItemEvent) {
				newPart.setIconURI(iconURI.replace(".png", "_small.png"));
			}
			newPart.setLabel(label);
			partStack.getChildren().add(newPart);
			partService.showPart(newPart, PartState.ACTIVATE);
		} else {
			partService.hidePart(partService.findPart(partId));
		}
	}

	@Inject
	@Optional
	private void partActivated(@Active final MPart part) {
		final MPartStack partStack = (MPartStack) modelService.find(Activator.MAIN_PART_STACK_ID, application);
		if (!partViewService.getPartViewMapping().containsKey(part.getElementId().substring(partStack.getElementId().length() + 1))) {
			return;
		}

		final PartView<?> view = partViewService.getPartView(part.getElementId());
		eventBroker.post(IApplicationEvent.ITEMS_COUNT, view.getDataListComposite().getDataListModel().getDataList().size());

		preferences.put("broker.parts.active", part.getElementId());
		try {
			preferences.flush();
		} catch (final BackingStoreException e) {
			// TODO LOGGER
		}

		final NavigationManager navigationManager = partViewService.getNavigationManager();
		navigationManager.addPart(part.getElementId());
		final MDirectToolItem backwardToolItem = (MDirectToolItem) modelService.find(NavigationMenuUtil.BACKWARD_ITEM_ID, application);
		Display.getCurrent().asyncExec(() -> backwardToolItem.setEnabled(navigationManager.canGoBackward()));
		final MDirectToolItem forwardToolItem = (MDirectToolItem) modelService.find(NavigationMenuUtil.FORWARD_ITEM_ID, application);
		Display.getCurrent().asyncExec(() -> forwardToolItem.setEnabled(navigationManager.canGoForward()));
	}

	@Inject
	@Optional
	public void appStarted(@UIEventTopic(UILifeCycle.APP_STARTUP_COMPLETE) final Event event) {

		final Shell activeShell = currentDisplay.getActiveShell();
		final Listener[] shellCloseListeners = activeShell.getListeners(SWT.Close);
		for (final Listener listener : shellCloseListeners) {
			Display.getCurrent().getActiveShell().removeListener(SWT.Close, listener);
		}

		activeShell.addListener(SWT.Close, e -> savePreferences());

		for (final Listener listener : shellCloseListeners) {
			activeShell.addListener(SWT.Close, listener);
		}
	}

	@Inject
	@Optional
	public void e4ServicesAvailable(@UIEventTopic(IApplicationEvent.E4_SERVICES_AVAILABLE) final Event event) {
		final MPartStack partStack = (MPartStack) modelService.find(Activator.MAIN_PART_STACK_ID, application);
		final TreeMap<Integer, String> partMapping = new TreeMap<>();
		for (final Entry<String, String> entry : partViewService.getPartViewMapping().entrySet()) {
			final String partId = partStack.getElementId() + "." + entry.getKey();
			if (preferences.getBoolean(partId + ".displayed", false)) {
				final int index = preferences.getInt(partId + ".index", -1);
				partMapping.put(index, partId);
				// Reset
				preferences.putBoolean(partId + ".displayed", false);
			}
		}

		for (final Entry<Integer, String> entry : partMapping.entrySet()) {
			partViewService.showView(entry.getValue());
		}

		final String activePartId = preferences.get("broker.parts.active", null);
		if (activePartId != null) {
			partViewService.showView(activePartId);
		}
	}

	@Inject
	@Optional
	public void appShutingdown(@UIEventTopic(UILifeCycle.APP_SHUTDOWN_STARTED) final Event event) {
		// Not working for now (because of a bug in e4)
	}

	private void savePreferences() {
		final MPartStack partStack = (MPartStack) modelService.find(Activator.MAIN_PART_STACK_ID, application);
		int index = 0;
		for (final MStackElement part : partStack.getChildren()) {
			preferences.putBoolean(part.getElementId() + ".displayed", true);
			preferences.putInt(part.getElementId() + ".index", index++);
		}

		try {
			preferences.flush();
		} catch (final BackingStoreException e) {
			// TODO LOGGER
		}
	}

}