package org.openmrs.module.conceptpropose.web.dto.factory;

import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.module.conceptpropose.web.dto.concept.AnswerDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnswerDtoFactory {

	public List<AnswerDto> create(Concept concept) {
		List<AnswerDto> answerDtos = new ArrayList<AnswerDto>();
		for (ConceptAnswer answer: concept.getAnswers()) {
			AnswerDto answerDto = new AnswerDto();
			if (answer.getConcept() != null) {
				answerDto.setConceptUuid(answer.getConcept().getUuid());
			}
			if (answer.getAnswerConcept() != null) {
				answerDto.setAnswerConceptUuid(answer.getAnswerConcept().getUuid());
			}
			if (answer.getAnswerDrug() != null) {
				answerDto.setAnswerDrugUuid(answer.getAnswerDrug().getUuid());
			}
			answerDto.setSortWeight(answer.getSortWeight());
			answerDtos.add(answerDto);
		}
		return answerDtos;
	}
}
