package org.openmrs.module.conceptpropose.web.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.openmrs.module.conceptpropose.ProposalStatus;
import org.openmrs.module.conceptpropose.web.dto.concept.ConceptDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProposedConceptDto extends ConceptDto {

	private String comment;

	private String discussion;

    private ProposalStatus status;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDiscussion() {
		return discussion;
	}

	public void setDiscussion(String discussion) { this.discussion = discussion; }

    public ProposalStatus getStatus() {
        return status;
    }

    public void setStatus(ProposalStatus status) {
        this.status = status;
    }
}
