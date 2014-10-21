package org.openmrs.module.conceptreview.api;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.conceptreview.ConceptReviewConsts;
import org.openmrs.module.conceptreview.ProposedConceptReviewPackage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Contains methods exposing the core functionality. It is an API that can be used outside of the
 * module. This is used in conjunction with the Core OpenMRS API for adding/saving the individual
 * ProposedConcept/ProposedConceptReview line items that are contained within packages.
 * <p>
 * Usage example:<br>
 * <code>
 * Context.getService(ProposedConceptService.class).getAllConceptProposalPackages();
 * 
 * @see org.openmrs.api.context.Context
 * @see org.openmrs.api.ConceptService
 */
@SuppressWarnings("JavadocReference")
public interface ProposedConceptReviewService extends OpenmrsService {
	
	/**
	 * Gets a list of all of the proposals that have been received on this OpenMRS instance. For
	 * instances acting in the role of clients this will return all of the packages that have been
	 * created for submissions. For instances acting in the role of servers this will return all of
	 * the packages that have been received via the REST services
	 *
	 * @return A list of ConceptProposalPackages that may be in a variety of states
	 * @throws org.openmrs.api.APIException
	 * @since 1.0
	 */
	@Authorized(ConceptReviewConsts.MODULE_PRIVILEGE)
	@Transactional
	List<ProposedConceptReviewPackage> getAllProposedConceptReviewPackages() throws APIException;

	/**
	 * Gets a specific ProposedConceptPackage by its id (id field in the class,
	 * concept_proposal_package_id in the database)
	 *
	 * @return The ConceptProposalPackages corresponding to the id parameter if it is available,
	 *         otherwise a null result
	 * @throws org.openmrs.api.APIException
	 * @since 1.0
	 */
	@Authorized(ConceptReviewConsts.MODULE_PRIVILEGE)
	@Transactional
	ProposedConceptReviewPackage getProposedConceptReviewPackageById(Integer id) throws APIException;

	/**
	 * Gets a specific ProposedConceptPackage by its uuid (uuid field in the class, uuid in the
	 * database)
	 *
	 * @return The ConceptProposalPackages corresponding to the uuid parameter if it is available,
	 *         otherwise a null result
	 * @throws org.openmrs.api.APIException
	 * @since 1.0
	 */
	@Authorized(ConceptReviewConsts.MODULE_PRIVILEGE)
	@Transactional
	ProposedConceptReviewPackage getProposedConceptReviewPackageByProposalUuid(String uuid) throws APIException;

	/**
	 * Saves a ProposedConceptPackage including references to all of the individual
	 * ConceptProposals. For instances acting in the role of clients this can be used to save a
	 * draft concept proposal before sending for submission. For instances acting in the role of
	 * servers this can be used to save comments or status updates at the package level
	 *
	 * @param The ProposedConceptPackage to be created or updated
	 * @return The updated ProposedConceptPackage - including the id assigned to it during storage
	 * @throws org.openmrs.api.APIException
	 * @since 1.0
	 */
	@Authorized(ConceptReviewConsts.MODULE_PRIVILEGE)
	@Transactional
	ProposedConceptReviewPackage saveProposedConceptReviewPackage(ProposedConceptReviewPackage conceptPackage) throws APIException;

	/**
	 * Deletes a ProposedConceptPackage including references to all of the individual
	 * ConceptProposals - but does not delete the individual ConceptProposals themselves.
	 *
	 * @param The ProposedConceptPackage to be removed from persistent storage
	 * @throws org.openmrs.api.APIException
	 * @since 1.0
	 */
	@Authorized(ConceptReviewConsts.MODULE_PRIVILEGE)
	@Transactional
	void deleteProposedConceptReviewPackage(ProposedConceptReviewPackage conceptPackage) throws APIException;

	@Authorized(ConceptReviewConsts.MODULE_PRIVILEGE)
	@Transactional
	void deleteProposedConceptReviewPackageById(int proposalId) throws APIException;
}
