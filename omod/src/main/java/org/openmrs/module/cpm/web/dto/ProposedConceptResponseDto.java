package org.openmrs.module.cpm.web.dto;

public class ProposedConceptResponseDto extends ProposedConceptDto {

	private String reviewComments;

	public String getReviewComments() {
		return reviewComments;
	}

	public void setReviewComments(final String reviewComments) {
		this.reviewComments = reviewComments;
	}

}
