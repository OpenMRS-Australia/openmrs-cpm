package org.openmrs.module.conceptpropose.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.conceptpropose.ProposedConceptPackage;
import org.openmrs.module.conceptpropose.api.ProposedConceptService;
import org.openmrs.module.conceptpropose.api.db.ProposedConceptPackageDAO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("org.openmrs.module.conceptpropose.api.ProposedConceptService")
public class ProposedConceptServiceImpl extends BaseOpenmrsService implements ProposedConceptService {
	
	private static final Log log = LogFactory.getLog(ProposedConceptServiceImpl.class);
	
	@Resource(name = "hibernateProposedConceptPackageDAO")
	private ProposedConceptPackageDAO proposalDao;


	@Override
    public List<ProposedConceptPackage> getAllProposedConceptPackages() throws APIException {
	    return proposalDao.getAllConceptProposalPackages();
    }

	@Override
    public ProposedConceptPackage getProposedConceptPackageById(Integer id) throws APIException {
		return proposalDao.getConceptProposalPackageById(id);
    }

	@Override
    public ProposedConceptPackage getProposedConceptPackageByUuid(String uuid) throws APIException {
		return proposalDao.getConceptProposalPackageByUuid(uuid);
    }

	@Override
    public ProposedConceptPackage saveProposedConceptPackage(ProposedConceptPackage conceptPackage) throws APIException {
		return proposalDao.saveConceptProposalPackage(conceptPackage);
    }

	@Override
    public void deleteProposedConceptPackage(ProposedConceptPackage conceptPackage) throws APIException {
		proposalDao.deleteConceptProposalPackage(conceptPackage);
    }
	
	@Override
	public ProposedConceptPackage getMostRecentConceptProposalPackage() {
		return proposalDao.getMostRecentConceptProposalPackage();
	}
}
