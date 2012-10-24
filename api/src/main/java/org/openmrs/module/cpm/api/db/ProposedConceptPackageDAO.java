package org.openmrs.module.cpm.api.db;

import java.util.List;

import org.openmrs.module.cpm.ProposedConceptPackage;

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
