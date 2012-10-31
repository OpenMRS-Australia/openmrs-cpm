package org.openmrs.module.cpm;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;

/**
 * This class represents a set of Concepts that has been proposed as a single group.  It acts as a wrapper for
 * individual concept proposals - providing attributes that record the proposers rationale for why the concepts
 * are needed, and allowing the proposal reviewer to manage a master/detail style listing of the overall proposal
 * package and its individual concepts.
 */
public class ProposedConceptPackage extends ShareablePackage<ProposedConcept> {

	private static Log log = LogFactory.getLog(ProposedConceptPackage.class);

	private Integer conceptProposalPackageId;
	private User createdBy;
	private Date dateCreated;
	private User changedBy;
	private Date dateChanged;
	private Integer version;

	/*
	 * Constructors
	 */

	public ProposedConceptPackage() {
		super();
		log.debug("Creating a ProposedConceptPackage");
		
		this.dateCreated = new Date();
		this.dateChanged = new Date();
		this.version = 0;
	}

	/*
	 * Persisted field getters/setters
	 */
	@Override
	public Integer getId() {
		return conceptProposalPackageId;
	}

	@Override
	public void setId(final Integer id) {
		conceptProposalPackageId = id;
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
    	final StringBuffer appender = new StringBuffer();
    	appender.append("ProposedConceptPackage(");
    	appender.append(getId() + "," + getEmail());
    	appender.append(")");
    	appender.append(super.toString());
    	return appender.toString();
    }

}
