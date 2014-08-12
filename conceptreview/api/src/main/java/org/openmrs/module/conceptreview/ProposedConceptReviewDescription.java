package org.openmrs.module.conceptreview;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;

@Entity
@Table(name = "conceptreview_proposed_concept_review_description")
public class ProposedConceptReviewDescription implements Serializable {

	@Id
	@GeneratedValue(generator = "nativeIfNotAssigned")
	@GenericGenerator(name = "nativeIfNotAssigned", strategy = "org.openmrs.api.db.hibernate.NativeIfNotAssignedIdentityGenerator")
	@Column(name = "conceptreview_proposed_concept_review_description_id")
	private Integer proposedConceptReviewDescriptionId;

	@ManyToOne
	@JoinColumn(name = "proposed_concept_review")
	private ProposedConceptReview proposedConceptReview;

	private String description;

	private Locale locale;

	public ProposedConceptReview getProposedConceptReview() {
		return proposedConceptReview;
	}

	public void setProposedConceptReview(ProposedConceptReview proposedConceptReview) {
		this.proposedConceptReview = proposedConceptReview;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}
