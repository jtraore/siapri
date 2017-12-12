package com.siapri.broker.app.views.common.proxy;

import java.util.HashSet;
import java.util.Set;

import javassist.CannotCompileException;
import net.sf.cglib.proxy.Enhancer;

public class ProxyFactory {
	
	@SuppressWarnings("unchecked")
	public static <T> T createProxy(final T target) {
		final Enhancer enhancer = new Enhancer();
		enhancer.setClassLoader(ProxyFactory.class.getClassLoader());
		enhancer.setSuperclass(target.getClass());
		final Set<Class<?>> interfaces = new HashSet<>();
		interfaces.add(IProxy.class);
		if (target.getClass().getDeclaredAnnotation(Data.class) != null) {
			try {
				interfaces.add(DataClassInterfaceGenerator.createInterface(target.getClass()));
			} catch (final CannotCompileException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		enhancer.setInterfaces(interfaces.toArray(new Class[interfaces.size()]));
		enhancer.setCallback(new ProxyCallback(target));
		return (T) enhancer.create();
	}
}
