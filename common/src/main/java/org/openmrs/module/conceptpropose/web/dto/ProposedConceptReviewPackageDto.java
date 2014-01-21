package org.openmrs.module.conceptpropose.web.dto;

import java.util.List;

public class ProposedConceptReviewPackageDto {

	private int id;

	private String name;

	private String age;

	private String email;

	private String description;

	private List<ProposedConceptReviewDto> concepts;

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

	public String getAge() {
		return age;
	}

	public void setAge(final String age) {
		this.age = age;
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

	public List<ProposedConceptReviewDto> getConcepts() {
		return concepts;
	}

	public void setConcepts(final List<ProposedConceptReviewDto> concepts) {
		this.concepts = concepts;
	}
}
