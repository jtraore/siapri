package com.siapri.broker.app;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.UIEvents.UILifeCycle;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.siapri.broker.business.Application;
import com.siapri.broker.business.service.IAuthenticationService;
import com.siapri.broker.business.service.IBasicDaoService;
import com.siapri.broker.business.service.impl.DaoCacheService;

public class ApplicationLifeCycleManager {

	// private static final String JAAS_CONFIG_FILE = "jaas_config.txt";
	//
	// private static final String JAAS_CONFIG_NAME = "LAWYER";

	@Inject
	private Logger logger;

	@Inject
	@Optional
	private MApplication application;

	@Inject
	private EModelService modelService;

	@Inject
	private EPartService partService;

	@Inject
	private IEventBroker eventBroker;

	@Inject
	private IEclipseContext eclipseContext;

	@Inject
	@Optional
	private Shell shell;

	@PostContextCreate
	void postContextCreate(final IApplicationContext applicationContext, final Display display) {
		applicationContext.applicationRunning();
		
		// Window.setDefaultImage(EImage.COURT.getSwtImage());

		eventBroker.subscribe(UILifeCycle.APP_STARTUP_COMPLETE, new EventHandler() {
			@Override
			public void handleEvent(final Event event) {
				registerPartViewService();
				registerE4Service();
				eventBroker.unsubscribe(this);
				eventBroker.post(IApplicationEvent.E4_SERVICES_AVAILABLE, null);
			}
		});

		new Thread(() -> registerDataService()).start();

		// Authentication using JAAS
		// final BundleContext bundleContext = Activator.getContext();
		// final URL configUrl = bundleContext.getBundle().getEntry(JAAS_CONFIG_FILE);
		// final ILoginContext secureContext = LoginContextFactory.createContext(JAAS_CONFIG_NAME, configUrl);
		// int count = 0;
		// boolean logged;
		// do {
		// try {
		// count++;
		// secureContext.login();
		// logged = true;
		// } catch (final LoginException e) {
		// logged = false;
		// count++;
		// logger.error(e);
		// }
		// } while (!logged || count > 3);
	}

	private void registerDataService() {
		final IBasicDaoService daoService = Application.getDaoService();
		ContextInjectionFactory.inject(daoService, eclipseContext);
		BundleUtil.registerService(IBasicDaoService.class, daoService);

		final DaoCacheService daoCacheService = Application.getDaoCacheService();
		ContextInjectionFactory.inject(daoCacheService, eclipseContext);
		BundleUtil.registerService(DaoCacheService.class, daoCacheService);

		final IAuthenticationService authService = Application.getAuthenticationService();
		authService.connect("admin", "admin");
		ContextInjectionFactory.inject(authService, eclipseContext);
		BundleUtil.registerService(IAuthenticationService.class, authService);
	}

	private void registerPartViewService() {
		final PartViewService partViewService = new PartViewService();
		ContextInjectionFactory.inject(partViewService, eclipseContext);
		BundleUtil.registerService(PartViewService.class, partViewService);
	}

	private void registerE4Service() {
		final E4Service e4Service = new E4Service(application, modelService, partService, eventBroker);
		BundleUtil.registerService(E4Service.class, e4Service);
	}
}
