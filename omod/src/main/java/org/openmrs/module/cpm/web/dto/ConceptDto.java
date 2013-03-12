package org.openmrs.module.cpm.web.dto;

public class ConceptDto {

	private int id;

	private String name;

	private String synonyms;

	private String datatype;

	private String description;

	private String uuid;

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

	public String getSynonyms() {
		return synonyms;
	}

	public void setSynonyms(final String synonyms) {
		this.synonyms = synonyms;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(final String datatype) {
		this.datatype = datatype;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
