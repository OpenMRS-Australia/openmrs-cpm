package org.openmrs.module.conceptpropose;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the base class underlying the exchange of information of aggregated Concept Proposals
 * between a Concept proposer, and a Concept Proposal reviewer. The attributes modelled in the
 * abstract class are the ones that will be exchanged between the two using transfer REST services
 */
@MappedSuperclass
public abstract class ShareablePackage<P extends ShareableProposal> extends BaseOpenmrsObject {

	protected Log log = LogFactory.getLog(getClass());

	private String name;

	private String email;

	private String description;

	protected Set<P> proposedConcepts = new HashSet<P>();

	private PackageStatus status;

	@Column(name = "uuid", unique = true, nullable = false, length = 38)
	@Override
	public String getUuid() {
		return super.getUuid();
	}
	
	@Column(nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(nullable = false)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Transient
	public abstract Set<P> getProposedConcepts();

	public void setProposedConcepts(Set<P> proposedConcepts) {
		this.proposedConcepts = proposedConcepts;
        for(P p : proposedConcepts) {
            p.setProposedConceptPackage(this);
        }
	}

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	public PackageStatus getStatus() {
		return status;
	}
	
	public void setStatus(PackageStatus status) {
		this.status = status;
	}
	
	/*
	 * Utility methods
	 */
	
	public void addProposedConcept(final P proposedConcept) {
		if (proposedConcept == null) {
			log.warn("Ignoring request to add null concept");
			return;
		}
		if (this.proposedConcepts != null) {
			log.debug("Adding concept: " + proposedConcept + " and adding: " + this + " as its parent");
			this.proposedConcepts.add(proposedConcept);
			proposedConcept.setProposedConceptPackage(this);
		} else {
			log.warn("Cannot add concept: " + proposedConcept + " to null concept list");
		}
	}
	
	public void removeProposedConcept(final P proposedConcept) {
		if (proposedConcept == null) {
			log.warn("Ignoring request to remove null concept");
			return;
		}
		if (this.proposedConcepts != null) {
			log.debug("Removing concept: " + proposedConcept);
			this.proposedConcepts.remove(proposedConcept);
		} else {
			log.warn("Cannot remove concept: " + proposedConcept + " to null concept list");
		}
	}

  public P getProposedConcept(final int proposedConceptId){
      for(P p : proposedConcepts){
          if(p.getId()!=null)  {
              if(p.getId() == proposedConceptId){
                  return p;
              }
          }
      }
      return null;
  }
	
}
