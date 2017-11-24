package com.siapri.broker.app.views.common.customizer.propertybinding.converter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateToDateConverter implements IPropertyConverter<LocalDate, Date> {

	@Override
	public Date fromEntity(final LocalDate obj) {
		return obj != null ? Date.from(obj.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;
	}

	@Override
	public LocalDate toEntity(final Date obj) {
		return obj != null ? obj.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
	}

}
