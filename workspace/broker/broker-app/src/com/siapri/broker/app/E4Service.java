package com.siapri.broker.app;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class E4Service {

	private final MApplication application;
	private final EModelService modelService;
	private final EPartService partService;
	private final IEventBroker eventBroker;

	public E4Service(final MApplication application, final EModelService modelService, final EPartService partService, final IEventBroker eventBroker) {
		this.application = application;
		this.modelService = modelService;
		this.partService = partService;
		this.eventBroker = eventBroker;
	}

	public final MApplication getApplication() {
		return application;
	}

	public final EModelService getModelService() {
		return modelService;
	}

	public final EPartService getPartService() {
		return partService;
	}

	public final IEventBroker getEventBroker() {
		return eventBroker;
	}
}
