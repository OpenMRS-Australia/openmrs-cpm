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
import org.openmrs.module.cpm.ConceptProposalPackageResponse;
import org.openmrs.module.cpm.api.ConceptProposalService;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class TestConceptProposalService extends BaseModuleContextSensitiveTest {
	
	private static Log log = LogFactory.getLog(TestConceptProposalService.class);
	private static String CPM_CORE_DATASET = "org/openmrs/module/cpm/coreTestData.xml";
	
	protected ConceptProposalService service = null;
	
	@Before
	public void before() throws Exception {
		service = Context.getService(ConceptProposalService.class);
		log.info("Loading the core Concept Proposal Module test data set");
		executeXmlDataSet(CPM_CORE_DATASET);
		log.info("Loading of the core Concept Proposal Module test data set complete");
	}
	
	@After
	public void after() throws Exception {
		deleteAllData();
	}
	
	/**
	 * Creates and returns new mock concept package
	 * 
	 * @return new mock concept package
	 */
	protected ConceptProposalPackage getMockConceptProposalPackage(Integer id, String name) {
		ConceptProposalPackage conceptPackage = new ConceptProposalPackage();
		conceptPackage.setId(id);
		conceptPackage.setName(name);
		conceptPackage.setEmail("test@test.com");
		return conceptPackage;
	}
		
	/**
	 * Creates and returns new mock concept package response
	 * 
	 * @return new mock concept package response
	 */
	protected ConceptProposalPackageResponse getMockConceptProposalPackageResponse(Integer id, String name, String email) {
		ConceptProposalPackageResponse conceptPackageResponse = new ConceptProposalPackageResponse(getMockConceptProposalPackage(id,name));
		conceptPackageResponse.setId(id);
		conceptPackageResponse.setName(name);
		return conceptPackageResponse;
	}

	public void saveConceptProposalService_basicSave() throws Exception {
	    //initializeInMemoryDatabase();
	    //authenticate();
	 
		ConceptProposalPackage testPackage = getMockConceptProposalPackage(null, "new package");
		log.info("Before: " + testPackage + ", email=" + testPackage.getEmail());
	    service.saveConceptProposalPackage(testPackage);
		log.info("After: " + testPackage);
		Assert.assertTrue(testPackage.getId() > 0);
	}
	
	public void getConceptProposalService_basicRetrieval() throws Exception {
	    //initializeInMemoryDatabase();
	    //authenticate();
	 
	    List<ConceptProposalPackage> packages = Context.getService(ConceptProposalService.class).getAllConceptProposalPackages();
		log.info("Retrieved: " + packages.size() + " packages");
		Assert.assertEquals(packages.size(), 2);
	}
}
