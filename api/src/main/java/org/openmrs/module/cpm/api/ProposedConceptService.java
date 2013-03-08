package org.openmrs.module.cpm.api;

import java.util.List;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.cpm.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * Contains methods exposing the core functionality. It is an API that can be used outside of the
 * module. This is used in conjunction with the Core OpenMRS API for adding/saving the individual
 * ProposedConcept/ProposedConceptResponse line items that are contained within packages.
 * <p>
 * Usage example:<br>
 * <code>
 * Context.getService(ProposedConceptService.class).getAllConceptProposalPackages();
 * 
 * @see org.openmrs.api.context.Context
 * @see org.openmrs.api.ConceptService
 */
public interface ProposedConceptService extends OpenmrsService {
	
	//	Starting with all of the services for the client side of the ProposedConcept module
	
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
	List<ProposedConceptPackage> getAllProposedConceptPackages() throws APIException;
	
	/**
	 * Gets a specific ProposedConceptPackage by its id (id field in the class,
	 * concept_proposal_package_id in the database)
	 * 
	 * @return The ConceptProposalPackages corresponding to the id parameter if it is available,
	 *         otherwise a null result
	 * @throws APIException
	 * @since 1.0
	 */
	@Authorized(ConceptProposalConsts.MODULE_PRIVILEGE)
	@Transactional
	ProposedConceptPackage getProposedConceptPackageById(Integer id) throws APIException;
	
	/**
	 * Gets a specific ProposedConceptPackage by its uuid (uuid field in the class, uuid in the
	 * database)
	 * 
	 * @return The ConceptProposalPackages corresponding to the uuid parameter if it is available,
	 *         otherwise a null result
	 * @throws APIException
	 * @since 1.0
	 */
	@Authorized(ConceptProposalConsts.MODULE_PRIVILEGE)
	@Transactional
	ProposedConceptPackage getProposedConceptPackageByUuid(String uuid) throws APIException;
	
	/**
	 * Saves a ProposedConceptPackage including references to all of the individual
	 * ConceptProposals. For instances acting in the role of clients this can be used to save a
	 * draft concept proposal before sending for submission. For instances acting in the role of
	 * servers this can be used to save comments or status updates at the package level
	 * 
	 * @param The ProposedConceptPackage to be created or updated
	 * @return The updated ProposedConceptPackage - including the id assigned to it during storage
	 * @throws APIException
	 * @since 1.0
	 */
	@Authorized(ConceptProposalConsts.MODULE_PRIVILEGE)
	@Transactional
	ShareablePackage saveProposedConceptPackage(ProposedConceptPackage conceptPackage) throws APIException;
	
	/**
	 * Deletes a ProposedConceptPackage including references to all of the individual
	 * ConceptProposals - but does not delete the individual ConceptProposals themselves.
	 * 
	 * @param The ProposedConceptPackage to be removed from persistent storage
	 * @throws APIException
	 * @since 1.0
	 */
	@Authorized(ConceptProposalConsts.MODULE_PRIVILEGE)
	@Transactional
	void deleteProposedConceptPackage(ProposedConceptPackage conceptPackage) throws APIException;


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
	List<ProposedConceptResponsePackage> getAllProposedConceptResponsePackages() throws APIException;
	
	/**
	 * Gets a specific ProposedConceptPackage by its id (id field in the class,
	 * concept_proposal_package_id in the database)
	 * 
	 * @return The ConceptProposalPackages corresponding to the id parameter if it is available,
	 *         otherwise a null result
	 * @throws APIException
	 * @since 1.0
	 */
	@Authorized(ConceptProposalConsts.MODULE_PRIVILEGE)
	@Transactional
	ProposedConceptResponsePackage getProposedConceptResponsePackageById(Integer id) throws APIException;
	
	/**
	 * Gets a specific ProposedConceptPackage by its uuid (uuid field in the class, uuid in the
	 * database)
	 * 
	 * @return The ConceptProposalPackages corresponding to the uuid parameter if it is available,
	 *         otherwise a null result
	 * @throws APIException
	 * @since 1.0
	 */
	@Authorized(ConceptProposalConsts.MODULE_PRIVILEGE)
	@Transactional
	ProposedConceptResponsePackage getProposedConceptResponsePackageByProposalUuid(String uuid) throws APIException;
	
	/**
	 * Saves a ProposedConceptPackage including references to all of the individual
	 * ConceptProposals. For instances acting in the role of clients this can be used to save a
	 * draft concept proposal before sending for submission. For instances acting in the role of
	 * servers this can be used to save comments or status updates at the package level
	 * 
	 * @param The ProposedConceptPackage to be created or updated
	 * @return The updated ProposedConceptPackage - including the id assigned to it during storage
	 * @throws APIException
	 * @since 1.0
	 */
	@Authorized(ConceptProposalConsts.MODULE_PRIVILEGE)
	@Transactional
	ProposedConceptResponsePackage saveProposedConceptResponsePackage(ProposedConceptResponsePackage conceptPackage) throws APIException;
	
	/**
	 * Deletes a ProposedConceptPackage including references to all of the individual
	 * ConceptProposals - but does not delete the individual ConceptProposals themselves.
	 * 
	 * @param The ProposedConceptPackage to be removed from persistent storage
	 * @throws APIException
	 * @since 1.0
	 */
	@Authorized(ConceptProposalConsts.MODULE_PRIVILEGE)
	@Transactional
	void deleteProposedConceptResponsePackage(ProposedConceptResponsePackage conceptPackage) throws APIException;
}
