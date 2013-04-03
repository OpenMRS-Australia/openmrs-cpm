package org.openmrs.module.cpm.web.dto;

public class ProposedConceptResponseDto extends ProposedConceptDto {

	private String reviewComments;

	private int conceptId;

	public String getReviewComments() {
		return reviewComments;
	}

	public void setReviewComments(final String reviewComments) {
		this.reviewComments = reviewComments;
	}

	public int getConceptId() {
		return conceptId;
	}

	public void setConceptId(final int conceptId) {
		this.conceptId = conceptId;
	}

}
