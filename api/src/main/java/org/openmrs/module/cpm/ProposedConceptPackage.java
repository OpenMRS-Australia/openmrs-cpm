package org.openmrs.module.cpm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Auditable;
import org.openmrs.User;

/**
 * This class represents a set of Concepts that has been proposed as a single group.  It acts as a wrapper for
 * individual concept proposals - providing attributes that record the proposers rationale for why the concepts
 * are needed, and allowing the proposal reviewer to manage a master/detail style listing of the overall proposal
 * package and its individual concepts.
 */
@Entity
@Table(name = "cpm_proposed_concept_package")
public class ProposedConceptPackage extends ShareablePackage<ProposedConcept> implements Auditable {

	private static Log log = LogFactory.getLog(ProposedConceptPackage.class);

	@Id
	@Column(name = "cpm_proposed_concept_package_id", nullable = false)
	private Integer conceptProposalPackageId;

	@ManyToOne
	@JoinColumn(name = "creator")
	private OurUser creator;

	@Column(name = "date_created", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@ManyToOne
	@JoinColumn(name = "changedBy")
	private OurUser changedBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "date_changed")
	private Date dateChanged;

	@Column(nullable = false)
	private Integer version;

	/*
	 * Constructors
	 */

	public ProposedConceptPackage() {
		super();
		log.debug("Creating a ProposedConceptPackage");

		dateCreated = new Date();
		dateChanged = new Date();
		version = 0;
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

	@Override
	public Date getDateCreated() {
    	return dateCreated;
    }

    @Override
	public void setDateCreated(final Date dateCreated) {
    	this.dateCreated = dateCreated;
    }

    @Override
	public Date getDateChanged() {
    	return dateChanged;
    }

    @Override
	public void setDateChanged(final Date dateChanged) {
    	this.dateChanged = dateChanged;
    }


    public Integer getVersion() {
    	return version;
    }


    public void setVersion(final Integer version) {
    	this.version = version;
    }

	@Override
	public OurUser getCreator() {
    	return creator;
    }

    @Override
	public void setCreator(final User creator) {
    	this.creator = (OurUser) creator;
    }

    @Override
	public OurUser getChangedBy() {
    	return changedBy;
    }

    @Override
	public void setChangedBy(final User changedBy) {
    	this.changedBy = (OurUser) changedBy;
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
