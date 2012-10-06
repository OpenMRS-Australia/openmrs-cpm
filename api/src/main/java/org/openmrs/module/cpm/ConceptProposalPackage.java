package org.openmrs.module.cpm;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.ConceptProposal;
import org.openmrs.User;

/**
 * This class represents a set of Concepts that has been proposed as a single group.  It acts as a wrapper for 
 * individual concept proposals - providing attributes that record the proposers rationale for why the concepts
 * are needed, and allowing the proposal reviewer to manage a master/detail style listing of the overall proposal
 * package and its individual concepts.
 */
public class ConceptProposalPackage extends BaseOpenmrsObject {
	
	protected Log log = LogFactory.getLog(getClass());

	private Integer conceptProposalPackageId;
	private String name;
	private String description;
	private User creator;
	private Date dateCreated;
	private User changedBy;
	private Date dateChanged;
	private Integer version;
	private Set<ConceptProposal> proposedConcepts;
	private ConceptProposalPackageStatus status;
	
	/* 
	 * Constructs
	 */
	
	public ConceptProposalPackage() {
		log.debug("Creating a ConceptProposalPackage");
	}
	
	/*
	 * Persisted field getters/setters
	 */
	@Override
	public Integer getId() {
		return this.conceptProposalPackageId;
	}
	
	@Override
	public void setId(Integer id) {
		this.conceptProposalPackageId = id;
	}

	@Column(name="name", nullable=false, length=255)
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
	
    
    public Integer getVersion() {
    	return version;
    }

	
    public void setVersion(Integer version) {
    	this.version = version;
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
	
    public ConceptProposalPackageStatus getStatus() {
    	return status;
    }
	
    public void setStatus(ConceptProposalPackageStatus status) {
    	this.status = status;
    }

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
    	name="cpm_concept_proposal_package_proposals",
    	joinColumns = { @JoinColumn(name="concept_proposal_package_id") },
    	inverseJoinColumns = { @JoinColumn(name="concept_proposal_id") }
    )
	public Set<ConceptProposal> getProposedConcepts() {
    	return proposedConcepts;
    }

	
    public void setProposedConcepts(Set<ConceptProposal> proposedConcepts) {
    	this.proposedConcepts = proposedConcepts;
    }
	
	/*
	 * Persisted field utility methods
	 */
    
    public void addProposedConcept(ConceptProposal proposedConcept) {
    	if (proposedConcept == null) {
    		log.warn("Ignoring request to add null proposedConcept");
    		return;
    	}
    	if (this.proposedConcepts != null) {
    		log.debug("Adding proposed concept: " + proposedConcept);
    		this.proposedConcepts.add(proposedConcept);
    	} else {
    		log.warn("Cannot add proposed concept: " + proposedConcept + " to null proposedConcept set");
    	}
    }

    public void removeProposedConcept(ConceptProposal proposedConcept) {
    	if (proposedConcept == null) {
    		log.warn("Ignoring request to remove null proposedConcept");
    		return;
    	}
    	if (this.proposedConcepts != null) {
    		log.debug("Removing proposed concept: " + proposedConcept);
    		this.proposedConcepts.add(proposedConcept);
    	} else {
    		log.warn("Cannot remove proposed concept: " + proposedConcept + " to null proposedConcept set");
    	}
    }

}
