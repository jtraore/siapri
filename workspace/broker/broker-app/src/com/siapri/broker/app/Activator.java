package com.siapri.broker.app;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.eclipse.core.runtime.FileLocator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.siapri.broker.business.BusinessStarter;

import javassist.ClassPool;
import javassist.LoaderClassPath;

public class Activator implements BundleActivator {

	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

	public static final String APP_NAME = "broker-app";

	public static final String BUNDLE_URI_PREFIX = String.format("bundleclass://%s/", APP_NAME);
	public static final String MAIN_PART_STACK_ID = String.format("%s.partstack.main", APP_NAME);
	public static final String MAIN_PART_DESCRIPTOR_ID = String.format("%s.partdescriptor.main", APP_NAME);
	public static final String MAIN_TOOLBAR_ID = String.format("%s.toolbar.main", APP_NAME);

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
		ClassPool.getDefault().appendClassPath(new LoaderClassPath(getClass().getClassLoader()));
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
