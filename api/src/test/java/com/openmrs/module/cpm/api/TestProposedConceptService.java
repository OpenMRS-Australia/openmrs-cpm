package com.openmrs.module.cpm.api;

import com.openmrs.module.cpm.test.CpmBaseContextSensitive;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.PropertyValueException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.PackageStatus;
import org.openmrs.module.cpm.ProposedConcept;
import org.openmrs.module.cpm.ProposedConceptPackage;
import org.openmrs.module.cpm.ProposedConceptResponse;
import org.openmrs.module.cpm.ProposedConceptResponsePackage;
import org.openmrs.module.cpm.api.ProposedConceptService;

import java.text.SimpleDateFormat;
import java.util.List;

public class TestProposedConceptService extends CpmBaseContextSensitive {
	
	private static final Log log = LogFactory.getLog(TestProposedConceptService.class);
	private static final String CPM_CORE_DATASET = "org/openmrs/module/cpm/coreTestData.xml";
	private static final String TEST_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	private static final String TEST_DATE_DISPLAY_FORMAT = "yyyy-MM-dd HH:mm:ss.S z G";
			
	protected ProposedConceptService service = null;
	protected ConceptService conceptService = null;
	protected SimpleDateFormat formatter = new SimpleDateFormat(TEST_DATE_FORMAT);
	protected SimpleDateFormat comparator = new SimpleDateFormat(TEST_DATE_DISPLAY_FORMAT);

	@Before
	public void before() throws Exception {
		service = Context.getService(ProposedConceptService.class);
		conceptService = Context.getConceptService();
		log.info("Loading the core Concept Proposal Module test data set");
		executeDataSet(CPM_CORE_DATASET);
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
	protected ProposedConceptPackage getMockProposedConceptPackage(Integer id, String name) {
		ProposedConceptPackage conceptPackage = new ProposedConceptPackage();
		conceptPackage.setId(id);
		conceptPackage.setName(name);
		conceptPackage.setDescription("description");
		conceptPackage.setEmail("test@test.com");
		return conceptPackage;
	}

	/**
	 * Creates and returns new mock concept package response
	 *
	 * @return new mock concept package response
	 */
	protected ProposedConceptResponsePackage getMockProposedConceptPackageResponse(Integer id, String name) {
		ProposedConceptResponsePackage conceptPackageResponse = new ProposedConceptResponsePackage();
		conceptPackageResponse.setId(id);
		conceptPackageResponse.setName(name);
		conceptPackageResponse.setDescription("description");
		conceptPackageResponse.setEmail("test@test.com");
		conceptPackageResponse.setId(id);
		conceptPackageResponse.setName(name);
		conceptPackageResponse.setVersion(0);
		return conceptPackageResponse;
	}
	
	/**
	 * Creates and returns a new mock concept 
	 * 
	 * @throws Exception
	 */
	protected ProposedConcept getMockProposedConcept(Integer id, String name, String description, Concept concept) {
		ProposedConcept proposedConcept = new ProposedConcept();
		proposedConcept.setId(id);
		proposedConcept.setConcept(concept);
		return proposedConcept;
	}
	
	/**
	 * Creates and returns a new mock concept 
	 * 
	 * @throws Exception
	 */
	protected ProposedConceptResponse getMockProposedConceptResponse(Integer id, ProposedConcept proposedConcept) {
		ProposedConceptResponse proposedConceptResponse = new ProposedConceptResponse();
		proposedConcept.setId(id);
		return proposedConceptResponse;
	}

	@Test
	public void getAllProposedConceptPackages_basicRetrieval() throws Exception {
	    List<ProposedConceptPackage> packages = Context.getService(ProposedConceptService.class).getAllProposedConceptPackages();
		log.info("Retrieved: " + packages.size() + " packages");
		Assert.assertEquals(2, packages.size());
	}

	@Test
	public void getAllProposedConceptPackages_emptyRetrieval() throws Exception {
	    List<ProposedConceptPackage> packages = Context.getService(ProposedConceptService.class).getAllProposedConceptPackages();
	    for (ProposedConceptPackage currentPackage : packages) {
	    	service.deleteProposedConceptPackage(currentPackage);
	    }
		
	    packages = service.getAllProposedConceptPackages();
		log.info("Retrieved: " + packages.size() + " packages");
		Assert.assertEquals(0, packages.size());
	}
	
	@Test
	public void getAllProposedConceptPackages_insertedRetrieval() throws Exception {
		List<ProposedConceptPackage> packages = service.getAllProposedConceptPackages();
		log.info("Before - retrieved: " + packages.size() + " packages");
		Assert.assertEquals(2, packages.size());

		ProposedConceptPackage testPackage = getMockProposedConceptPackage(null, "new package");
	    service.saveProposedConceptPackage(testPackage);
	    packages = service.getAllProposedConceptPackages();
		log.info("After - retrieved: " + packages.size() + " packages");
		Assert.assertEquals(3, packages.size());
	}

	@Test
	public void getProposedConceptPackageById_basicRetrieval() throws Exception {
		ProposedConceptPackage testPackage = service.getProposedConceptPackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals("Concept Proposal Package 1", testPackage.getName());
		Assert.assertEquals("proposer@cpm.com", testPackage.getEmail());
		Assert.assertEquals("d0dd9f30-15e7-11e2-892e-0800200c9a66", testPackage.getUuid());
		Assert.assertEquals("Description for concept proposal package 1", testPackage.getDescription());
		Assert.assertEquals(1,testPackage.getVersion().intValue());
		Assert.assertEquals(PackageStatus.DRAFT,testPackage.getStatus());
		Assert.assertEquals(3, testPackage.getProposedConcepts().size());
		Assert.assertNull(testPackage.getCreator().getName());
	}
	
	@Test
	public void getProposedConceptPackageById_emptyRetrieval() throws Exception {
		ProposedConceptPackage testPackage = service.getProposedConceptPackageById(0);
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}

	@Test
	public void getProposedConceptPackageById_nullRetrieval() throws Exception {
		ProposedConceptPackage testPackage = service.getProposedConceptPackageById(null);
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}
	
	@Test
	public void getProposedConceptPackageByUuid_basicRetrieval() throws Exception {
		ProposedConceptPackage testPackage = service.getProposedConceptPackageByUuid("d0dd9f30-15e7-11e2-892e-0800200c9a66");
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals("Concept Proposal Package 1", testPackage.getName());
	}
	
	@Test
	public void getProposedConceptPackageByUuid_emptyRetrieval() throws Exception {
		ProposedConceptPackage testPackage = service.getProposedConceptPackageByUuid("doesnotexist");
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}

	@Test
	public void getProposedConceptPackageByUuid_nullRetrieval() throws Exception {
		ProposedConceptPackage testPackage = service.getProposedConceptPackageByUuid(null);
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}
	
	@Test
	public void saveProposedConceptPackage_basicSave() throws Exception {
		ProposedConceptPackage testPackage = getMockProposedConceptPackage(null, "new package");
		log.info("Before: " + testPackage);
	    service.saveProposedConceptPackage(testPackage);
		log.info("After: " + testPackage);
		Assert.assertTrue(testPackage.getId().intValue() >= 3);
	}
	
	@Test
	public void saveProposedConceptPackage_saveWithChildConcept() throws Exception {
		ProposedConceptPackage testPackage = getMockProposedConceptPackage(null, "new package");
		List<Concept> concepts = conceptService.getAllConcepts();
		log.debug("************************************************************");
		for (Concept currentConcept : concepts) {
			log.debug("Concept: " + currentConcept);
		}
		Concept testConcept1 = conceptService.getConcept(3);
		Concept testConcept2 = conceptService.getConcept(4);
		ProposedConcept concept1 = getMockProposedConcept(null, "concept 1", "concept 1 description", testConcept1);
		ProposedConcept concept2 = getMockProposedConcept(null, "concept 2", "concept 1 description", testConcept2);
		testPackage.addProposedConcept(concept1);
		testPackage.addProposedConcept(concept2);
		
		log.info("Before: " + testPackage);
	    service.saveProposedConceptPackage(testPackage);
		log.info("After: " + testPackage);
		Assert.assertTrue(testPackage.getId().intValue() >= 3);
	}

	@Test
	public void saveProposedConceptPackage_basicUpdate() throws Exception {
		ProposedConceptPackage testPackage = service.getProposedConceptPackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals("Concept Proposal Package 1", testPackage.getName());

		String newName = "New Name";
		testPackage.setName(newName);
		service.saveProposedConceptPackage(testPackage);
		
		testPackage = service.getProposedConceptPackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals(newName, testPackage.getName());
	}

	@Test
	public void saveProposedConceptPackage_updateAddChild() throws Exception {
		ProposedConceptPackage testPackage = service.getProposedConceptPackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals("Concept Proposal Package 1", testPackage.getName());

		Concept testConcept = conceptService.getConcept(3);
		String newName = "New Name";
		testPackage.setName(newName);
		ProposedConcept concept1 = getMockProposedConcept(null, "concept 1", "concept 1 description",testConcept);
		testPackage.addProposedConcept(concept1);
		service.saveProposedConceptPackage(testPackage);
		
		testPackage = service.getProposedConceptPackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals(newName, testPackage.getName());
		Assert.assertEquals(4, testPackage.getProposedConcepts().size());
	}
	
	@Test
	public void saveProposedConceptPackage_updateRemoveChild() throws Exception {
		ProposedConceptPackage testPackage = service.getProposedConceptPackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals("Concept Proposal Package 1", testPackage.getName());
		Assert.assertEquals(3, testPackage.getProposedConcepts().size());

		String newName = "New Name";
		testPackage.setName(newName);
		ProposedConcept proposedConcept = (ProposedConcept) testPackage.getProposedConcepts().iterator().next();
		log.debug("Removing proposed concept: " + proposedConcept);
		testPackage.removeProposedConcept(proposedConcept);
		service.saveProposedConceptPackage(testPackage);
		
		testPackage = service.getProposedConceptPackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals(newName, testPackage.getName());
		Assert.assertEquals(2, testPackage.getProposedConcepts().size());
	}

	@Test
	public void deleteProposedConceptPackage_basicDelete() throws Exception {
		ProposedConceptPackage testPackage = service.getProposedConceptPackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertNotNull(testPackage);
	    service.deleteProposedConceptPackage(testPackage);
	    
		testPackage = service.getProposedConceptPackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}

	@Test
	public void deleteProposedConceptPackage_multipleDelete() throws Exception {
		ProposedConceptPackage testPackage = service.getProposedConceptPackageById(1);
		log.info("Before: " + testPackage);
		Assert.assertNotNull(testPackage);
	    service.deleteProposedConceptPackage(testPackage);
		log.info("After: " + testPackage);
	    
	    service.deleteProposedConceptPackage(testPackage);
	}

	@Test
	public void deleteProposedConceptPackage_nullDelete() throws Exception {
		ProposedConceptPackage testPackage = null;
		log.info("Package: " + testPackage);
	    service.deleteProposedConceptPackage(testPackage);
	}

	
	@Test
	public void getAllProposedConceptPackageResponses_basicRetrieval() throws Exception {
	    List<ProposedConceptResponsePackage> packages = service.getAllProposedConceptResponsePackages();
		log.info("Retrieved: " + packages.size() + " packages");
		Assert.assertEquals(2, packages.size());
	}

	@Test
	public void getAllProposedConceptPackageResponses_emptyRetrieval() throws Exception {
	    List<ProposedConceptResponsePackage> packages = service.getAllProposedConceptResponsePackages();
	    for (ProposedConceptResponsePackage currentPackage : packages) {
	    	service.deleteProposedConceptResponsePackage(currentPackage);
	    }
		
	    packages = service.getAllProposedConceptResponsePackages();
		log.info("Retrieved: " + packages.size() + " packages");
		Assert.assertEquals(0, packages.size());
	}
	
	@Test
	public void getAllProposedConceptPackageResponses_insertedRetrieval() throws Exception {
		List<ProposedConceptResponsePackage> packages = service.getAllProposedConceptResponsePackages();
		log.info("Before - retrieved: " + packages.size() + " packages");
		Assert.assertEquals(2, packages.size());

		ProposedConceptResponsePackage testPackage = getMockProposedConceptPackageResponse(null, "new package");
	    service.saveProposedConceptResponsePackage(testPackage);
	    packages = service.getAllProposedConceptResponsePackages();
		log.info("After - retrieved: " + packages.size() + " packages");
		Assert.assertEquals(3, packages.size());
	}

	@Test
	public void getProposedConceptPackageResponseById_basicRetrieval() throws Exception {
		ProposedConceptResponsePackage testPackage = service.getProposedConceptResponsePackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals("Concept Proposal Package Response 1", testPackage.getName());
		Assert.assertEquals("proposer@cpm.com", testPackage.getEmail());
		Assert.assertEquals("d0dd9f3c-15e7-11e2-892e-0800200c9a66", testPackage.getUuid());
		Assert.assertEquals("d0dd9f30-15e7-11e2-892e-0800200c9a66", testPackage.getProposedConceptPackageUuid());
		Assert.assertEquals("Description for concept proposal package 1", testPackage.getDescription());
		Assert.assertEquals(1,testPackage.getVersion().intValue());
		Assert.assertEquals(PackageStatus.RECEIVED,testPackage.getStatus());
		Assert.assertEquals(3, testPackage.getProposedConcepts().size());
		Assert.assertNull(testPackage.getCreator().getName());
	}
	
	@Test
	public void getProposedConceptPackageResponseById_emptyRetrieval() throws Exception {
		ProposedConceptResponsePackage testPackage = service.getProposedConceptResponsePackageById(0);
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}

	@Test
	public void getProposedConceptPackageResponseById_nullRetrieval() throws Exception {
		ProposedConceptResponsePackage testPackage = service.getProposedConceptResponsePackageById(null);
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}
	
	@Test
	public void getProposedConceptPackageResponseByProposalUuid_basicRetrieval() throws Exception {
		ProposedConceptResponsePackage testPackage = service.getProposedConceptResponsePackageByProposalUuid("d0dd9f30-15e7-11e2-892e-0800200c9a66");
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals("Concept Proposal Package Response 1", testPackage.getName());
	}
	
	@Test
	public void getProposedConceptPackageResponseByProposalUuid_emptyRetrieval() throws Exception {
		ProposedConceptResponsePackage testPackage = service.getProposedConceptResponsePackageByProposalUuid("doesnotexist");
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}

	@Test
	public void getProposedConceptPackageResponseByProposalUuid_nullRetrieval() throws Exception {
		ProposedConceptResponsePackage testPackage = service.getProposedConceptResponsePackageByProposalUuid(null);
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}
	
	@Test
	public void saveProposedConceptPackageResponse_basicSave() throws Exception {
		ProposedConceptResponsePackage testPackage = getMockProposedConceptPackageResponse(null, "new package");
		log.info("Before: " + testPackage);
	    service.saveProposedConceptResponsePackage(testPackage);
		log.info("After: " + testPackage);
		Assert.assertTrue(testPackage.getId().intValue() >= 3);
	}
	
	@Test
	public void saveProposedConceptPackageResponse_saveWithChildren() throws Exception {
		ProposedConceptResponsePackage testPackage = getMockProposedConceptPackageResponse(null, "new package");
		Concept testConcept1 = conceptService.getConcept(3);
		Concept testConcept2 = conceptService.getConcept(4);
		log.warn("Concept 1: " + testConcept1);
		log.warn("Concept 2: " + testConcept2);
		ProposedConceptResponse concept1 = getMockProposedConceptResponse(null,getMockProposedConcept(null, "concept 1", "concept 1 description", testConcept1));
		ProposedConceptResponse concept2 = getMockProposedConceptResponse(null,getMockProposedConcept(null, "concept 2", "concept 2 description", testConcept2));
		testPackage.addProposedConcept(concept1);
		testPackage.addProposedConcept(concept2);

		log.info("Before: " + testPackage);
	    service.saveProposedConceptResponsePackage(testPackage);
		log.info("After: " + testPackage);
		Assert.assertTrue(testPackage.getId().intValue() >= 3);
	}
	

	@Test
	public void saveProposedConceptPackageResponse_basicUpdate() throws Exception {
		ProposedConceptResponsePackage testPackage = service.getProposedConceptResponsePackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals("Concept Proposal Package Response 1", testPackage.getName());

		String newName = "New Name";
		testPackage.setName(newName);
		service.saveProposedConceptResponsePackage(testPackage);
		
		testPackage = service.getProposedConceptResponsePackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals(newName, testPackage.getName());
	}

	@Test
	public void saveProposedConceptPackageResponse_updateAddChild() throws Exception {
		ProposedConceptResponsePackage testPackage = service.getProposedConceptResponsePackageById(1);
		Concept testConcept = conceptService.getConcept(3);
		ProposedConceptResponse concept1 = getMockProposedConceptResponse(null,getMockProposedConcept(null, "concept 1", "concept 1 description", testConcept));
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals("Concept Proposal Package Response 1", testPackage.getName());

		String newName = "New Name";
		testPackage.setName(newName);
		testPackage.addProposedConcept(concept1);
		service.saveProposedConceptResponsePackage(testPackage);
		
		testPackage = service.getProposedConceptResponsePackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals(newName, testPackage.getName());
		Assert.assertEquals(4, testPackage.getProposedConcepts().size());
	}

	@Test
	public void saveProposedConceptPackageResponse_updateRemoveChild() throws Exception {
		ProposedConceptResponsePackage testPackage = service.getProposedConceptResponsePackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals("Concept Proposal Package Response 1", testPackage.getName());

		String newName = "New Name";
		ProposedConceptResponse testResponse = (ProposedConceptResponse) testPackage.getProposedConcepts().iterator().next();
		log.debug("Removing proposed concept response: " + testResponse);
		
		testPackage.setName(newName);
		testPackage.removeProposedConcept(testResponse);
		service.saveProposedConceptResponsePackage(testPackage);
		
		testPackage = service.getProposedConceptResponsePackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals(newName, testPackage.getName());
		Assert.assertEquals(2, testPackage.getProposedConcepts().size());
	}

	@Test
	public void deleteProposedConceptPackageResponse_basicDelete() throws Exception {
		ProposedConceptResponsePackage testPackage = service.getProposedConceptResponsePackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertNotNull(testPackage);
	    service.deleteProposedConceptResponsePackage(testPackage);
	    
		testPackage = service.getProposedConceptResponsePackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}

	@Test
	public void deleteProposedConceptPackageResponse_multipleDelete() throws Exception {
		ProposedConceptResponsePackage testPackage = service.getProposedConceptResponsePackageById(1);
		log.info("Before: " + testPackage);
		Assert.assertNotNull(testPackage);
	    service.deleteProposedConceptResponsePackage(testPackage);
		log.info("After: " + testPackage);
	    
	    service.deleteProposedConceptResponsePackage(testPackage);
	}

	@Test
	public void deleteProposedConceptPackageResponse_nullDelete() throws Exception {
		ProposedConceptResponsePackage testPackage = null;
		log.info("Package: " + testPackage);
	    service.deleteProposedConceptResponsePackage(testPackage);
	}
	
	
	
	/*
	 * 2 Nov 12
	 * David
	 * 
	 * Correcting tests that test nullable settings are set correctly
	 */
	
	@Test
	public void saveProposedConceptPackage_noNullFieldsException() {
		ProposedConceptPackage testPackage = getMockProposedConceptPackage(null, "new package");
		testPackage.setDescription(null);
		log.info("Before: " + testPackage);
		service.saveProposedConceptPackage(testPackage);
	}

	@Test(expected = PropertyValueException.class)
	public void saveProposedConceptPackage_nullFieldsException() throws Exception {
		ProposedConceptPackage testPackage = getMockProposedConceptPackage(null, "new package");
		testPackage.setStatus(null);
		log.info("Before: " + testPackage);
	    service.saveProposedConceptPackage(testPackage);
	}
	
	@Test
	public void saveProposedConceptPackageResponse_noNullFieldsException() {
		ProposedConceptResponsePackage testPackage = getMockProposedConceptPackageResponse(null, "new package");
		testPackage.setDescription(null);
		log.info("Before: " + testPackage);
		service.saveProposedConceptResponsePackage(testPackage);
	}

	@Test(expected = PropertyValueException.class)
	public void saveProposedConceptPackageResponse_nullFieldsException() throws Exception {
		ProposedConceptResponsePackage testPackage = getMockProposedConceptPackageResponse(null, "new package");
		testPackage.setStatus(null);
		log.info("Before: " + testPackage);
	    service.saveProposedConceptResponsePackage(testPackage);
	}

}