package org.openmrs.module.conceptreview.web.converter;

import org.dozer.CustomConverter;
import org.openmrs.api.context.Context;

import java.util.Locale;

public class ConceptUuidToPreferredNameConverter implements CustomConverter {

	@Override
	public Object convert(Object existing, Object source, Class<?> destinationClass, Class<?> sourceClass) {
		if (source instanceof String) {
			return Context.getConceptService().getConceptByUuid((String) source).getPreferredName(new Locale("en")).getName();
		}
		return null;
	}
}
