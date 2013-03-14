package org.openmrs.module.cpm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Concept;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the base class underlying the exchange of information of individual Concept Proposals
 * between a Concept proposer, and a Concept Proposal reviewer. The attributes modelled in the
 * abstract class are the ones that will be exchanged between the two using transfer REST services
 */
@MappedSuperclass
public abstract class ShareableProposal<P extends ShareablePackage> extends BaseOpenmrsObject {

	private static Log log = LogFactory.getLog(ShareableProposal.class);

	protected P proposedConceptPackage;
    private String name;
    private String description;
	private Set<Comment> comments = new HashSet<Comment>();
	protected Concept concept;
	private ProposalStatus status = ProposalStatus.DRAFT;

	@Transient
    public abstract Integer getId();
    public abstract void setId(Integer id);

	@Column(name = "uuid", unique = true, nullable = false, length = 38)
	@Override
	public String getUuid() {
		return super.getUuid();
	}

	@Transient
	public abstract P getProposedConceptPackage();

	public void setConcept(final Concept concept) {
		this.concept = concept;
	}

	public void setProposedConceptPackage(final P proposedConceptPackage) {
		this.proposedConceptPackage = proposedConceptPackage;
	}
	
	@Transient
	public Set<Comment> getComments() {
    	return comments;
    }

    public void setComments(final Set<Comment> comments) {
    	this.comments = comments;
    }

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
    public ProposalStatus getStatus() {
    	return status;
    }
	
    public void setStatus(final ProposalStatus status) {
    	this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*
	 * Utility methods
	 */

    public void addComment(final Comment comment) {
		if (comment == null) {
			log.warn("Ignoring request to add null comment");
			return;
		}
		if (this.comments != null) {
			log.debug("Adding comment: " + comment);
			this.comments.add(comment);
		} else {
			log.warn("Cannot add comment: " + comment + " to null comment list");
		}
	}

	public void removeComment(final Comment comment) {
		if (comment == null) {
			log.warn("Ignoring request to remove null comment");
			return;
		}
		if (this.comments != null) {
			log.debug("Removing comment: " + comment);
			this.comments.add(comment);
		} else {
			log.warn("Cannot remove comment: " + comment + " to null comment list");
		}
	}

    @Override
    public String toString() {
        return "ShareableProposal{" +
                super.toString() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", proposedConceptPackage.uuid=" + (proposedConceptPackage == null? null:proposedConceptPackage.getUuid()) +
                ", concept=" + concept +
                ", comments=" + comments +
                ", status=" + status +
                '}';
    }
}
