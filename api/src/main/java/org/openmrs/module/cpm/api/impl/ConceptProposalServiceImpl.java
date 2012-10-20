package org.openmrs.module.cpm.api.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.cpm.ConceptProposalPackage;
import org.openmrs.module.cpm.ConceptProposalPackageResponse;
import org.openmrs.module.cpm.ShareablePackage;
import org.openmrs.module.cpm.api.ConceptProposalService;
import org.openmrs.module.cpm.api.db.ConceptProposalPackageDAO;
import org.openmrs.module.cpm.api.db.ConceptProposalPackageResponseDAO;
import org.springframework.stereotype.Service;

@Service("org.openmrs.module.cpm.api.ConceptProposalService")
public class ConceptProposalServiceImpl extends BaseOpenmrsService implements ConceptProposalService {
	
	private static final Log log = LogFactory.getLog(ConceptProposalServiceImpl.class);
	
	@Resource(name = "hibernateConceptProposalPackageDAO")
	private ConceptProposalPackageDAO proposalDao;
	@Resource(name = "hibernateConceptProposalPackageResponseDAO")
	private ConceptProposalPackageResponseDAO proposalResponseDao;

	//	Starting with all of the services for the client side of the ConceptProposal module
	
	@Override
    public List<ConceptProposalPackage> getAllConceptProposalPackages() throws APIException {
	    return proposalDao.getAllConceptProposalPackages();
    }

	@Override
    public ConceptProposalPackage getConceptProposalPackageById(Integer id) throws APIException {
		return proposalDao.getConceptProposalPackageById(id);
    }

	@Override
    public ConceptProposalPackage getConceptProposalPackageByUuid(String uuid) throws APIException {
		return proposalDao.getConceptProposalPackageByUuid(uuid);
    }

	@Override
    public ConceptProposalPackage saveConceptProposalPackage(ConceptProposalPackage conceptPackage) throws APIException {
		return proposalDao.saveConceptProposalPackage(conceptPackage);
    }

	@Override
    public void deleteConceptProposalPackage(ConceptProposalPackage conceptPackage) throws APIException {
		proposalDao.deleteConceptProposalPackage(conceptPackage);
    }
	
	//	Moving on to all of the services for the server side of the Concept Proposal Module
	
	@Override
	public List<ConceptProposalPackageResponse> getAllConceptProposalPackageResponses() throws APIException {
		return proposalResponseDao.getAllConceptProposalPackageResponses();
	}
	
	@Override
	public ConceptProposalPackageResponse getConceptProposalPackageResponseById(Integer id) throws APIException {
		return proposalResponseDao.getConceptProposalPackageResponseById(id);
	}
	
	@Override
	public ConceptProposalPackageResponse getConceptProposalPackageResponseByProposalUuid(String uuid) throws APIException {
		return proposalResponseDao.getConceptProposalPackageResponseByProposalUuid(uuid);
	}
	
	@Override
	public ConceptProposalPackageResponse saveConceptProposalPackageResponse(ConceptProposalPackageResponse conceptPackageResponse) throws APIException {
		return proposalResponseDao.saveConceptProposalPackageResponse(conceptPackageResponse);
	}
	
	@Override
	public void deleteConceptProposalPackageResponse(ConceptProposalPackageResponse conceptPackageResponse) throws APIException {
		proposalResponseDao.deleteConceptProposalPackageResponse(conceptPackageResponse);
	}
	
}
