package com.ngosdi.lawyer.app.views.common.proxy;

import java.beans.Introspector;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyCallback implements MethodInterceptor {

	private final IProxy proxy;

	public ProxyCallback(final Object target) {
		proxy = new Proxy(target);
	}

	@Override
	public Object intercept(final Object target, final Method method,
			final Object[] args, final MethodProxy methodProxy)
			throws Throwable {
		if (!Modifier.isAbstract(method.getModifiers())) {
			Object oldValue = null;

			if (method.getName().startsWith("set") && args != null
					&& args.length == 1) {
				final String getterName = "g" + method.getName().substring(1);
				final Method getter = target.getClass().getMethod(getterName);
				oldValue = getter.invoke(target);
			}

			final Object obj = method.invoke(proxy.getTarget(), args);

			if (oldValue != null) {
				//extract the property name of the bean : ex : with method getClientName(), the bean property is clientName.
				String property  = method.getName().substring(3);				
				proxy.firePropertyChange(Introspector.decapitalize(property), oldValue, args[0]);// TODO here replace
															// args[0] with the
															// new returned
															// value
			}

			return obj;
		}

		if (method.getName().equals("addPropertyChangeListener")) {
			proxy.addPropertyChangeListener((PropertyChangeListener) args[0]);
		} else if (method.getName().equals("removePropertyChangeListener")) {
			proxy.removePropertyChangeListener((PropertyChangeListener) args[0]);
		}

		return null;
	}

}
