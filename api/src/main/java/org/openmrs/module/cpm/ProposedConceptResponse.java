package org.openmrs.module.cpm;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;

/**
 * This extends the SharedProposal to represent a persisted Concept Proposal on the reviewers side
 * of the Concept Proposal exchange. This will be persisted on the reviewer instance of OpenMRS, by
 * server side REST services. Given that the ids on both the client and server sides will be
 * different, the underlying UUID will be used to test for equality.
 * 
 * @see ConceptProposalReview
 */
public class ProposedConceptResponse extends ShareableProposal {
	
	private static Log log = LogFactory.getLog(ProposedConceptResponse.class);
	
	private Integer conceptProposalResponseId;
	private String proposedConceptUuid;
	private User createdBy;
	private Date dateCreated;
	private User changedBy;
	private Date dateChanged;
	private Integer version;
	
	/**
	 * Private constructore for Hibernate
	 */
	protected ProposedConceptResponse() {
		super();
	}
	
	/**
	 * Create the server side Concept Proposal Response based on the proposer submitted Concept Proposal.
	 * This changes the status of the proposal to reflect that this is in the first state of the server 
	 * side workflow
	 * 
	 * @param shareableProposal The Concept Proposal submitted by a client side proposer
	 */
	public ProposedConceptResponse(ShareableProposal shareableProposal) {
		super();
		log.debug("Creating a new ProposedConceptResponse from: " + shareableProposal);
		
		this.setName(shareableProposal.getName());
		this.setProposedConceptUuid(shareableProposal.getUuid());
		this.setComments(shareableProposal.getComments());
		this.setStatus(ProposalStatus.RECEIVED);		
	}
	
 	public Integer getId() {
		return conceptProposalResponseId;
	}
	
	public void setId(final Integer id) {
		this.conceptProposalResponseId = id;
	}
	
	public String getProposedConceptUuid() {
		return proposedConceptUuid;
	}
	
	public void setProposedConceptUuid(final String proposedConceptUuid) {
		this.proposedConceptUuid = proposedConceptUuid;
	}
		
	public User getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(final User createdBy) {
		this.createdBy = createdBy;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(final Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public User getChangedBy() {
		return changedBy;
	}
	
	public void setChangedBy(final User changedBy) {
		this.changedBy = changedBy;
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
	
}
