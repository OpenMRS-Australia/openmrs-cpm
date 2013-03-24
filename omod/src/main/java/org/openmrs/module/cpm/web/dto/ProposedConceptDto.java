package org.openmrs.module.cpm.web.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.openmrs.module.cpm.ProposalStatus;
import org.openmrs.module.cpm.web.dto.concept.ConceptDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProposedConceptDto extends ConceptDto {

    private String comment;

    private ProposalStatus status;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ProposalStatus getStatus() {
        return status;
    }

    public void setStatus(ProposalStatus status) {
        this.status = status;
    }
}
