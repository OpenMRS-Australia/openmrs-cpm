package org.openmrs.module.conceptpropose.web.dto;

import java.util.List;

public class SubmissionDto {

	private String name;

	private String email;

	private String description;

	private String proposedConceptPackageUuid;


	private List<ProposedConceptDto> concepts;


	public String getProposedConceptPackageUuid() {
		return proposedConceptPackageUuid;
	}

	public void setProposedConceptPackageUuid(String proposedConceptPackageUuid) {
		this.proposedConceptPackageUuid = proposedConceptPackageUuid;
	}

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

	public List<ProposedConceptDto> getConcepts() {
		return concepts;
	}

	public void setConcepts(final List<ProposedConceptDto> concepts) {
		this.concepts = concepts;
	}

	@Override
	public String toString() {
		return "SubmissionDto{" +
				"description='" + description + '\'' +
				", email='" + email + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
