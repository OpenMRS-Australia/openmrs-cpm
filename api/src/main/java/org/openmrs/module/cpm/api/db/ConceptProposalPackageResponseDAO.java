package org.openmrs.module.cpm.api.db;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.module.cpm.ConceptProposalPackageResponse;

/**
 * Service interface for the ConceptProposalPackage service data access objects.  This interface is used by Spring to inject
 * transaction interceptors around the ConceptProposalPackageResponseDAO implementations  
 */
public interface ConceptProposalPackageResponseDAO {
	
	List<ConceptProposalPackageResponse> getAllConceptProposalPackageResponses() throws APIException;
	
	ConceptProposalPackageResponse getConceptProposalPackageResponseById(Integer id) throws APIException;
	
	ConceptProposalPackageResponse getConceptProposalPackageResponseByProposalUuid(String uuid) throws APIException;
	
	ConceptProposalPackageResponse saveConceptProposalPackageResponse(ConceptProposalPackageResponse conceptPackage) throws APIException;
	
	void deleteConceptProposalPackageResponse(ConceptProposalPackageResponse conceptPackage) throws APIException;
	
}
