package org.openmrs.module.conceptpropose.web.dto.concept;

import java.util.Collection;

public class ConceptDto {

	private int id;

	private Collection<NameDto> names;

	// for UI, not necessary for submission
	private String preferredName;

	private String datatype;

	private String conceptClass;

	private NumericDto numericDetails;

	private Collection<DescriptionDto> descriptions;

	private Collection<AnswerDto> answers;

	// for UI, not necessary for submission
	private String currLocaleDescription;

	private String uuid;

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public Collection<NameDto> getNames() {
		return names;
	}

	public void setNames(Collection<NameDto> names) {
		this.names = names;
	}

	public String getPreferredName() {
		return preferredName;
	}

	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(final String datatype) {
		this.datatype = datatype;
	}

	public String getConceptClass() {
		return conceptClass;
	}

	public void setConceptClass(String conceptClass) {
		this.conceptClass = conceptClass;
	}

	public NumericDto getNumericDetails() {
		return numericDetails;
	}

	public void setNumericDetails(NumericDto numericDetails) {
		this.numericDetails = numericDetails;
	}

	public Collection<DescriptionDto> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(Collection<DescriptionDto> descriptions) {
		this.descriptions = descriptions;
	}

	public Collection<AnswerDto> getAnswers() {
		return answers;
	}

	public void setAnswers(Collection<AnswerDto> answers) {
		this.answers = answers;
	}

	public String getCurrLocaleDescription() {
		return currLocaleDescription;
	}

	public void setCurrLocaleDescription(String currLocaleDescription) {
		this.currLocaleDescription = currLocaleDescription;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
