package org.openmrs.module.cpm.web.dto.concept;

import java.util.List;

public class ConceptDto {

	private int id;

	private List<NameDto> names;

	// for UI, not necessary for submission
	private String preferredName;

	private String datatype;

	private String conceptClass;

	private NumericDto numericDetails;

	private List<DescriptionDto> descriptions;

	// for UI, not necessary for submission
	private String currLocaleDescription;

	private String uuid;

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public List<NameDto> getNames() {
		return names;
	}

	public void setNames(List<NameDto> names) {
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

	public List<DescriptionDto> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<DescriptionDto> descriptions) {
		this.descriptions = descriptions;
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
