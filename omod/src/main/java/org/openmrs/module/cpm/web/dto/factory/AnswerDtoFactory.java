package org.openmrs.module.cpm.web.dto.factory;

import com.google.common.collect.Lists;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.module.cpm.web.dto.concept.AnswerDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnswerDtoFactory {

	public List<AnswerDto> create(Concept concept) {
		List<AnswerDto> answerDtos = new ArrayList<AnswerDto>();
		for (ConceptAnswer answer: concept.getAnswers()) {
			AnswerDto answerDto = new AnswerDto();
			answerDto.setConceptUuid(answer.getConcept().getUuid());
			answerDto.setAnswerConceptUuid(answer.getAnswerConcept().getUuid());
			answerDto.setAnswerDrugUuid(answer.getAnswerDrug().getUuid());
			answerDto.setSortWeight(answer.getSortWeight());
			answerDtos.add(answerDto);
		}
		return answerDtos;
	}
}
