package org.openmrs.module.cpm.web.dto.concept;

import org.openmrs.module.cpm.web.dto.Dto;

public class DescriptionDto implements Dto {

	private String description;

	private String locale;

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(final String locale) {
		this.locale = locale;
	}
}
