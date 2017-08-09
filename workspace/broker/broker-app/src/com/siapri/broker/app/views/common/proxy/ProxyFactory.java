package com.siapri.broker.app.views.common.proxy;

import net.sf.cglib.proxy.Enhancer;

public class ProxyFactory {

	@SuppressWarnings("unchecked")
	public static <T> T createProxy(final T target) {
		final Enhancer enhancer = new Enhancer();
		enhancer.setClassLoader(ProxyFactory.class.getClassLoader());
		enhancer.setSuperclass(target.getClass());
		enhancer.setInterfaces(new Class[] { IProxy.class });
		enhancer.setCallback(new ProxyCallback(target));
		return (T) enhancer.create();
	}
}
