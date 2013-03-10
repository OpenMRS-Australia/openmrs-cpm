package org.openmrs.module.cpm.web.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.openmrs.module.cpm.ProposalStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProposedConceptDto extends ConceptDto{

    private String comments;

    private ProposalStatus status;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ProposalStatus getStatus() {
        return status;
    }

    public void setStatus(ProposalStatus status) {
        this.status = status;
    }
}
