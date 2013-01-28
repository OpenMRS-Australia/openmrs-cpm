package org.openmrs.module.cpm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Auditable;
import org.openmrs.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a set of Concepts that has been proposed as a single group.  It acts as a wrapper for
 * individual concept proposals - providing attributes that record the proposers rationale for why the concepts
 * are needed, and allowing the proposal reviewer to manage a master/detail style listing of the overall proposal
 * package and its individual concepts.
 */
@Entity
@Table(name = "cpm_proposed_concept_response_package")
public class ProposedConceptResponsePackage extends ShareablePackage<ProposedConceptResponse> implements Auditable {

	private static Log log = LogFactory.getLog(ProposedConceptResponsePackage.class);

	private Integer proposedConceptResponsePackageId;

	private String proposedConceptPackageUuid;

	private User creator;

	private Date dateCreated;

	private User changedBy;

	private Date dateChanged;

	private Integer version;
	
	public ProposedConceptResponsePackage() {
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

		setName(shareablePackage.getName());
		setEmail(shareablePackage.getEmail());
		setDescription(shareablePackage.getDescription());
		setProposedConceptPackageUuid(shareablePackage.getUuid());
		setProposedConcepts(new HashSet<ProposedConceptResponse>());

		if (shareablePackage.getProposedConcepts() != null) {
			// For each of the proposals in the submitted ProposedConceptPackage we create and equivalent
			// response item that will allow us to record additional details

			for (final ProposedConcept currentProposal : shareablePackage.getProposedConcepts()) {
				final ProposedConceptResponse proposalResponse  = new ProposedConceptResponse(currentProposal);
				addProposedConcept(proposalResponse);
			}
		}

		setStatus(PackageStatus.RECEIVED);
		setVersion(0);
	}

	@Id
	@Column(nullable = false)
	@Override
	public Integer getId() {
		return proposedConceptResponsePackageId;
	}

	@Override
	public void setId(final Integer id) {
		proposedConceptResponsePackageId = id;
	}


    public String getProposedConceptPackageUuid() {
    	return proposedConceptPackageUuid;
    }

    public void setProposedConceptPackageUuid(final String proposedConceptPackageUuid) {
    	this.proposedConceptPackageUuid = proposedConceptPackageUuid;
    }

	@Column(nullable = false)
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
	@Override
	public User getCreator() {
    	return creator;
    }

    @Override
	public void setCreator(final User creator) {
    	this.creator = creator;
    }

	@ManyToOne
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
	public Set<ProposedConceptResponse> getProposedConcepts() {
		return proposedConcepts;
	}

	/*
	 * Utility methods
	 */

    @Override
    public String toString() {
    	final StringBuffer appender = new StringBuffer();
    	appender.append("ConceptReviewPackage(");
    	appender.append(getId());
    	appender.append(")");
    	appender.append(super.toString());
    	return appender.toString();
    }
}
