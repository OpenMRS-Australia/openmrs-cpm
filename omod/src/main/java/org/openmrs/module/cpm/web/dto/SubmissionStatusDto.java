package org.openmrs.module.cpm.web.dto;

import org.openmrs.module.cpm.PackageStatus;

public class SubmissionStatusDto implements Dto {

	private PackageStatus status;

	public SubmissionStatusDto() {}

	public SubmissionStatusDto(final PackageStatus status) {
		this.status = status;
	}

	public PackageStatus getStatus() {
		return status;
	}

	public void setStatus(final PackageStatus status) {
		this.status = status;
	}
}
