package org.openmrs.module.conceptpropose;

import javax.persistence.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.GenericGenerator;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;

import java.io.Serializable;
import java.util.List;

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
	private List<ProposedConceptResponseName> names;
	private List<ProposedConceptResponseDescription> descriptions;

	private ConceptDatatype datatype;
	private ConceptClass conceptClass;
	private ProposedConceptResponseNumeric numericDetails;

	private String reviewComment;
	private List<ProposedConceptResponseAnswer> codedDetails;

	public ProposedConceptResponse() {
		super();
		this.version = 0;
		setStatus(ProposalStatus.RECEIVED);
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

	@OneToMany(mappedBy = "proposedConceptResponse", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<ProposedConceptResponseName> getNames() {
		return names;
	}

	public void setNames(final List<ProposedConceptResponseName> names) {
		this.names = names;
		if (this.names != null) {
			for (ProposedConceptResponseName name: this.names) {
				name.setProposedConceptResponse(this);
			}
		}
	}

	@OneToMany(mappedBy = "proposedConceptResponse", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<ProposedConceptResponseDescription> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(final List<ProposedConceptResponseDescription> descriptions) {
		this.descriptions = descriptions;
		if (this.descriptions != null) {
			for (ProposedConceptResponseDescription name: this.descriptions) {
				name.setProposedConceptResponse(this);
			}
		}
	}

	@ManyToOne
	@JoinColumn(name = "concept_id")
	public Concept getConcept() {
		return concept;
	}

	public void setDatatype(ConceptDatatype datatype) {
		this.datatype = datatype;
	}

	@ManyToOne
	@JoinColumn(name = "datatype_id")
	public ConceptDatatype getDatatype() {
		return datatype;
	}

	public void setConceptClass(ConceptClass conceptClass) {
		this.conceptClass = conceptClass;
	}

	@ManyToOne
	@JoinColumn(name = "concept_class_id")
	public ConceptClass getConceptClass() {
		return conceptClass;
	}

	public void setNumericDetails(ProposedConceptResponseNumeric numericDetails) {
		this.numericDetails = numericDetails;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cpm_proposed_concept_response_numeric_id")
	public ProposedConceptResponseNumeric getNumericDetails() {
		return numericDetails;
	}

	public String getReviewComment() {
		return reviewComment;
	}

	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}

	public void setCodedDetails(List<ProposedConceptResponseAnswer> codedDetails) {
		this.codedDetails = codedDetails;
	}

	@OneToMany(mappedBy = "proposedConceptResponse", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<ProposedConceptResponseAnswer> getCodedDetails() {
		return codedDetails;
	}
}
