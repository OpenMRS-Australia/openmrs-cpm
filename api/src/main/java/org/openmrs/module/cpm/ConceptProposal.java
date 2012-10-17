package org.openmrs.module.cpm;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;

/**
 * This extends the SharedProposal to represent a persisted Concept Proposal on the proposers side
 * of the Concept Proposal exchange. This will be persisted on the proposers instance of OpenMRS,
 * and it will use REST services to submit this to the server, and to subsequently check for
 * updates. Given that the ids on both the client and server sides will be different, the underlying
 * UUID will be used to test for equality
 * 
 * @see ConceptProposalReview
 */
public class ConceptProposal extends ShareableProposal {
	
	private static Log log = LogFactory.getLog(ConceptProposal.class);
	
	private Integer conceptProposalId;
	private User createdBy;
	private Date dateCreated;
	private User changedBy;
	private Date dateChanged;
	private Integer version;
	
	public ConceptProposal() {
		this.dateCreated = new Date();
		this.dateChanged = new Date();
		this.version = 0;
	}
	
	public Integer getId() {
		return conceptProposalId;
	}
	
	public void setId(Integer id) {
		this.conceptProposalId = id;
	}
		
	public User getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public User getChangedBy() {
		return changedBy;
	}
	
	public void setChangedBy(User changedBy) {
		this.changedBy = changedBy;
	}
	
	public Date getDateChanged() {
		return dateChanged;
	}
	
	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}
	
	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}
	
}
