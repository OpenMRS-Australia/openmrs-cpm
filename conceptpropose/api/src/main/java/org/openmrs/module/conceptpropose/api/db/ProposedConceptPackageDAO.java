package org.openmrs.module.conceptpropose.api.db;

import java.util.List;

import org.openmrs.module.conceptpropose.ProposedConceptPackage;

/**
 * Service interface for the ProposedConceptPackage service data access objects.  This interface is used by Spring to inject
 * transaction interceptors around the ProposedConceptPackageDAO implementations  
 */
public interface ProposedConceptPackageDAO {
	
	List<ProposedConceptPackage> getAllConceptProposalPackages();
	
	ProposedConceptPackage getConceptProposalPackageById(Integer id);
	
	ProposedConceptPackage getConceptProposalPackageByUuid(String uuid);
	
	ProposedConceptPackage saveConceptProposalPackage(ProposedConceptPackage conceptPackage);
	
	void deleteConceptProposalPackage(ProposedConceptPackage conceptPackage);
		
}
