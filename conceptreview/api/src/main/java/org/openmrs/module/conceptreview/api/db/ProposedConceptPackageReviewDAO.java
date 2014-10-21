package org.openmrs.module.conceptreview.api.db;

import org.openmrs.api.APIException;
import org.openmrs.module.conceptreview.ProposedConceptReview;
import org.openmrs.module.conceptreview.ProposedConceptReviewPackage;

import java.util.List;

/**
 * Service interface for the ProposedConceptPackage service data access objects.  This interface is used by Spring to inject
 * transaction interceptors around the ProposedConceptPackageReviewDAO implementations
 */
public interface ProposedConceptPackageReviewDAO {
	
	List<ProposedConceptReviewPackage> getAllConceptProposalReviewPackages() throws APIException;
	
	ProposedConceptReviewPackage getConceptProposalReviewPackageById(Integer id) throws APIException;
	
	ProposedConceptReviewPackage getConceptProposalReviewPackageByProposalUuid(String uuid) throws APIException;
	
	ProposedConceptReviewPackage saveConceptProposalReviewPackage(ProposedConceptReviewPackage conceptPackage) throws APIException;
	
	void deleteConceptProposalReviewPackage(ProposedConceptReviewPackage conceptPackage) throws APIException;

	void deleteConceptProposalReviewPackageById(int proposalId);

	ProposedConceptReview getConceptProposalReviewBySourceProposalUuidAndSourceConceptUuid(String packageUuid, String conceptUuid) throws APIException;
}
