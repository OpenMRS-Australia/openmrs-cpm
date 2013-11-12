package org.openmrs.module.conceptpropose;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Concept;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * This is the base class underlying the exchange of information of individual Concept Proposals
 * between a Concept proposer, and a Concept Proposal reviewer. The attributes modelled in the
 * abstract class are the ones that will be exchanged between the two using transfer REST services
 */
@MappedSuperclass
public abstract class ShareableProposal<P extends ShareablePackage> extends BaseOpenmrsObject {

	private static Log log = LogFactory.getLog(ShareableProposal.class);

	protected P proposedConceptPackage;
	protected Concept concept;
    private  String comment;
	private ProposalStatus status;

	@Transient
    public abstract Integer getId();
    public abstract void setId(Integer id);

	@Column(name = "uuid", unique = true, nullable = false, length = 38)
	@Override
	public String getUuid() {
		return super.getUuid();
	}

    @Column(name = "comment", nullable = true)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Transient
	public abstract P getProposedConceptPackage();

	public void setConcept(final Concept concept) {
		this.concept = concept;
	}

	public void setProposedConceptPackage(final P proposedConceptPackage) {
		this.proposedConceptPackage = proposedConceptPackage;
	}

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
    public ProposalStatus getStatus() {
    	return status;
    }
	
    public void setStatus(final ProposalStatus status) {
    	this.status = status;
    }


}
