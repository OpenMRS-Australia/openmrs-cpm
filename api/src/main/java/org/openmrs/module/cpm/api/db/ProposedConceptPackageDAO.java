package org.openmrs.module.cpm.api.db;

import org.openmrs.module.cpm.PackageStatus;
import org.openmrs.module.cpm.ProposedConceptPackage;

import java.util.List;

/**
 * Service interface for the ProposedConceptPackage service data access objects.  This interface is used by Spring to inject
 * transaction interceptors around the ProposedConceptPackageDAO implementations  
 */
public interface ProposedConceptPackageDAO {

    ProposedConceptPackage getConceptProposalPackageById(Integer id);
	
	ProposedConceptPackage getConceptProposalPackageByUuid(String uuid);
	
	ProposedConceptPackage saveConceptProposalPackage(ProposedConceptPackage conceptPackage);
	
	void deleteConceptProposalPackage(ProposedConceptPackage conceptPackage);

    List<ProposedConceptPackage> getProposedConceptPackagesForStatuses(PackageStatus... statuses);
}
