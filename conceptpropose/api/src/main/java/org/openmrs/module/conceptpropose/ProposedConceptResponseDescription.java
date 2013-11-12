package org.openmrs.module.conceptpropose;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Locale;

@Entity
@Table(name = "cpm_proposed_concept_response_description")
public class ProposedConceptResponseDescription implements Serializable {

	@Id
	@ManyToOne
	@JoinColumn(name = "proposed_concept_response")
	private ProposedConceptResponse proposedConceptResponse;

	@Id
	private String description;

	private Locale locale;

	public ProposedConceptResponse getProposedConceptResponse() {
		return proposedConceptResponse;
	}

	public void setProposedConceptResponse(ProposedConceptResponse proposedConceptResponse) {
		this.proposedConceptResponse = proposedConceptResponse;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}
