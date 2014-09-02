package org.openmrs.module.conceptpropose.web.dto.concept;

public class AnswerDto {

	public String conceptUuid;

	public String answerConceptUuid;

	public String answerConceptPreferredName; // for review UI

	public String answerDrugUuid;

	public String answerDrugPreferredName; // for review UI

	private Double sortWeight;

	public String getConceptUuid() {
		return conceptUuid;
	}

	public void setConceptUuid(String conceptUuid) {
		this.conceptUuid = conceptUuid;
	}

	public String getAnswerConceptUuid() {
		return answerConceptUuid;
	}

	public void setAnswerConceptUuid(String answerConceptUuid) {
		this.answerConceptUuid = answerConceptUuid;
	}

	public String getAnswerConceptPreferredName() {
		return answerConceptPreferredName;
	}

	public void setAnswerConceptPreferredName(String answerConceptPreferredName) {
		this.answerConceptPreferredName = answerConceptPreferredName;
	}

	public String getAnswerDrugUuid() {
		return answerDrugUuid;
	}

	public void setAnswerDrugUuid(String answerDrugUuid) {
		this.answerDrugUuid = answerDrugUuid;
	}

	public String getAnswerDrugPreferredName() {
		return answerDrugPreferredName;
	}

	public void setAnswerDrugPreferredName(String answerDrugPreferredName) {
		this.answerDrugPreferredName = answerDrugPreferredName;
	}

	public Double getSortWeight() {
		return sortWeight;
	}

	public void setSortWeight(Double sortWeight) {
		this.sortWeight = sortWeight;
	}
}
