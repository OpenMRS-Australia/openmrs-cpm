package org.openmrs.module.cpm.api.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.cpm.ConceptProposalPackage;
import org.openmrs.module.cpm.api.ConceptProposalService;
import org.openmrs.module.cpm.api.db.ConceptProposalPackageDAO;
import org.springframework.stereotype.Service;


@Service("org.openmrs.module.cpm.api.ConceptProposalService")
public class ConceptProposalServiceImpl extends BaseOpenmrsService implements ConceptProposalService {
	
	@Resource(name = "hibernateConceptProposalPackageDAO")
	private ConceptProposalPackageDAO dao;

	protected final Log log = LogFactory.getLog(getClass());

	@Override
    public List<ConceptProposalPackage> getAllConceptProposalPackages() throws APIException {
	    return dao.getAllConceptProposalPackages();
    }
	
}
