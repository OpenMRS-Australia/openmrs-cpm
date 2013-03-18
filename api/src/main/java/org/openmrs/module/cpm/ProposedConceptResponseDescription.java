package org.openmrs.module.cpm;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Locale;

@Entity
public class ProposedConceptResponseDescription implements Serializable {

	@Id
	@ManyToOne
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
