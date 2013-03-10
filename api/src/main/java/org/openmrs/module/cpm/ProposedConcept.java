package org.openmrs.module.cpm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.GenericGenerator;
import org.openmrs.Concept;

/**
 * This extends the SharedProposal to represent a persisted Concept Proposal on the proposers side
 * of the Concept Proposal exchange. This will be persisted on the proposers instance of OpenMRS,
 * and it will use REST services to submit this to the server, and to subsequently check for
 * updates. Given that the ids on both the client and server sides will be different, the underlying
 * UUID will be used to test for equality
 * 
 * @see ConceptProposalReview
 */
@Entity
@Table(name = "cpm_proposed_concept")
public class ProposedConcept extends ShareableProposal<ProposedConceptPackage> {
	
	private static Log log = LogFactory.getLog(ProposedConcept.class);
	
	private Integer proposedConceptId;
	private Integer version;

	public ProposedConcept() {
		super();
		log.debug("Creating a ProposedConcept");
		this.version = 0;
	}
	
	@Id
	@GeneratedValue(generator = "nativeIfNotAssigned")
	@GenericGenerator(name = "nativeIfNotAssigned", strategy = "org.openmrs.api.db.hibernate.NativeIfNotAssignedIdentityGenerator")
	@Column(name = "cpm_proposed_concept_id")
	public Integer getId() {
		return proposedConceptId;
	}
	
	public void setId(final Integer id) {
		this.proposedConceptId = id;
	}

	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(final Integer version) {
		this.version = version;
	}

	@ManyToOne
	@JoinColumn(name = "cpm_proposed_concept_package_id", nullable = false)
	@Override
	public ProposedConceptPackage getProposedConceptPackage() {
		return proposedConceptPackage;
	}

	@ManyToOne
	@JoinColumn(name = "concept_id", nullable = false)
	public Concept getConcept() {
		return concept;
	}
}
