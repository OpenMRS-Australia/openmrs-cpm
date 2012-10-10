package com.openmrs.module.cpm.api;

import java.util.List;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.ConceptProposalPackage;
import org.openmrs.module.cpm.api.ConceptProposalPackageService;
import org.openmrs.test.BaseModuleContextSensitiveTest;


public class TestConceptProposalService extends BaseModuleContextSensitiveTest {
	
	protected Log log = LogFactory.getLog(getClass()); 
	protected ConceptProposalPackageService service = null;
	
	@Before
	public void before() throws Exception {
		service = Context.getService(ConceptProposalPackageService.class);
	}
	
	@After
	public void after() throws Exception {
		deleteAllData();
	}
	
	/**
	 * Creates and returns new mock subscription
	 * 
	 * @return new mock subscription instance
	 */
	protected ConceptProposalPackage getMockConceptProposalPackage(Integer id, String name) {
		ConceptProposalPackage conceptPackage = new ConceptProposalPackage();
		conceptPackage.setId(id);
		conceptPackage.setName(name);
		return conceptPackage;
	}
		
	@Test
	public void saveConceptProposalPackage_basicSave() throws Exception {
	    //initializeInMemoryDatabase();
	    //authenticate();
	 
		ConceptProposalPackage testPackage = getMockConceptProposalPackage(null, "new package");
		log.info("Before: " + testPackage);
	    service.saveConceptProposalPackage(testPackage);
		log.info("After: " + testPackage);
		Assert.assertTrue(testPackage.getId() > 0);
	}
	
	@Test
	public void getConceptProposalPackage_basicRetrieval() throws Exception {
	    //initializeInMemoryDatabase();
	    //authenticate();
	 
	    List<ConceptProposalPackage> packages = Context.getService(ConceptProposalPackageService.class).getAllConceptProposalPackages();
		log.info("Retrieved: " + packages.size() + " packages");
	}
}
