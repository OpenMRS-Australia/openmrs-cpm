package org.openmrs.module.cpm.api;

import java.util.List;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.cpm.ConceptProposalConsts;
import org.openmrs.module.cpm.ConceptProposalPackage;
import org.springframework.transaction.annotation.Transactional;

/**
 * Contains methods exposing the core functionality. It is an API that can be used outside of the
 * module.
 * <p>
 * Usage example:<br>
 * <code>
 * Context.getService(ConceptProposalService.class).getAllConceptProposalPackages();
 * 
 * @see org.openmrs.api.context.Context
 */
public interface ConceptProposalService extends OpenmrsService {

	/**
	 * Gets a list of all of the proposals that have been proposed on this OpenMRS instance.  For instances acting in the role of clients
	 * this will return all of the packages that have been created for submissions.  For instances acting in the role of servers this will
	 * return all of the packages that have been recieved via the REST services
	 * 
	 * @return A list of ConceptProposalPackages that may be in a variety of states
	 * @throws APIException
	 * @since 1.0
	 */
	@Authorized(ConceptProposalConsts.MODULE_PRIVILEGE)
	@Transactional
	List<ConceptProposalPackage> getAllConceptProposalPackages() throws APIException;
	
}
