package org.openmrs.module.conceptpropose.web.converter;

import org.dozer.CustomConverter;
import org.openmrs.ConceptClass;
import org.openmrs.api.context.Context;

public class ConceptClassConverter implements CustomConverter {

	@Override
	public Object convert(Object dest, Object source, Class<?> destClass, Class<?> sourceClass) {

		if (source instanceof String) {
			final ConceptClass conceptClass = Context.getConceptService().getConceptClassByUuid((String) source);
			return conceptClass;
		}

		return null;
	}
}
