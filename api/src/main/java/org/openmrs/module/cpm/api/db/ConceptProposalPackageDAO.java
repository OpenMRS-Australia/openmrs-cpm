package org.openmrs.module.cpm.api.db;

import java.util.List;

import org.openmrs.module.cpm.ConceptProposalPackage;

/**
 * Service interface for the ConceptProposalPackage service data access objects.  This interface is used by Spring to inject
 * transaction interceptors around the ConceptProposalPackageDAO implementations  
 */
public interface ConceptProposalPackageDAO {
	
	List<ConceptProposalPackage> getAllConceptProposalPackages();
	
	ConceptProposalPackage getConceptProposalPackageById(Integer id);
	
	ConceptProposalPackage getConceptProposalPackageByUuid(String uuid);
	
	ConceptProposalPackage saveConceptProposalPackage(ConceptProposalPackage conceptPackage);
	
	void deleteConceptProposalPackage(ConceptProposalPackage conceptPackage);
		
}
