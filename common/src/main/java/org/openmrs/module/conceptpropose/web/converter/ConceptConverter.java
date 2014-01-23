package org.openmrs.module.conceptpropose.web.converter;

import org.dozer.CustomConverter;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;

public class ConceptConverter implements CustomConverter {

	@Override
	public Object convert(Object existing, Object source, Class<?> destinationClass, Class<?> sourceClass) {

		if (source instanceof Concept) {

			if (destinationClass == String.class) {
				return ((Concept) source).getName().getName();
			} else {
				return ((Concept) source).getConceptId();
			}

		} else if (source instanceof Integer) {
			return Context.getConceptService().getConcept(String.valueOf(source));
		}

		return null;
	}
}
