package org.openmrs.module.cpm.api;

import java.util.List;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.cpm.ConceptProposalConsts;
import org.openmrs.module.cpm.ConceptProposalPackage;
import org.openmrs.module.cpm.ConceptProposalPackageResponse;
import org.openmrs.module.cpm.ShareablePackage;
import org.springframework.transaction.annotation.Transactional;

/**
 * Contains methods exposing the core functionality. It is an API that can be used outside of the
 * module. This is used in conjunction with the Core OpenMRS API for adding/saving the individual
 * ConceptProposal/ConceptProposalResponse line items that are contained within packages.
 * <p>
 * Usage example:<br>
 * <code>
 * Context.getService(ConceptProposalService.class).getAllConceptProposalPackages();
 * 
 * @see org.openmrs.api.context.Context
 * @see org.openmrs.api.ConceptService
 */
public interface ConceptProposalService extends OpenmrsService {
	
	//	Starting with all of the services for the client side of the ConceptProposal module
	
	/**
	 * Gets a list of all of the proposals that have been proposed on this OpenMRS instance. For
	 * instances acting in the role of clients this will return all of the packages that have been
	 * created for submissions. For instances acting in the role of servers this will return all of
	 * the packages that have been received via the REST services
	 * 
	 * @return A list of ConceptProposalPackages that may be in a variety of states
	 * @throws APIException
	 * @since 1.0
	 */
	@Authorized(ConceptProposalConsts.MODULE_PRIVILEGE)
	@Transactional
	List<ConceptProposalPackage> getAllConceptProposalPackages() throws APIException;
	
	/**
	 * Gets a specific ConceptProposalPackage by its id (id field in the class,
	 * concept_proposal_package_id in the database)
	 * 
	 * @return The ConceptProposalPackages corresponding to the id parameter if it is available,
	 *         otherwise a null result
	 * @throws APIException
	 * @since 1.0
	 */
	@Authorized(ConceptProposalConsts.MODULE_PRIVILEGE)
	@Transactional
	ShareablePackage getConceptProposalPackageById(Integer id) throws APIException;
	
	/**
	 * Gets a specific ConceptProposalPackage by its uuid (uuid field in the class, uuid in the
	 * database)
	 * 
	 * @return The ConceptProposalPackages corresponding to the uuid parameter if it is available,
	 *         otherwise a null result
	 * @throws APIException
	 * @since 1.0
	 */
	@Authorized(ConceptProposalConsts.MODULE_PRIVILEGE)
	@Transactional
	ShareablePackage getConceptProposalPackageByUuid(String uuid) throws APIException;
	
	/**
	 * Saves a ConceptProposalPackage including references to all of the individual
	 * ConceptProposals. For instances acting in the role of clients this can be used to save a
	 * draft concept proposal before sending for submission. For instances acting in the role of
	 * servers this can be used to save comments or status updates at the package level
	 * 
	 * @param The ConceptProposalPackage to be created or updated
	 * @return The updated ConceptProposalPackage - including the id assigned to it during storage
	 * @throws APIException
	 * @since 1.0
	 */
	@Authorized(ConceptProposalConsts.MODULE_PRIVILEGE)
	@Transactional
	ShareablePackage saveConceptProposalPackage(ConceptProposalPackage conceptPackage) throws APIException;
	
	/**
	 * Deletes a ConceptProposalPackage including references to all of the individual
	 * ConceptProposals - but does not delete the individual ConceptProposals themselves.
	 * 
	 * @param The ConceptProposalPackage to be removed from persistent storage
	 * @throws APIException
	 * @since 1.0
	 */
	@Authorized(ConceptProposalConsts.MODULE_PRIVILEGE)
	@Transactional
	void deleteConceptProposalPackage(ConceptProposalPackage conceptPackage) throws APIException;
	
	//	Moving on to all of the services for the server side of the Concept Proposal Module
	
	/**
	 * Gets a list of all of the proposals that have been received on this OpenMRS instance. For
	 * instances acting in the role of clients this will return all of the packages that have been
	 * created for submissions. For instances acting in the role of servers this will return all of
	 * the packages that have been received via the REST services
	 * 
	 * @return A list of ConceptProposalPackages that may be in a variety of states
	 * @throws APIException
	 * @since 1.0
	 */
	@Authorized(ConceptProposalConsts.MODULE_PRIVILEGE)
	@Transactional
	List<ConceptProposalPackageResponse> getAllConceptProposalPackageResponses() throws APIException;
	
	/**
	 * Gets a specific ConceptProposalPackage by its id (id field in the class,
	 * concept_proposal_package_id in the database)
	 * 
	 * @return The ConceptProposalPackages corresponding to the id parameter if it is available,
	 *         otherwise a null result
	 * @throws APIException
	 * @since 1.0
	 */
	@Authorized(ConceptProposalConsts.MODULE_PRIVILEGE)
	@Transactional
	ShareablePackage getConceptProposalPackageResponseById(Integer id) throws APIException;
	
	/**
	 * Gets a specific ConceptProposalPackage by its uuid (uuid field in the class, uuid in the
	 * database)
	 * 
	 * @return The ConceptProposalPackages corresponding to the uuid parameter if it is available,
	 *         otherwise a null result
	 * @throws APIException
	 * @since 1.0
	 */
	@Authorized(ConceptProposalConsts.MODULE_PRIVILEGE)
	@Transactional
	ShareablePackage getConceptProposalPackageResponseByProposalUuid(String uuid) throws APIException;
	
	/**
	 * Saves a ConceptProposalPackage including references to all of the individual
	 * ConceptProposals. For instances acting in the role of clients this can be used to save a
	 * draft concept proposal before sending for submission. For instances acting in the role of
	 * servers this can be used to save comments or status updates at the package level
	 * 
	 * @param The ConceptProposalPackage to be created or updated
	 * @return The updated ConceptProposalPackage - including the id assigned to it during storage
	 * @throws APIException
	 * @since 1.0
	 */
	@Authorized(ConceptProposalConsts.MODULE_PRIVILEGE)
	@Transactional
	ShareablePackage saveConceptProposalPackageResponse(ConceptProposalPackageResponse conceptPackage) throws APIException;
	
	/**
	 * Deletes a ConceptProposalPackage including references to all of the individual
	 * ConceptProposals - but does not delete the individual ConceptProposals themselves.
	 * 
	 * @param The ConceptProposalPackage to be removed from persistent storage
	 * @throws APIException
	 * @since 1.0
	 */
	@Authorized(ConceptProposalConsts.MODULE_PRIVILEGE)
	@Transactional
	void deleteConceptProposalPackageResponse(ConceptProposalPackageResponse conceptPackage) throws APIException;
	
}
