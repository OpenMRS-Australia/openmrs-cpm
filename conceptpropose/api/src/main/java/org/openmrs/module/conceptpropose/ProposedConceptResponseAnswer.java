package org.openmrs.module.conceptpropose;

import org.hibernate.annotations.GenericGenerator;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Concept;
import org.openmrs.Drug;
import org.openmrs.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cpm_proposed_concept_response_answer")
public class ProposedConceptResponseAnswer extends BaseOpenmrsObject {

	private Integer conceptAnswerId;

	private ProposedConceptResponse proposedConceptResponse;

	private String answerConceptUuid;

	private String answerDrugUuid;

	private Double sortWeight;

	@Id
	@GeneratedValue(generator = "nativeIfNotAssigned")
	@GenericGenerator(name = "nativeIfNotAssigned", strategy = "org.openmrs.api.db.hibernate.NativeIfNotAssignedIdentityGenerator")
	@Column(name = "cpm_proposed_concept_response_answer_id")
	@Override
	public Integer getId() {
		return conceptAnswerId;
	}

	@Override
	public void setId(Integer id) {
		conceptAnswerId = id;
	}

	@ManyToOne
	@JoinColumn(name = "proposed_concept_response")
	public ProposedConceptResponse getProposedConceptResponse() {
		return proposedConceptResponse;
	}

	public void setProposedConceptResponse(ProposedConceptResponse proposedConceptResponse) {
		this.proposedConceptResponse = proposedConceptResponse;
	}

	@Column(name = "answer_concept_uuid")
	public String getAnswerConceptUuid() {
		return answerConceptUuid;
	}

	public void setAnswerConceptUuid(String answerConceptUuid) {
		this.answerConceptUuid = answerConceptUuid;
	}

	@Column(name = "answer_drug_uuid")
	public String getAnswerDrugUuid() {
		return answerDrugUuid;
	}

	public void setAnswerDrugUuid(String answerDrugUuid) {
		this.answerDrugUuid = answerDrugUuid;
	}

	@Column(name = "sort_weight")
	public Double getSortWeight() {
		return sortWeight;
	}

	public void setSortWeight(Double sortWeight) {
		this.sortWeight = sortWeight;
	}
}
