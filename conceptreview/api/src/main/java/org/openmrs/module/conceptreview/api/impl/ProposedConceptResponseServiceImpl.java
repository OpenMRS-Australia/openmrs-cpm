package org.openmrs.module.conceptreview.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.conceptreview.api.ProposedConceptResponseService;
import org.openmrs.module.conceptreview.api.db.ProposedConceptPackageResponseDAO;
import org.openmrs.module.conceptreview.ProposedConceptResponsePackage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("org.openmrs.module.conceptpropose.api.ProposedConceptService")
public class ProposedConceptResponseServiceImpl extends BaseOpenmrsService implements ProposedConceptResponseService {
	
	private static final Log log = LogFactory.getLog(ProposedConceptResponseServiceImpl.class);
	
	@Resource(name = "hibernateProposedConceptPackageResponseDAO")
	private ProposedConceptPackageResponseDAO proposalResponseDao;


	@Override
	public List<ProposedConceptResponsePackage> getAllProposedConceptResponsePackages() throws APIException {
		return proposalResponseDao.getAllConceptProposalResponsePackages();
	}
	
	@Override
	public ProposedConceptResponsePackage getProposedConceptResponsePackageById(Integer id) throws APIException {
		return proposalResponseDao.getConceptProposalResponsePackageById(id);
	}
	
	@Override
	public ProposedConceptResponsePackage getProposedConceptResponsePackageByProposalUuid(String uuid) throws APIException {
		return proposalResponseDao.getConceptProposalResponsePackageByProposalUuid(uuid);
	}
	
	@Override
	public ProposedConceptResponsePackage saveProposedConceptResponsePackage(ProposedConceptResponsePackage conceptPackageResponse) throws APIException {
		return proposalResponseDao.saveConceptProposalResponsePackage(conceptPackageResponse);
	}
	
	@Override
	public void deleteProposedConceptResponsePackage(ProposedConceptResponsePackage conceptPackageResponse) throws APIException {
		proposalResponseDao.deleteConceptProposalResponsePackage(conceptPackageResponse);
	}

	@Override
	public void deleteProposedConceptResponsePackageById(final int proposalId) throws APIException {
		proposalResponseDao.deleteConceptProposalResponsePackageById(proposalId);
	}
}
