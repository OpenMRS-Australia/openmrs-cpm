package org.openmrs.module.cpm.model;

import org.openmrs.BaseOpenmrsObject;


public class Proposal extends BaseOpenmrsObject {

	private Integer id;

	private String email;

	private String description;

	private ProposalStatus status;

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

	public ProposalStatus getStatus() {
		return status;
	}

	public void setStatus(final ProposalStatus status) {
		this.status = status;
	}
}
