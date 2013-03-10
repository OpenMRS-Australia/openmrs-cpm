package org.openmrs.module.cpm.web.dto;

import java.util.ArrayList;

public class SubmissionDto {

	private String name;

	private String email;

	private String description;

	private ArrayList<ProposedConceptDto> concepts;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public ArrayList<ProposedConceptDto> getConcepts() {
		return concepts;
	}

	public void setConcepts(final ArrayList<ProposedConceptDto> concepts) {
		this.concepts = concepts;
	}
}
