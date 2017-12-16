package com.siapri.broker.app.views.common.customizer.propertybinding.converter;

import com.siapri.broker.app.views.common.proxy.IProxy;
import com.siapri.broker.app.views.common.proxy.ProxyFactory;

public class BeanToProxyConverter implements IPropertyConverter<Object, IProxy> {
	
	@Override
	public IProxy fromEntity(final Object obj) {
		return (IProxy) ProxyFactory.createProxy(obj);
	}
	
	@Override
	public Object toEntity(final IProxy obj) {
		return obj.getTarget();
	}

}
