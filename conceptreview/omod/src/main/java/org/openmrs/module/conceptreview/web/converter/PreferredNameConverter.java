package org.openmrs.module.conceptreview.web.converter;

import org.dozer.CustomConverter;
import org.openmrs.api.ConceptNameType;
import org.openmrs.module.conceptreview.ProposedConceptReview;
import org.openmrs.module.conceptreview.ProposedConceptReviewName;

import java.util.List;

public class PreferredNameConverter implements CustomConverter {
	@Override
	public Object convert(Object existing, Object source, Class<?> destinationClass, Class<?> sourceClass) {

		if (source instanceof List) {
			for (Object name : (List) source) {
				if (name instanceof ProposedConceptReviewName &&
						((ProposedConceptReviewName) name).getType() == ConceptNameType.FULLY_SPECIFIED) {
					return ((ProposedConceptReviewName) name).getName();
				}
			}
		}
		return null;
	}
}
