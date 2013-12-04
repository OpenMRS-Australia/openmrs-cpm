package org.openmrs.module.conceptreview.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.conceptreview.api.ProposedConceptReviewService;
import org.openmrs.module.conceptreview.api.db.ProposedConceptPackageReviewDAO;
import org.openmrs.module.conceptreview.ProposedConceptReviewPackage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("org.openmrs.module.conceptpropose.api.ProposedConceptService")
public class ProposedConceptReviewServiceImpl extends BaseOpenmrsService implements ProposedConceptReviewService {
	
	private static final Log log = LogFactory.getLog(ProposedConceptReviewServiceImpl.class);
	
	@Resource(name = "hibernateProposedConceptPackageReviewDAO")
	private ProposedConceptPackageReviewDAO proposalReviewDao;


	@Override
	public List<ProposedConceptReviewPackage> getAllProposedConceptReviewPackages() throws APIException {
		return proposalReviewDao.getAllConceptProposalReviewPackages();
	}
	
	@Override
	public ProposedConceptReviewPackage getProposedConceptReviewPackageById(Integer id) throws APIException {
		return proposalReviewDao.getConceptProposalReviewPackageById(id);
	}
	
	@Override
	public ProposedConceptReviewPackage getProposedConceptReviewPackageByProposalUuid(String uuid) throws APIException {
		return proposalReviewDao.getConceptProposalReviewPackageByProposalUuid(uuid);
	}
	
	@Override
	public ProposedConceptReviewPackage saveProposedConceptReviewPackage(ProposedConceptReviewPackage conceptPackageReview) throws APIException {
		return proposalReviewDao.saveConceptProposalReviewPackage(conceptPackageReview);
	}
	
	@Override
	public void deleteProposedConceptReviewPackage(ProposedConceptReviewPackage conceptPackageReview) throws APIException {
		proposalReviewDao.deleteConceptProposalReviewPackage(conceptPackageReview);
	}

	@Override
	public void deleteProposedConceptReviewPackageById(final int proposalId) throws APIException {
		proposalReviewDao.deleteConceptProposalReviewPackageById(proposalId);
	}
}
