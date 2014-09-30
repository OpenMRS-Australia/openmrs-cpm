package org.openmrs.module.conceptpropose.web.dto;

import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.openmrs.module.conceptpropose.ProposalStatus;
import org.openmrs.module.conceptpropose.web.dto.concept.ConceptDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProposedConceptDto extends ConceptDto {

	private String comment;

//	private List<CommentDto> discussion;
	private List<CommentDto> comments;

    private ProposalStatus status;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

//	public List<CommentDto> getDiscussion() { return discussion; }
//
//	public void setDiscussion(List<CommentDto> comments) { this.discussion = comments; }

	public List<CommentDto> getComments() { return comments; }

	public void setComments(List<CommentDto> comments) { this.comments = comments; }

    public ProposalStatus getStatus() {
        return status;
    }

    public void setStatus(ProposalStatus status) {
        this.status = status;
    }
}
