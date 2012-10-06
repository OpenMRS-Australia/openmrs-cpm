package org.openmrs.module.cpm.api.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.cpm.api.db.ConceptProposalPackageDAO;


public class ConceptProposalServiceImpl {
	
	@Resource(name = "hibernateConceptProposalPackageDAO")
	private ConceptProposalPackageDAO dao;

	protected final Log log = LogFactory.getLog(getClass());
	
}
