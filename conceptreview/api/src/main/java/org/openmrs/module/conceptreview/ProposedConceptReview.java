package org.openmrs.module.conceptreview;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.GenericGenerator;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.module.conceptpropose.ProposalStatus;
import org.openmrs.module.conceptpropose.ShareableProposal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * This extends the SharedProposal to represent a persisted Concept Proposal on the reviewers side
 * of the Concept Proposal exchange. This will be persisted on the reviewer instance of OpenMRS, by
 * server side REST services. Given that the ids on both the client and server sides will be
 * different, the underlying UUID will be used to test for equality.
 */
@Entity
@Table(name = "conceptreview_proposed_concept_review")
public class ProposedConceptReview extends ShareableProposal<ProposedConceptReviewPackage> {
	
	@Transient
	private static Log log = LogFactory.getLog(ProposedConceptReview.class);
	
	private Integer proposedConceptReviewId;
	private String proposedConceptUuid;
	private Integer version;
	private List<ProposedConceptReviewName> names;
	private List<ProposedConceptReviewDescription> descriptions;

	private ConceptDatatype datatype;
	private ConceptClass conceptClass;
	private ProposedConceptReviewNumeric numericDetails;

	private String reviewComment;
	private List<ProposedConceptReviewAnswer> codedDetails;

	public ProposedConceptReview() {
		super();
		this.version = 0;
		setStatus(ProposalStatus.RECEIVED);
	}
	
	@Id
	@GeneratedValue(generator = "nativeIfNotAssigned")
	@GenericGenerator(name = "nativeIfNotAssigned", strategy = "org.openmrs.api.db.hibernate.NativeIfNotAssignedIdentityGenerator")
	@Column(name = "conceptreview_proposed_concept_review_id")
 	public Integer getId() {
		return proposedConceptReviewId;
	}
	
	public void setId(final Integer id) {
		this.proposedConceptReviewId = id;
	}

	@Column(name = "conceptreview_proposed_concept_uuid")
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
	@JoinColumn(name = "conceptreview_proposed_concept_review_package_id", nullable = false)
	@Override
	public ProposedConceptReviewPackage getProposedConceptPackage() {
		return proposedConceptPackage;
	}

	@OneToMany(mappedBy = "proposedConceptReview", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<ProposedConceptReviewName> getNames() {
		return names;
	}

	public void setNames(final List<ProposedConceptReviewName> names) {
		this.names = names;
		if (this.names != null) {
			for (ProposedConceptReviewName name: this.names) {
				name.setProposedConceptReview(this);
			}
		}
	}

	@OneToMany(mappedBy = "proposedConceptReview", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<ProposedConceptReviewDescription> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(final List<ProposedConceptReviewDescription> descriptions) {
		this.descriptions = descriptions;
		if (this.descriptions != null) {
			for (ProposedConceptReviewDescription name: this.descriptions) {
				name.setProposedConceptReview(this);
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

	public void setNumericDetails(ProposedConceptReviewNumeric numericDetails) {
		this.numericDetails = numericDetails;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "conceptreview_proposed_concept_review_numeric_id")
	public ProposedConceptReviewNumeric getNumericDetails() {
		return numericDetails;
	}

	public String getReviewComment() {
		return reviewComment;
	}

	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}

	public void setCodedDetails(List<ProposedConceptReviewAnswer> codedDetails) {
		this.codedDetails = codedDetails;
	}

	@OneToMany(mappedBy = "proposedConceptReview", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<ProposedConceptReviewAnswer> getCodedDetails() {
		return codedDetails;
	}
}
