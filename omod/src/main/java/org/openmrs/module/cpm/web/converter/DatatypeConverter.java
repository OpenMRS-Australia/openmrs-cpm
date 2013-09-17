package org.openmrs.module.cpm.web.converter;

import org.dozer.CustomConverter;
import org.openmrs.ConceptDatatype;
import org.openmrs.api.context.Context;

public class DatatypeConverter implements CustomConverter {

	@Override
	public Object convert(Object dest, Object source, Class<?> destClass, Class<?> sourceClass) {

		if (source instanceof String) {
			return Context.getConceptService().getConceptDatatypeByUuid((String) source);
		}

		return null;
	}
}
