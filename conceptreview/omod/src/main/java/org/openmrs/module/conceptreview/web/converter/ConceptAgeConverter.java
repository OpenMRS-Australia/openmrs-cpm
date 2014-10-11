package org.openmrs.module.conceptreview.web.converter;

import org.dozer.CustomConverter;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Date;

public class ConceptAgeConverter implements CustomConverter {
	@Override
	public Object convert(Object existing, Object source, Class<?> destinationClass, Class<?> sourceClass) {

		if (source instanceof Date) {
			Days d = Days.daysBetween(new DateTime(source), new DateTime(new Date()));
			return String.valueOf(d.getDays());
		}

		return null;
	}
}
