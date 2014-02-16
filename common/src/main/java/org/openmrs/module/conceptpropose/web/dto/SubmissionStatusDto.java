package org.openmrs.module.conceptpropose.web.dto;

import org.openmrs.module.conceptpropose.PackageStatus;

public class SubmissionStatusDto {

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
