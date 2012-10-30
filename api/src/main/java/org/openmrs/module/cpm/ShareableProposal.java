package org.openmrs.module.cpm;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Concept;

/**
 * This is the base class underlying the exchange of information of individual Concept Proposals
 * between a Concept proposer, and a Concept Proposal reviewer. The attributes modelled in the
 * abstract class are the ones that will be exchanged between the two using transfer REST services
 */
public abstract class ShareableProposal  extends BaseOpenmrsObject {

	private static Log log = LogFactory.getLog(ShareableProposal.class);

	private String name;
	private String description;
	private ShareablePackage proposedConceptPackage;
	private Concept concept;
	private Set<ShareableComment> comments = new HashSet<ShareableComment>();
	private ProposalStatus status = ProposalStatus.DRAFT;

    public abstract Integer getId();
    public abstract void setId(Integer id);

    public String getName() {
    	return name;
    }
	
    public void setName(final String name) {
    	this.name = name;
    }
    
    public String getDescription() {
    	return description;
    }
	
    public void setDescription(final String description) {
    	this.description = description;
    }
    
	public ShareablePackage getProposedConceptPackage() {
		return proposedConceptPackage;
	}
	
	public Concept getConcept() {
		return concept;
	}

	public void setConcept(final Concept concept) {
		this.concept = concept;
	}
	
	public void setProposedConceptPackage(final ShareablePackage proposedConceptPackage) {
		this.proposedConceptPackage = proposedConceptPackage;
	}
	
	public Set<ShareableComment> getComments() {
    	return comments;
    }

    public void setComments(final Set<ShareableComment> comments) {
    	this.comments = comments;
    }
    
    public ProposalStatus getStatus() {
    	return status;
    }
	
    public void setStatus(final ProposalStatus status) {
    	this.status = status;
    }

	/*
	 * Utility methods
	 */
	
    public void addComment(final ShareableComment comment) {
		if (comment == null) {
			log.warn("Ignoring request to add null comment");
			return;
		}
		if (this.comments != null) {
			log.debug("Adding comment: " + comment);
			this.comments.add(comment);
		} else {
			log.warn("Cannot add comment: " + comment + " to null comment list");
		}
	}
	
	public void removeComment(final ShareableComment comment) {
		if (comment == null) {
			log.warn("Ignoring request to remove null comment");
			return;
		}
		if (this.comments != null) {
			log.debug("Removing comment: " + comment);
			this.comments.add(comment);
		} else {
			log.warn("Cannot remove comment: " + comment + " to null comment list");
		}
	}
    
}
