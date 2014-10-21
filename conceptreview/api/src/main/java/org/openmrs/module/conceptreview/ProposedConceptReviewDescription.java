package org.openmrs.module.conceptreview;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Locale;

@Entity
@Table(name = "conceptreview_proposed_concept_review_description")
public class ProposedConceptReviewDescription implements Serializable {

	@Id
	@ManyToOne
	@JoinColumn(name = "proposed_concept_review")
	private ProposedConceptReview proposedConceptReview;

	@Id
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
