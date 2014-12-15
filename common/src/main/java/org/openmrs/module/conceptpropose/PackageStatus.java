package org.openmrs.module.conceptpropose;


public enum PackageStatus {
	DRAFT,         // proposer status: when saved but not sent
	TBS,           // proposer status:  when proposer click's submit but yet to receive a response from server
	PENDING,
	SUBMITTED,     // proposer status: after submission
	RECEIVED,      // reviewer status: when proposal has been received
	CLOSED,        // proposer & reviewer status: when all concepts have been processed
	DELETED,       // proposer & reviewer status: when reviewer has marked it as deleted
	DOESNOTEXIST   // proposer status: when reviewer DB does not have proposer's proposal
}
