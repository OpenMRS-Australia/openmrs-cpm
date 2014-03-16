package org.openmrs.module.conceptreview;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.GenericGenerator;
import org.openmrs.Auditable;
import org.openmrs.User;
import org.openmrs.module.conceptpropose.PackageStatus;
import org.openmrs.module.conceptpropose.ShareablePackage;

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
@Table(name = "conceptreview_proposed_concept_review_package")
public class ProposedConceptReviewPackage extends ShareablePackage<ProposedConceptReview> implements Auditable {

	private static Log log = LogFactory.getLog(ProposedConceptReviewPackage.class);

	private Integer proposedConceptReviewPackageId;

	private String proposedConceptPackageUuid;

	private User creator;

	private Date dateCreated;

	private User changedBy;

	private Date dateChanged;

	private Integer version;
	
	public ProposedConceptReviewPackage() {
		setStatus(PackageStatus.RECEIVED);
		dateCreated = new Date();
		dateChanged = new Date();
		version = 0;
	}

	@Id
	@GeneratedValue(generator = "nativeIfNotAssigned")
	@GenericGenerator(name = "nativeIfNotAssigned", strategy = "org.openmrs.api.db.hibernate.NativeIfNotAssignedIdentityGenerator")
	@Column(name = "conceptreview_proposed_concept_review_package_id", nullable = false)
	@Override
	public Integer getId() {
		return proposedConceptReviewPackageId;
	}

	@Override
	public void setId(final Integer id) {
		proposedConceptReviewPackageId = id;
	}

	@Column(name = "conceptreview_proposed_concept_package_uuid")
    public String getProposedConceptPackageUuid() {
    	return proposedConceptPackageUuid;
    }

    public void setProposedConceptPackageUuid(final String proposedConceptPackageUuid) {
    	this.proposedConceptPackageUuid = proposedConceptPackageUuid;
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

	@Column(name = "date_changed")
	@Temporal(TemporalType.DATE)
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
	public Set<ProposedConceptReview> getProposedConcepts() {
		return proposedConcepts;
	}

	/*
	 * Utility methods
	 */

    @Override
    public String toString() {
    	final StringBuilder appender = new StringBuilder();
    	appender.append("ConceptReviewPackage (proposedConceptReviewPackageId:");
    	appender.append(proposedConceptReviewPackageId);
	    appender.append(", proposedConceptPackageUuid:");
	    appender.append(proposedConceptPackageUuid);
	    appender.append(")");
	    appender.append(super.toString());
    	return appender.toString();
    }
}
