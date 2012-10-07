package org.openmrs.module.cpm.api.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.cpm.ConceptProposalPackage;
import org.openmrs.module.cpm.api.ConceptProposalPackageService;
import org.openmrs.module.cpm.api.db.ConceptProposalPackageDAO;
import org.springframework.stereotype.Service;

@Service("org.openmrs.module.cpm.api.ConceptProposalPackageService")
public class ConceptProposalPackageServiceImpl extends BaseOpenmrsService implements ConceptProposalPackageService {
	
	@Resource(name = "hibernateConceptProposalPackageDAO")
	private ConceptProposalPackageDAO dao;

	protected final Log log = LogFactory.getLog(getClass());

	@Override
    public List<ConceptProposalPackage> getAllConceptProposalPackages() throws APIException {
	    return dao.getAllConceptProposalPackages();
    }

	@Override
    public ConceptProposalPackage getConceptProposalPackageById(Integer id) throws APIException {
		return dao.getConceptProposalPackageById(id);
    }

	@Override
    public ConceptProposalPackage getConceptProposalPackageByUuid(String uuid) throws APIException {
		return dao.getConceptProposalPackageByUuid(uuid);
    }

	@Override
    public ConceptProposalPackage saveConceptProposalPackage(ConceptProposalPackage conceptPackage) throws APIException {
		return dao.saveConceptProposalPackage(conceptPackage);
    }

	@Override
    public void deleteConceptProposalPackage(ConceptProposalPackage conceptPackage) throws APIException {
		dao.deleteConceptProposalPackage(conceptPackage);
    }
	
}
