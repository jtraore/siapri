package com.siapri.broker.app;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

public class BundleUtil {

	public static <T> void registerService(final Class<T> clazz, final T service) {
		final Bundle bundle = FrameworkUtil.getBundle(BundleUtil.class);
		final BundleContext bundleCntxt = bundle.getBundleContext();
		bundleCntxt.registerService(clazz, service, null);
	}

	public static <T> T getService(final Class<T> clazz) {
		final Bundle bundle = FrameworkUtil.getBundle(BundleUtil.class);
		final BundleContext bundleCntxt = bundle.getBundleContext();
		return bundleCntxt.getService(bundleCntxt.getServiceReference(clazz));
	}
}
