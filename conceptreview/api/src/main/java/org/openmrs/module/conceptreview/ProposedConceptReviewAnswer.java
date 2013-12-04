package org.openmrs.module.conceptreview;

import org.hibernate.annotations.GenericGenerator;
import org.openmrs.BaseOpenmrsObject;

import javax.persistence.*;

@Entity
@Table(name = "cpm_proposed_concept_review_answer")
public class ProposedConceptReviewAnswer extends BaseOpenmrsObject {

	private Integer conceptAnswerId;

	private ProposedConceptReview proposedConceptReview;

	private String answerConceptUuid;

	private String answerDrugUuid;

	private Double sortWeight;

	@Id
	@GeneratedValue(generator = "nativeIfNotAssigned")
	@GenericGenerator(name = "nativeIfNotAssigned", strategy = "org.openmrs.api.db.hibernate.NativeIfNotAssignedIdentityGenerator")
	@Column(name = "cpm_proposed_concept_review_answer_id")
	@Override
	public Integer getId() {
		return conceptAnswerId;
	}

	@Override
	public void setId(Integer id) {
		conceptAnswerId = id;
	}

	@ManyToOne
	@JoinColumn(name = "proposed_concept_review")
	public ProposedConceptReview getProposedConceptReview() {
		return proposedConceptReview;
	}

	public void setProposedConceptReview(ProposedConceptReview proposedConceptReview) {
		this.proposedConceptReview = proposedConceptReview;
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
