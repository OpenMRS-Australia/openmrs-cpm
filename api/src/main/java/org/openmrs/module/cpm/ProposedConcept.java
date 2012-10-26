package org.openmrs.module.cpm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This extends the SharedProposal to represent a persisted Concept Proposal on the proposers side
 * of the Concept Proposal exchange. This will be persisted on the proposers instance of OpenMRS,
 * and it will use REST services to submit this to the server, and to subsequently check for
 * updates. Given that the ids on both the client and server sides will be different, the underlying
 * UUID will be used to test for equality
 * 
 * @see ConceptProposalReview
 */
public class ProposedConcept extends ShareableProposal {
	
	private static Log log = LogFactory.getLog(ProposedConcept.class);
	
	private Integer proposedConceptId;
	private Integer version;
	
	public ProposedConcept() {
		super();
		log.debug("Creating a ProposedConcept");
		this.version = 0;
	}
	
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
	
}
