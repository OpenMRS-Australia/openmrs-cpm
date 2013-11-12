package org.openmrs.module.conceptpropose;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.GenericGenerator;
import org.openmrs.Auditable;
import org.openmrs.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Set;

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

	private Integer conceptProposalPackageId;
	private User creator;
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

		setStatus(PackageStatus.DRAFT);
		dateCreated = new Date();
		dateChanged = new Date();
		version = 0;
	}

	/*
	 * Persisted field getters/setters
	 */

	@Id
	@GeneratedValue(generator = "nativeIfNotAssigned")
	@GenericGenerator(name = "nativeIfNotAssigned", strategy = "org.openmrs.api.db.hibernate.NativeIfNotAssignedIdentityGenerator")
	@Column(name = "cpm_proposed_concept_package_id", nullable = false)
	@Override
	public Integer getId() {
		return conceptProposalPackageId;
	}

	@Override
	public void setId(final Integer id) {
		conceptProposalPackageId = id;
	}

	@Column(name = "date_created", nullable = false)
	@Temporal(TemporalType.DATE)
	@Override
	public Date getDateCreated() {
    	return dateCreated;
    }

    @Override
	public void setDateCreated(final Date dateCreated) {
    	this.dateCreated = dateCreated;
    }

	@Temporal(TemporalType.DATE)
	@Column(name = "date_changed")
    @Override
	public Date getDateChanged() {
    	return dateChanged;
    }

    @Override
	public void setDateChanged(final Date dateChanged) {
    	this.dateChanged = dateChanged;
    }

	@Column(nullable = false)
    public Integer getVersion() {
    	return version;
    }


    public void setVersion(final Integer version) {
    	this.version = version;
    }

	@ManyToOne
	@JoinColumn(name = "creator")
	@Override
	public User getCreator() {
    	return creator;
    }

    @Override
	public void setCreator(final User creator) {
    	this.creator = creator;
    }

	@ManyToOne
	@JoinColumn(name = "changedBy")
    @Override
	public User getChangedBy() {
    	return changedBy;
    }

    @Override
	public void setChangedBy(final User changedBy) {
    	this.changedBy = changedBy;
    }

	@OneToMany(mappedBy = "proposedConceptPackage", cascade = CascadeType.ALL, orphanRemoval = true)
	@Override
	public Set<ProposedConcept> getProposedConcepts() {
		return proposedConcepts;
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
