package org.openmrs.module.conceptpropose.api.db;

import org.openmrs.api.APIException;
import org.openmrs.module.conceptpropose.ProposedConceptResponsePackage;

import java.util.List;

/**
 * Service interface for the ProposedConceptPackage service data access objects.  This interface is used by Spring to inject
 * transaction interceptors around the ProposedConceptPackageResponseDAO implementations  
 */
public interface ProposedConceptPackageResponseDAO {
	
	List<ProposedConceptResponsePackage> getAllConceptProposalResponsePackages() throws APIException;
	
	ProposedConceptResponsePackage getConceptProposalResponsePackageById(Integer id) throws APIException;
	
	ProposedConceptResponsePackage getConceptProposalResponsePackageByProposalUuid(String uuid) throws APIException;
	
	ProposedConceptResponsePackage saveConceptProposalResponsePackage(ProposedConceptResponsePackage conceptPackage) throws APIException;
	
	void deleteConceptProposalResponsePackage(ProposedConceptResponsePackage conceptPackage) throws APIException;

	void deleteConceptProposalResponsePackageById(int proposalId);
}
