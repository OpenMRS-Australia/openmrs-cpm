package org.openmrs.module.cpm.model;

import org.openmrs.BaseOpenmrsObject;


//@Entity
public class Proposal extends BaseOpenmrsObject {

//	@Id
	private Integer id;

	private String email;

//	@Column(columnDefinition = "TEXT")
	private String description;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(final Integer id) {
		this.id = id;
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
}
