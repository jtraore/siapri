package com.siapri.broker.app.views.sinister;

import com.siapri.broker.business.model.Sinister;

public class SinisterDetail {

	private final Sinister sinister;

	public SinisterDetail(final Sinister sinister) {
		this.sinister = sinister;
	}

	public final Sinister getSinister() {
		return sinister;
	}

}
