package org.openmrs.module.conceptpropose.web.dto;

public class ProposedConceptReviewDto extends ProposedConceptDto {

	private String reviewComment;

	private String reviewDiscussion;

	private int conceptId;

	public String getReviewComment() {
		return reviewComment;
	}

	public void setReviewComment(final String reviewComment) {
		this.reviewComment = reviewComment;
	}

	public String getReviewDiscussion() { return reviewDiscussion; }

	public void setReviewDiscussion(final String reviewDiscussion) { this.reviewDiscussion= reviewDiscussion; }

	public int getConceptId() {
		return conceptId;
	}

	public void setConceptId(final int conceptId) {
		this.conceptId = conceptId;
	}

}
