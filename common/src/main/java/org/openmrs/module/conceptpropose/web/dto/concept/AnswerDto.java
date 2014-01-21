package org.openmrs.module.conceptpropose.web.dto.concept;

public class AnswerDto {

	public String conceptUuid;

	public String answerConceptUuid;

	public String answerDrugUuid;

	private Double sortWeight;

	public String getAnswerConceptUuid() {
		return answerConceptUuid;
	}

	public void setAnswerConceptUuid(String answerConceptUuid) {
		this.answerConceptUuid = answerConceptUuid;
	}

	public String getConceptUuid() {
		return conceptUuid;
	}

	public void setConceptUuid(String conceptUuid) {
		this.conceptUuid = conceptUuid;
	}

	public String getAnswerDrugUuid() {
		return answerDrugUuid;
	}

	public void setAnswerDrugUuid(String answerDrugUuid) {
		this.answerDrugUuid = answerDrugUuid;
	}

	public Double getSortWeight() {
		return sortWeight;
	}

	public void setSortWeight(Double sortWeight) {
		this.sortWeight = sortWeight;
	}
}
