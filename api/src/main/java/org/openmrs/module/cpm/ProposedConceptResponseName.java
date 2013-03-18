package org.openmrs.module.cpm;

import org.openmrs.api.ConceptNameType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;

@Entity
public class ProposedConceptResponseName implements Serializable {

	@Id
	@ManyToOne
	private ProposedConceptResponse proposedConceptResponse;

	@Id
	private String name;

	@Enumerated(EnumType.STRING)
	private ConceptNameType type;

	private Locale locale;

	public ProposedConceptResponse getProposedConceptResponse() {
		return proposedConceptResponse;
	}

	public void setProposedConceptResponse(ProposedConceptResponse proposedConceptResponse) {
		this.proposedConceptResponse = proposedConceptResponse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ConceptNameType getType() {
		return type;
	}

	public void setType(ConceptNameType type) {
		this.type = type;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}
