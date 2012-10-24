package org.openmrs.module.cpm.api.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.cpm.ProposedConceptPackage;
import org.openmrs.module.cpm.ProposedConceptPackageResponse;
import org.openmrs.module.cpm.api.ProposedConceptService;
import org.openmrs.module.cpm.api.db.ProposedConceptPackageDAO;
import org.openmrs.module.cpm.api.db.ProposedConceptPackageResponseDAO;
import org.springframework.stereotype.Service;

@Service("org.openmrs.module.cpm.api.ProposedConceptService")
public class ProposedConceptServiceImpl extends BaseOpenmrsService implements ProposedConceptService {
	
	private static final Log log = LogFactory.getLog(ProposedConceptServiceImpl.class);
	
	@Resource(name = "hibernateProposedConceptPackageDAO")
	private ProposedConceptPackageDAO proposalDao;
	@Resource(name = "hibernateProposedConceptPackageResponseDAO")
	private ProposedConceptPackageResponseDAO proposalResponseDao;

	//	Starting with all of the services for the client side of the ProposedConcept module
	
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
	
	//	Moving on to all of the services for the server side of the Concept Proposal Module
	
	@Override
	public List<ProposedConceptPackageResponse> getAllProposedConceptPackageResponses() throws APIException {
		return proposalResponseDao.getAllConceptProposalPackageResponses();
	}
	
	@Override
	public ProposedConceptPackageResponse getProposedConceptPackageResponseById(Integer id) throws APIException {
		return proposalResponseDao.getConceptProposalPackageResponseById(id);
	}
	
	@Override
	public ProposedConceptPackageResponse getProposedConceptPackageResponseByProposalUuid(String uuid) throws APIException {
		return proposalResponseDao.getConceptProposalPackageResponseByProposalUuid(uuid);
	}
	
	@Override
	public ProposedConceptPackageResponse saveProposedConceptPackageResponse(ProposedConceptPackageResponse conceptPackageResponse) throws APIException {
		return proposalResponseDao.saveConceptProposalPackageResponse(conceptPackageResponse);
	}
	
	@Override
	public void deleteProposedConceptPackageResponse(ProposedConceptPackageResponse conceptPackageResponse) throws APIException {
		proposalResponseDao.deleteConceptProposalPackageResponse(conceptPackageResponse);
	}
	
}
