package org.openmrs.module.cpm;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

/**
 * This is the base class underlying the exchange of information of aggregated Concept Proposals
 * between a Concept proposer, and a Concept Proposal reviewer. The attributes modelled in the
 * abstract class are the ones that will be exchanged between the two using transfer REST services
 */
public abstract class ShareablePackage extends BaseOpenmrsObject {
	
	protected Log log = LogFactory.getLog(getClass());
	
	private String name;
	private String email;
	private String description;
	private Set<ShareableProposal> proposedConcepts = new HashSet<ShareableProposal>();
	private PackageStatus status = PackageStatus.DRAFT;
	
	public ShareablePackage() {
		super();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Set<ShareableProposal> getProposedConcepts() {
		return proposedConcepts;
	}
	
	public void setProposedConcepts(Set<ShareableProposal> proposedConcepts) {
		this.proposedConcepts = proposedConcepts;
	}
	
	public PackageStatus getStatus() {
		return status;
	}
	
	public void setStatus(PackageStatus status) {
		this.status = status;
	}
	
	/*
	 * Utility methods
	 */
	
	public void addProposedConcept(final ShareableProposal proposedConcept) {
		if (proposedConcept == null) {
			log.warn("Ignoring request to add null concept");
			return;
		}
		if (this.proposedConcepts != null) {
			log.debug("Adding concept: " + proposedConcept + " and adding: " + this + " as its parent");
			this.proposedConcepts.add(proposedConcept);
			proposedConcept.setProposedConceptPackage(this);
		} else {
			log.warn("Cannot add concept: " + proposedConcept + " to null concept list");
		}
	}
	
	public void removeProposedConcept(final ShareableProposal proposedConcept) {
		if (proposedConcept == null) {
			log.warn("Ignoring request to remove null concept");
			return;
		}
		if (this.proposedConcepts != null) {
			log.debug("Removing concept: " + proposedConcept);
			this.proposedConcepts.remove(proposedConcept);
		} else {
			log.warn("Cannot remove concept: " + proposedConcept + " to null concept list");
		}
	}
	
}
