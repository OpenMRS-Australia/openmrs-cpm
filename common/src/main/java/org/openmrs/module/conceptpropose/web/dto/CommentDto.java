package org.openmrs.module.conceptpropose.web.dto;

public class CommentDto {

	private String name;

	private String email;

	private String comment;

	private String proposedConceptPackageUuid;

	private String proposedConceptUuid;

	public String getProposedConceptPackageUuid() {
		return proposedConceptPackageUuid;
	}

	public void setProposedConceptPackageUuid(String proposedConceptPackageUuid) {
		this.proposedConceptPackageUuid = proposedConceptPackageUuid;
	}
	public String getProposedConceptUuid() {
		return proposedConceptUuid;
	}
	public void setProposedConceptUuid(String proposedConceptUuid) {
		this.proposedConceptUuid = proposedConceptUuid;
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

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "SubmissionDto{" +
				"comment='" + comment+ '\'' +
				", email='" + email + '\'' +
				", name='" + name + '\'' +
				", package='" + proposedConceptPackageUuid + '\'' +
				", concept='" + proposedConceptUuid + '\'' +
				'}';
	}
}