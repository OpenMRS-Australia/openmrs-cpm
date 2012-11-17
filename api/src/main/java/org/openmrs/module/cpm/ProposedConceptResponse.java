package org.openmrs.module.cpm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
public class ProposedConceptResponse extends ShareableProposal {
	
	@Transient
	private static Log log = LogFactory.getLog(ProposedConceptResponse.class);
	
	@Id
	@Column(name = "cpm_proposed_concept_response_id")
	private Integer proposedConceptResponseId;
	private String proposedConceptUuid;
	private Integer version;
	
	/**
	 * Private constructore for Hibernate
	 */
	protected ProposedConceptResponse() {
		super();
	}
	
	/**
	 * Create the server side Concept Proposal Response based on the proposer submitted Concept Proposal.
	 * This changes the status of the proposal to reflect that this is in the first state of the server 
	 * side workflow
	 * 
	 * @param shareableProposal The Concept Proposal submitted by a client side proposer
	 */
	public ProposedConceptResponse(ShareableProposal shareableProposal) {
		super();
		log.debug("Creating a new ProposedConceptResponse from: " + shareableProposal);
		
		this.setName(shareableProposal.getName());
		this.setDescription(shareableProposal.getDescription());
		this.setProposedConceptUuid(shareableProposal.getUuid());
		this.setConcept(shareableProposal.getConcept());
		this.setComments(shareableProposal.getComments());
		this.setStatus(ProposalStatus.RECEIVED);		
		this.version = 0;
	}
	
 	public Integer getId() {
		return proposedConceptResponseId;
	}
	
	public void setId(final Integer id) {
		this.proposedConceptResponseId = id;
	}
	
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
	
}
