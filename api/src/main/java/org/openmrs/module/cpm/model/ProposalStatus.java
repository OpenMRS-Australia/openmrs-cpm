package org.openmrs.module.cpm.model;

public enum ProposalStatus {

	DRAFT ("Draft"),
	SUBMITTED ("Submitted");

	private String value;

	ProposalStatus(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public static ProposalStatus fromString(final String value) {
		if (value != null) {
			for (final ProposalStatus status : ProposalStatus.values()) {
				if (value.equals(status.toString())) {
					return status;
				}
			}
		}
		return null;
	}
}
