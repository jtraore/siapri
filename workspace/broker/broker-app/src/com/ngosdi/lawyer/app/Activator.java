package com.ngosdi.lawyer.app;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.eclipse.core.runtime.FileLocator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.siapri.broker.business.BusinessStarter;

public class Activator implements BundleActivator {
	
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	
	public static final String BUNDLE_URI_PREFIX = "bundleclass://broker-app/";
	public static final String MAIN_PART_STACK_ID = "broker-app.partstack.main";
	public static final String MAIN_PART_DESCRIPTOR_ID = "broker-app.partdescriptor.main";
	
	private static BundleContext context;
	
	static BundleContext getContext() {
		return context;
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext )
	 */
	@Override
	public void start(final BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		final URL scanUrl = FileLocator.resolve(Thread.currentThread().getContextClassLoader().getResource("/"));
		BusinessStarter.start(scanUrl);
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}
	
}
