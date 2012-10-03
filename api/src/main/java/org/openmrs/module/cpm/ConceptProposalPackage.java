package org.openmrs.module.cpm;

import java.util.Date;
import java.util.Set;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.User;

/**
 * This class represents a set of Concepts that has been proposed as a single group.  It acts as a wrapper for 
 * individual concept proposals - providing attributes that record the proposers rationale for why the concepts
 * are needed, and allowing the proposal reviewer to manage a master/detail style listing of the overall proposal
 * package and its individual concepts.
 */
public class ConceptProposalPackage extends BaseOpenmrsObject {
	
	private Integer conceptProposalPackageId;
	private String name;
	private String description;
	private User creator;
	private Date dateCreated;
	private User changedBy;
	private Date dateChanged;
	private Set<ConceptProposal> proposedConcepts;
		
	@Override
	public Integer getId() {
		return this.conceptProposalPackageId;
	}
	
	@Override
	public void setId(Integer id) {
		this.conceptProposalPackageId = id;
	}

	
    public Integer getConceptProposalPackageId() {
    	return conceptProposalPackageId;
    }

	
    public void setConceptProposalPackageId(Integer conceptProposalPackageId) {
    	this.conceptProposalPackageId = conceptProposalPackageId;
    }

	
    public String getName() {
    	return name;
    }

	
    public void setName(String name) {
    	this.name = name;
    }

	
    public Date getDateCreated() {
    	return dateCreated;
    }

	
    public void setDateCreated(Date dateCreated) {
    	this.dateCreated = dateCreated;
    }

	
    public Date getDateChanged() {
    	return dateChanged;
    }

	
    public void setDateChanged(Date dateChanged) {
    	this.dateChanged = dateChanged;
    }

	
    public User getCreator() {
    	return creator;
    }

	
    public void setCreator(User creator) {
    	this.creator = creator;
    }

	
    public User getChangedBy() {
    	return changedBy;
    }

	
    public void setChangedBy(User changedBy) {
    	this.changedBy = changedBy;
    }

	
    public String getDescription() {
    	return description;
    }

	
    public void setDescription(String description) {
    	this.description = description;
    }

	
    public Set<ConceptProposal> getProposedConcepts() {
    	return proposedConcepts;
    }

	
    public void setProposedConcepts(Set<ConceptProposal> proposedConcepts) {
    	this.proposedConcepts = proposedConcepts;
    }
	
}
