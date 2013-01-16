package org.openmrs.module.cpm.web.dto;

import org.openmrs.module.cpm.ProposalStatus;

public class ProposedConceptResponseDto {

	private int conceptId;

	private String name;

	private String className;

	private String datatype;

	private String comments;

	private ProposalStatus status;

	public int getConceptId() {
		return conceptId;
	}

	public void setConceptId(final int conceptId) {
		this.conceptId = conceptId;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(final String className) {
		this.className = className;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(final String datatype) {
		this.datatype = datatype;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public ProposalStatus getStatus() {
		return status;
	}

	public void setStatus(final ProposalStatus status) {
		this.status = status;
	}
}
