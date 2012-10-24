package org.openmrs.module.cpm.api.db;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.module.cpm.ProposedConceptPackageResponse;

/**
 * Service interface for the ProposedConceptPackage service data access objects.  This interface is used by Spring to inject
 * transaction interceptors around the ProposedConceptPackageResponseDAO implementations  
 */
public interface ProposedConceptPackageResponseDAO {
	
	List<ProposedConceptPackageResponse> getAllConceptProposalPackageResponses() throws APIException;
	
	ProposedConceptPackageResponse getConceptProposalPackageResponseById(Integer id) throws APIException;
	
	ProposedConceptPackageResponse getConceptProposalPackageResponseByProposalUuid(String uuid) throws APIException;
	
	ProposedConceptPackageResponse saveConceptProposalPackageResponse(ProposedConceptPackageResponse conceptPackage) throws APIException;
	
	void deleteConceptProposalPackageResponse(ProposedConceptPackageResponse conceptPackage) throws APIException;
	
}
