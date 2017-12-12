package com.siapri.broker.app.views.common.proxy;

import java.beans.Introspector;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyCallback implements MethodInterceptor {

	private final IProxy proxy;

	public ProxyCallback(final Object target) {
		proxy = new Proxy(target);
	}

	@Override
	public Object intercept(final Object target, final Method method, final Object[] args, final MethodProxy methodProxy) throws Throwable {

		if (!Modifier.isAbstract(method.getModifiers())) {

			Object oldValue = null;

			if (method.getName().startsWith("set") && args != null && args.length == 1) {
				final String getterName = (args[0] instanceof Boolean ? "is" : "get") + method.getName().substring(3);
				final Method getter = target.getClass().getMethod(getterName);
				oldValue = getter.invoke(target);
			}

			final Object obj = method.invoke(proxy.getTarget(), args);

			if (args.length > 0 && !Objects.equals(oldValue, args[0])) {
				final String property = method.getName().substring(3);
				proxy.firePropertyChange(Introspector.decapitalize(property), oldValue, args[0]);
			}

			return obj;
		}

		if (method.getName().equals("addPropertyChangeListener")) {
			proxy.addPropertyChangeListener((PropertyChangeListener) args[0]);
		} else if (method.getName().equals("removePropertyChangeListener")) {
			proxy.removePropertyChangeListener((PropertyChangeListener) args[0]);
		} else if (method.getName().equals("getTarget")) {
			return proxy.getTarget();
		}

		if (proxy.getTarget().getClass().getDeclaredAnnotation(Data.class) != null) {
			if (method.getName().startsWith("set") && args != null && args.length == 1) {
				final String property = StringUtils.uncapitalize(method.getName().substring(3));
				final Field field = getField(property, proxy.getTarget());
				final boolean accessible = field.isAccessible();
				field.setAccessible(true);
				final Object oldValue = field.get(proxy.getTarget());
				field.set(proxy.getTarget(), args[0]);
				field.setAccessible(accessible);
				if (args.length > 0 && !Objects.equals(oldValue, args[0])) {
					proxy.firePropertyChange(property, oldValue, args[0]);
				}
			} else if ((method.getName().startsWith("get") || method.getName().startsWith("is")) && (args == null || args.length == 0)) {
				final Field field = getField(StringUtils.uncapitalize(method.getName().substring(method.getName().startsWith("get") ? 3 : 2)), proxy.getTarget());
				final boolean accessible = field.isAccessible();
				field.setAccessible(true);
				final Object value = field.get(proxy.getTarget());
				field.setAccessible(accessible);
				return value;
			}
		}

		return null;
	}

	private static Field getField(final String fieldName, final Object obj) throws NoSuchFieldException, SecurityException {
		Class<?> clazz = obj.getClass();
		Field field = null;
		while (clazz != null && field == null) {
			field = clazz.getDeclaredField(fieldName);
			clazz = clazz.getSuperclass();
		}
		return field;
	}

}