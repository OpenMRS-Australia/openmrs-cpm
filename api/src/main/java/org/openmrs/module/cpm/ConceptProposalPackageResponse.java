package org.openmrs.module.cpm;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;

/**
 * This class represents a set of Concepts that has been proposed as a single group.  It acts as a wrapper for 
 * individual concept proposals - providing attributes that record the proposers rationale for why the concepts
 * are needed, and allowing the proposal reviewer to manage a master/detail style listing of the overall proposal
 * package and its individual concepts.
 */
public class ConceptProposalPackageResponse extends ShareablePackage {
	
	private static Log log = LogFactory.getLog(ConceptProposalPackageResponse.class);

	private Integer conceptProposalPackageId;
	private String conceptProposalPackageUuid;
	private User createdBy;
	private Date dateCreated;
	private User changedBy;
	private Date dateChanged;
	private Integer version;
	
	/**
	 * Private constructor for Hibernate to be able to create empty objects
	 */
	protected ConceptProposalPackageResponse() {
		super();
	}
	
	/**
	 * Create the server side Concept Proposal Package Response based on the proposer submitted Concept 
	 * Proposal Package.  This changes the status of the proposal to reflect that this is in the first state 
	 * of the server side workflow
	 * 
	 * @param shareablePackage The Concept Proposal Package submitted by a client side proposer
	 */
	
	public ConceptProposalPackageResponse(final ShareablePackage shareablePackage) {
		super();
		log.debug("Creating a ConceptProposalPackageResponse from: " + shareablePackage);

		this.setName(shareablePackage.getName());
		this.setEmail(shareablePackage.getEmail());
		this.setDescription(shareablePackage.getDescription());
		this.setProposedConcepts(new HashSet<ShareableProposal>());
		
		if (shareablePackage.getProposedConcepts() != null) {
			Set<ShareableProposal> proposedConceptResponses = this.getProposedConcepts();
			
			// For each of the proposals in the submitted ConceptProposalPackage we create and equivalent 
			// response item that will allow us to record additional details
			
			for (ShareableProposal currentProposal : shareablePackage.getProposedConcepts()) {
				ConceptProposalResponse proposalResponse  = new ConceptProposalResponse(currentProposal);
				proposedConceptResponses.add(proposalResponse);
			}
		}
		
		this.setStatus(PackageStatus.RECEIVED);
	}
	
	public Integer getId() {
		return this.conceptProposalPackageId;
	}
	
	public void setId(final Integer id) {
		this.conceptProposalPackageId = id;
	}

	
    public String getConceptProposalPackageUuid() {
    	return conceptProposalPackageUuid;
    }
	
    public void setConceptProposalPackageUuid(final String conceptProposalPackageUuid) {
    	this.conceptProposalPackageUuid = conceptProposalPackageUuid;
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

	public User getCreatedBy() {
    	return createdBy;
    }
	
    public void setCreatedBy(final User createdBy) {
    	this.createdBy = createdBy;
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
