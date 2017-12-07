package com.siapri.broker.app.views.common.proxy;

import java.beans.Introspector;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;

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
				// extract the property name of the bean : ex : with method getClientName(), the bean property is clientName.
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

		return null;
	}

}
