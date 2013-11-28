package org.openmrs.module.conceptpropose.web.dto;

public class ProposedConceptResponseDto extends ProposedConceptDto {

	private String reviewComment;

	private int conceptId;

	public String getReviewComment() {
		return reviewComment;
	}

	public void setReviewComment(final String reviewComment) {
		this.reviewComment = reviewComment;
	}

	public int getConceptId() {
		return conceptId;
	}

	public void setConceptId(final int conceptId) {
		this.conceptId = conceptId;
	}

}
