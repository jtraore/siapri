package com.siapri.broker.app.views.sinister;

import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizer;
import com.siapri.broker.app.views.common.proxy.ProxyFactory;
import com.siapri.broker.business.model.Sinister;

public class SinisterCustomizer extends AbstractCustomizer<Sinister> {
	
	private final SinisterCustomizerModel customizerModel;
	
	public SinisterCustomizer(final Sinister sinister, final String title, final String description) {
		super(sinister, title, description);
		customizerModel = ProxyFactory.createProxy(new SinisterCustomizerModel(sinister));
	}
	
	@Override
	public Composite createArea(final Composite parent, final int style) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void validateUpdate() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void cancelUpdate() {
		// TODO Auto-generated method stub

	}

}
