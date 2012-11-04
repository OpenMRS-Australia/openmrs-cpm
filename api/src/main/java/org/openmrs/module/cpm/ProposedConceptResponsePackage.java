package org.openmrs.module.cpm;

import java.util.Date;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;

/**
 * This class represents a set of Concepts that has been proposed as a single group.  It acts as a wrapper for 
 * individual concept proposals - providing attributes that record the proposers rationale for why the concepts
 * are needed, and allowing the proposal reviewer to manage a master/detail style listing of the overall proposal
 * package and its individual concepts.
 */
public class ProposedConceptResponsePackage extends ShareablePackage<ProposedConceptResponse> {
	
	private static Log log = LogFactory.getLog(ProposedConceptResponsePackage.class);

	private Integer proposedConceptResponsePackageId;
	private String proposedConceptPackageUuid;
	private User creator;
	private Date dateCreated;
	private User changedBy;
	private Date dateChanged;
	private Integer version;
	
	/**
	 * Private constructor for Hibernate to be able to create empty objects
	 */
	protected ProposedConceptResponsePackage() {
		super();
	}
	
	/**
	 * Create the server side Concept Proposal Package Response based on the proposer submitted Concept 
	 * Proposal Package.  This changes the status of the proposal to reflect that this is in the first state 
	 * of the server side workflow
	 * 
	 * @param shareablePackage The Concept Proposal Package submitted by a client side proposer
	 */
	
	public ProposedConceptResponsePackage(final ProposedConceptPackage shareablePackage) {
		super();
		log.debug("Creating a ProposedConceptResponsePackage from: " + shareablePackage);

		this.setName(shareablePackage.getName());
		this.setEmail(shareablePackage.getEmail());
		this.setDescription(shareablePackage.getDescription());
		this.setProposedConceptPackageUuid(shareablePackage.getUuid());
		this.setProposedConcepts(new HashSet<ProposedConceptResponse>());
		
		if (shareablePackage.getProposedConcepts() != null) {
			// For each of the proposals in the submitted ProposedConceptPackage we create and equivalent 
			// response item that will allow us to record additional details
			
			for (ProposedConcept currentProposal : shareablePackage.getProposedConcepts()) {
				ProposedConceptResponse proposalResponse  = new ProposedConceptResponse(currentProposal);
				this.addProposedConcept(proposalResponse);
			}
		}
		
		this.setStatus(PackageStatus.RECEIVED);
		this.setVersion(0);
	}
	
	public Integer getId() {
		return this.proposedConceptResponsePackageId;
	}
	
	public void setId(final Integer id) {
		this.proposedConceptResponsePackageId = id;
	}

	
    public String getProposedConceptPackageUuid() {
    	return proposedConceptPackageUuid;
    }
	
    public void setProposedConceptPackageUuid(final String proposedConceptPackageUuid) {
    	this.proposedConceptPackageUuid = proposedConceptPackageUuid;
    }

	public Date getDateCreated() {
    	return dateCreated;
    }
	
    public void setDateCreated(final Date dateCreated) {
    	this.dateCreated = dateCreated;
    }
	
    public Date getDateChanged() {
    	return dateChanged;
    }
	
    public void setDateChanged(final Date dateChanged) {
    	this.dateChanged = dateChanged;
    }
	
    
    public Integer getVersion() {
    	return version;
    }

	
    public void setVersion(final Integer version) {
    	this.version = version;
    }

	public User getCreator() {
    	return creator;
    }
	
    public void setCreator(final User creator) {
    	this.creator = creator;
    }
	
    public User getChangedBy() {
    	return changedBy;
    }
	
    public void setChangedBy(final User changedBy) {
    	this.changedBy = changedBy;
    }	
	
	/*
	 * Utility methods
	 */
    
    @Override
    public String toString() {
    	StringBuffer appender = new StringBuffer();
    	appender.append("ConceptReviewPackage(");
    	appender.append(this.getId());
    	appender.append(")");
    	appender.append(super.toString());
    	return appender.toString();
    }

}
