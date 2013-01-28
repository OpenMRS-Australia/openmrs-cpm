package org.openmrs.module.cpm.web.dto;

import org.openmrs.module.cpm.ProposalStatus;

public class ProposedConceptDto extends ConceptDto{

	private boolean selected;

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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
