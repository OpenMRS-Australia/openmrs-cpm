package org.openmrs.module.conceptpropose.web.dto;

import java.util.List;

public class ProposedConceptReviewDto extends ProposedConceptDto {

	private String reviewComment;

	private String newCommentName;
	private String newCommentEmail;
	private String newCommentText;

	private int conceptId;

	private String sourceUuid;

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

	public String getNewCommentName() {
		return newCommentName;
	}
	public void setNewCommentName(String newCommentName) {
		this.newCommentName = newCommentName;
	}

	public String getNewCommentEmail() {
		return newCommentEmail;
	}

	public void setNewCommentEmail(String newCommentEmail) {
		this.newCommentEmail = newCommentEmail;
	}

	public String getNewCommentText() {
		return newCommentText;
	}

	public void setNewCommentText(String newCommentText) {
		this.newCommentText = newCommentText;
	}

	public String getSourceUuid() {
		return sourceUuid;
	}

	public void setSourceUuid(String sourceUuid) {
		this.sourceUuid = sourceUuid;
	}
}
