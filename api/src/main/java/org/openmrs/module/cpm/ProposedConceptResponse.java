package org.openmrs.module.cpm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.GenericGenerator;
import org.openmrs.Concept;

/**
 * This extends the SharedProposal to represent a persisted Concept Proposal on the reviewers side
 * of the Concept Proposal exchange. This will be persisted on the reviewer instance of OpenMRS, by
 * server side REST services. Given that the ids on both the client and server sides will be
 * different, the underlying UUID will be used to test for equality.
 * 
 * @see ConceptProposalReview
 */
@Entity
@Table(name = "cpm_proposed_concept_response")
public class ProposedConceptResponse extends ShareableProposal<ProposedConceptResponsePackage> {
	
	@Transient
	private static Log log = LogFactory.getLog(ProposedConceptResponse.class);
	
	private Integer proposedConceptResponseId;
	private String proposedConceptUuid;
	private Integer version;
	private String name;
	private String description;

	public ProposedConceptResponse() {
		super();
		this.version = 0;
	}
	
	@Id
	@GeneratedValue(generator = "nativeIfNotAssigned")
	@GenericGenerator(name = "nativeIfNotAssigned", strategy = "org.openmrs.api.db.hibernate.NativeIfNotAssignedIdentityGenerator")
	@Column(name = "cpm_proposed_concept_response_id")
 	public Integer getId() {
		return proposedConceptResponseId;
	}
	
	public void setId(final Integer id) {
		this.proposedConceptResponseId = id;
	}

	@Column(name = "cpm_proposed_concept_uuid")
	public String getProposedConceptUuid() {
		return proposedConceptUuid;
	}
	
	public void setProposedConceptUuid(final String proposedConceptUuid) {
		this.proposedConceptUuid = proposedConceptUuid;
	}
		
	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(final Integer version) {
		this.version = version;
	}

	@ManyToOne
	@JoinColumn(name = "cpm_proposed_concept_response_package_id", nullable = false)
	@Override
	public ProposedConceptResponsePackage getProposedConceptPackage() {
		return proposedConceptPackage;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@ManyToOne
	@JoinColumn(name = "concept_id")
	public Concept getConcept() {
		return concept;
	}
}
