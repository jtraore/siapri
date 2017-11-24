package com.siapri.broker.app.views.common.customizer.propertybinding.converter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class ZonedDateTimeToDateConverter implements IPropertyConverter<ZonedDateTime, Date> {
	
	@Override
	public Date fromEntity(final ZonedDateTime obj) {
		return obj != null ? Date.from(obj.toInstant()) : null;
	}
	
	@Override
	public ZonedDateTime toEntity(final Date obj) {
		return obj != null ? obj.toInstant().atZone(ZoneId.systemDefault()) : null;
	}
}
