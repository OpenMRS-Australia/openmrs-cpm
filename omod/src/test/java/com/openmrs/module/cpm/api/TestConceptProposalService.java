package com.openmrs.module.cpm.api;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.ConceptProposalPackage;
import org.openmrs.module.cpm.api.ConceptProposalService;
import org.openmrs.test.BaseModuleContextSensitiveTest;


public class TestConceptProposalService extends BaseModuleContextSensitiveTest {
	
	protected Log log = LogFactory.getLog(getClass());
	
	@Test
	public void testCreateConceptProposalService() throws Exception {
	    initializeInMemoryDatabase();
	    authenticate();
	 
	    List<ConceptProposalPackage> packages = Context.getService(ConceptProposalService.class).getAllConceptProposalPackages();
		log.info("Retrieved: " + packages.size() + " packages");
	}
	
}
