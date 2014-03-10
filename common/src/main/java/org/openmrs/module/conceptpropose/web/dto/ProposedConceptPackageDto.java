package org.openmrs.module.conceptpropose.web.dto;

import org.openmrs.module.conceptpropose.PackageStatus;

import java.util.List;

public class ProposedConceptPackageDto {

	private int id;

	private String name;

	private String email;

	private String description;

	private PackageStatus status;

	private List<ProposedConceptDto> concepts;

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
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

	public PackageStatus getStatus() {
		return status;
	}

	public void setStatus(final PackageStatus packageStatus) {
		status = packageStatus;
	}

	public List<ProposedConceptDto> getConcepts() {
		return concepts;
	}

	public void setConcepts(final List<ProposedConceptDto> concepts) {
		this.concepts = concepts;
	}
}
