package com.openmrs.module.cpm.api;

import java.text.SimpleDateFormat;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.PropertyValueException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.ConceptProposalPackage;
import org.openmrs.module.cpm.ConceptProposalPackageResponse;
import org.openmrs.module.cpm.PackageStatus;
import org.openmrs.module.cpm.api.ConceptProposalService;

import com.openmrs.module.cpm.test.CpmBaseContextSensitive;

public class TestConceptProposalService extends CpmBaseContextSensitive {
	
	private static final Log log = LogFactory.getLog(TestConceptProposalService.class);
	private static final String CPM_CORE_DATASET = "org/openmrs/module/cpm/coreTestData.xml";
	private static final String TEST_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	private static final String TEST_DATE_DISPLAY_FORMAT = "yyyy-MM-dd HH:mm:ss.S z G";
			
	protected ConceptProposalService service = null;
	protected SimpleDateFormat formatter = new SimpleDateFormat(TEST_DATE_FORMAT);
	protected SimpleDateFormat comparator = new SimpleDateFormat(TEST_DATE_DISPLAY_FORMAT);

	@Before
	public void before() throws Exception {
		service = Context.getService(ConceptProposalService.class);
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
	protected ConceptProposalPackage getMockConceptProposalPackage(Integer id, String name) {
		ConceptProposalPackage conceptPackage = new ConceptProposalPackage();
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
	protected ConceptProposalPackageResponse getMockConceptProposalPackageResponse(Integer id, String name) {
		ConceptProposalPackageResponse conceptPackageResponse = new ConceptProposalPackageResponse(getMockConceptProposalPackage(id,name));
		conceptPackageResponse.setId(id);
		conceptPackageResponse.setName(name);
		conceptPackageResponse.setVersion(0);
		return conceptPackageResponse;
	}

	@Test
	public void getAllConceptProposalPackages_basicRetrieval() throws Exception {
	    List<ConceptProposalPackage> packages = Context.getService(ConceptProposalService.class).getAllConceptProposalPackages();
		log.info("Retrieved: " + packages.size() + " packages");
		Assert.assertEquals(2, packages.size());
	}

	@Test
	public void getAllConceptProposalPackages_emptyRetrieval() throws Exception {
	    List<ConceptProposalPackage> packages = Context.getService(ConceptProposalService.class).getAllConceptProposalPackages();
	    for (ConceptProposalPackage currentPackage : packages) {
	    	service.deleteConceptProposalPackage(currentPackage);
	    }
		
	    packages = service.getAllConceptProposalPackages();
		log.info("Retrieved: " + packages.size() + " packages");
		Assert.assertEquals(0, packages.size());
	}
	
	@Test
	public void getAllConceptProposalPackages_insertedRetrieval() throws Exception {
		List<ConceptProposalPackage> packages = service.getAllConceptProposalPackages();
		log.info("Before - retrieved: " + packages.size() + " packages");
		Assert.assertEquals(2, packages.size());

		ConceptProposalPackage testPackage = getMockConceptProposalPackage(null, "new package");
	    service.saveConceptProposalPackage(testPackage);
	    packages = service.getAllConceptProposalPackages();
		log.info("After - retrieved: " + packages.size() + " packages");
		Assert.assertEquals(3, packages.size());
	}

	@Test
	public void getConceptProposalPackageById_basicRetrieval() throws Exception {
		ConceptProposalPackage testPackage = service.getConceptProposalPackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals("Concept Proposal Package 1", testPackage.getName());
		Assert.assertEquals("proposer@cpm.com", testPackage.getEmail());
		Assert.assertEquals("d0dd9f30-15e7-11e2-892e-0800200c9a66", testPackage.getUuid());
		Assert.assertEquals("Description for concept proposal package 1", testPackage.getDescription());
		Assert.assertEquals(comparator.format(formatter.parse("2005-01-01 00:01:00.0")),comparator.format(testPackage.getDateCreated()));
		Assert.assertEquals(1,testPackage.getVersion().intValue());
		Assert.assertEquals(PackageStatus.DRAFT,testPackage.getStatus());
		Assert.assertEquals(3, testPackage.getProposedConcepts().size());
		Assert.assertNull(testPackage.getCreatedBy().getName());
	}
	
	@Test
	public void getConceptProposalPackageById_emptyRetrieval() throws Exception {
		ConceptProposalPackage testPackage = service.getConceptProposalPackageById(0);
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}

	@Test
	public void getConceptProposalPackageById_nullRetrieval() throws Exception {
		ConceptProposalPackage testPackage = service.getConceptProposalPackageById(null);
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}
	
	@Test
	public void getConceptProposalPackageByUuid_basicRetrieval() throws Exception {
		ConceptProposalPackage testPackage = service.getConceptProposalPackageByUuid("d0dd9f30-15e7-11e2-892e-0800200c9a66");
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals("Concept Proposal Package 1", testPackage.getName());
	}
	
	@Test
	public void getConceptProposalPackageByUuid_emptyRetrieval() throws Exception {
		ConceptProposalPackage testPackage = service.getConceptProposalPackageByUuid("doesnotexist");
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}

	@Test
	public void getConceptProposalPackageByUuid_nullRetrieval() throws Exception {
		ConceptProposalPackage testPackage = service.getConceptProposalPackageByUuid(null);
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}
	
	@Test
	public void saveConceptProposalPackage_basicSave() throws Exception {
		ConceptProposalPackage testPackage = getMockConceptProposalPackage(null, "new package");
		log.info("Before: " + testPackage);
	    service.saveConceptProposalPackage(testPackage);
		log.info("After: " + testPackage);
		Assert.assertTrue(testPackage.getId().intValue() >= 3);
	}
	
	@Test
	public void saveConceptProposalPackage_basicUpdate() throws Exception {
		ConceptProposalPackage testPackage = service.getConceptProposalPackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals("Concept Proposal Package 1", testPackage.getName());

		String newName = "New Name";
		testPackage.setName(newName);
		service.saveConceptProposalPackage(testPackage);
		
		testPackage = service.getConceptProposalPackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals(newName, testPackage.getName());
	}

	@Test(expected = PropertyValueException.class)
	public void saveConceptProposalPackage_nullFieldsException() throws Exception {
		ConceptProposalPackage testPackage = getMockConceptProposalPackage(null, "new package");
		testPackage.setDescription(null);
		log.info("Before: " + testPackage);
	    service.saveConceptProposalPackage(testPackage);
	}

	@Test
	public void deleteConceptProposalPackage_basicDelete() throws Exception {
		ConceptProposalPackage testPackage = service.getConceptProposalPackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertNotNull(testPackage);
	    service.deleteConceptProposalPackage(testPackage);
	    
		testPackage = service.getConceptProposalPackageById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}

	@Test
	public void deleteConceptProposalPackage_multipleDelete() throws Exception {
		ConceptProposalPackage testPackage = service.getConceptProposalPackageById(1);
		log.info("Before: " + testPackage);
		Assert.assertNotNull(testPackage);
	    service.deleteConceptProposalPackage(testPackage);
		log.info("After: " + testPackage);
	    
	    service.deleteConceptProposalPackage(testPackage);
	}

	@Test
	public void deleteConceptProposalPackage_nullDelete() throws Exception {
		ConceptProposalPackage testPackage = null;
		log.info("Package: " + testPackage);
	    service.deleteConceptProposalPackage(testPackage);
	}

	
	@Test
	public void getAllConceptProposalPackageResponses_basicRetrieval() throws Exception {
	    List<ConceptProposalPackageResponse> packages = service.getAllConceptProposalPackageResponses();
		log.info("Retrieved: " + packages.size() + " packages");
		Assert.assertEquals(2, packages.size());
	}

	@Test
	public void getAllConceptProposalPackageResponses_emptyRetrieval() throws Exception {
	    List<ConceptProposalPackageResponse> packages = service.getAllConceptProposalPackageResponses();
	    for (ConceptProposalPackageResponse currentPackage : packages) {
	    	service.deleteConceptProposalPackageResponse(currentPackage);
	    }
		
	    packages = service.getAllConceptProposalPackageResponses();
		log.info("Retrieved: " + packages.size() + " packages");
		Assert.assertEquals(0, packages.size());
	}
	
	@Test
	public void getAllConceptProposalPackageResponses_insertedRetrieval() throws Exception {
		List<ConceptProposalPackageResponse> packages = service.getAllConceptProposalPackageResponses();
		log.info("Before - retrieved: " + packages.size() + " packages");
		Assert.assertEquals(2, packages.size());

		ConceptProposalPackageResponse testPackage = getMockConceptProposalPackageResponse(null, "new package");
	    service.saveConceptProposalPackageResponse(testPackage);
	    packages = service.getAllConceptProposalPackageResponses();
		log.info("After - retrieved: " + packages.size() + " packages");
		Assert.assertEquals(3, packages.size());
	}

	@Test
	public void getConceptProposalPackageResponseById_basicRetrieval() throws Exception {
		ConceptProposalPackageResponse testPackage = service.getConceptProposalPackageResponseById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals("Concept Proposal Package Response 1", testPackage.getName());
		Assert.assertEquals("proposer@cpm.com", testPackage.getEmail());
		Assert.assertEquals("d0dd9f3c-15e7-11e2-892e-0800200c9a66", testPackage.getUuid());
		Assert.assertEquals("d0dd9f30-15e7-11e2-892e-0800200c9a66", testPackage.getConceptProposalPackageUuid());
		Assert.assertEquals("Description for concept proposal package 1", testPackage.getDescription());
		Assert.assertEquals(comparator.format(formatter.parse("2005-01-01 00:01:00.0")),comparator.format(testPackage.getDateCreated()));
		Assert.assertEquals(1,testPackage.getVersion().intValue());
		Assert.assertEquals(PackageStatus.RECEIVED,testPackage.getStatus());
		Assert.assertEquals(3, testPackage.getProposedConcepts().size());
		Assert.assertNull(testPackage.getCreatedBy().getName());
	}
	
	@Test
	public void getConceptProposalPackageResponseById_emptyRetrieval() throws Exception {
		ConceptProposalPackageResponse testPackage = service.getConceptProposalPackageResponseById(0);
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}

	@Test
	public void getConceptProposalPackageResponseById_nullRetrieval() throws Exception {
		ConceptProposalPackageResponse testPackage = service.getConceptProposalPackageResponseById(null);
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}
	
	@Test
	public void getConceptProposalPackageResponseByProposalUuid_basicRetrieval() throws Exception {
		ConceptProposalPackageResponse testPackage = service.getConceptProposalPackageResponseByProposalUuid("d0dd9f30-15e7-11e2-892e-0800200c9a66");
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals("Concept Proposal Package Response 1", testPackage.getName());
	}
	
	@Test
	public void getConceptProposalPackageResponseByProposalUuid_emptyRetrieval() throws Exception {
		ConceptProposalPackageResponse testPackage = service.getConceptProposalPackageResponseByProposalUuid("doesnotexist");
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}

	@Test
	public void getConceptProposalPackageResponseByProposalUuid_nullRetrieval() throws Exception {
		ConceptProposalPackageResponse testPackage = service.getConceptProposalPackageResponseByProposalUuid(null);
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}
	
	@Test
	public void saveConceptProposalPackageResponse_basicSave() throws Exception {
		ConceptProposalPackageResponse testPackage = getMockConceptProposalPackageResponse(null, "new package");
		log.info("Before: " + testPackage);
	    service.saveConceptProposalPackageResponse(testPackage);
		log.info("After: " + testPackage);
		Assert.assertTrue(testPackage.getId().intValue() >= 3);
	}
	
	@Test
	public void saveConceptProposalPackageResponse_basicUpdate() throws Exception {
		ConceptProposalPackageResponse testPackage = service.getConceptProposalPackageResponseById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals("Concept Proposal Package Response 1", testPackage.getName());

		String newName = "New Name";
		testPackage.setName(newName);
		service.saveConceptProposalPackageResponse(testPackage);
		
		testPackage = service.getConceptProposalPackageResponseById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertEquals(newName, testPackage.getName());
	}

	@Test(expected = PropertyValueException.class)
	public void saveConceptProposalPackageResponse_nullFieldsException() throws Exception {
		ConceptProposalPackageResponse testPackage = getMockConceptProposalPackageResponse(null, "new package");
		testPackage.setDescription(null);
		log.info("Before: " + testPackage);
	    service.saveConceptProposalPackageResponse(testPackage);
	}

	@Test
	public void deleteConceptProposalPackageResponse_basicDelete() throws Exception {
		ConceptProposalPackageResponse testPackage = service.getConceptProposalPackageResponseById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertNotNull(testPackage);
	    service.deleteConceptProposalPackageResponse(testPackage);
	    
		testPackage = service.getConceptProposalPackageResponseById(1);
		log.info("Retrieved: " + testPackage);
		Assert.assertNull(testPackage);
	}

	@Test
	public void deleteConceptProposalPackageResponse_multipleDelete() throws Exception {
		ConceptProposalPackageResponse testPackage = service.getConceptProposalPackageResponseById(1);
		log.info("Before: " + testPackage);
		Assert.assertNotNull(testPackage);
	    service.deleteConceptProposalPackageResponse(testPackage);
		log.info("After: " + testPackage);
	    
	    service.deleteConceptProposalPackageResponse(testPackage);
	}

	@Test
	public void deleteConceptProposalPackageResponse_nullDelete() throws Exception {
		ConceptProposalPackageResponse testPackage = null;
		log.info("Package: " + testPackage);
	    service.deleteConceptProposalPackageResponse(testPackage);
	}
	
}