package org.openmrs.module.conceptreview.api;

import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.PropertyValueException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.PackageStatus;
import org.openmrs.module.conceptpropose.ProposalStatus;
import org.openmrs.module.conceptreview.ProposedConceptReview;
import org.openmrs.module.conceptreview.ProposedConceptReviewAnswer;
import org.openmrs.module.conceptreview.ProposedConceptReviewNumeric;
import org.openmrs.module.conceptreview.ProposedConceptReviewPackage;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestProposedConceptReviewService extends BaseModuleContextSensitiveTest {

    private static final Log log = LogFactory.getLog(TestProposedConceptReviewService.class);
    private static final String TEST_DATASET = "org/openmrs/module/conceptreview/testData.xml";

    protected ProposedConceptReviewService service = null;
    protected ConceptService conceptService = null;

    @Before
    public void before() throws Exception {
        service = Context.getService(ProposedConceptReviewService.class);
        conceptService = Context.getConceptService();
        log.info("Loading the core Concept Proposal Module test data set");
        executeDataSet(TEST_DATASET);
        log.info("Loading of the core Concept Proposal Module test data set complete");
    }

    @After
    public void after() throws Exception {
        deleteAllData();
    }

    /**
     * Creates and returns new mock concept package review
     *
     * @return new mock concept package review
     */
    protected ProposedConceptReviewPackage getMockProposedConceptPackageReview(Integer id, String name) {
        ProposedConceptReviewPackage conceptPackageReview = new ProposedConceptReviewPackage();
        conceptPackageReview.setId(id);
        conceptPackageReview.setName(name);
        conceptPackageReview.setDescription("description");
        conceptPackageReview.setEmail("test@test.com");
        conceptPackageReview.setId(id);
        conceptPackageReview.setName(name);
        conceptPackageReview.setVersion(0);
        return conceptPackageReview;
    }

    /**
     * Creates and returns a new mock concept
     *
     * @throws Exception
     */
    protected ProposedConceptReview getMockProposedConceptReview() {
        ProposedConceptReview proposedConceptReview = new ProposedConceptReview();
        return proposedConceptReview;
    }



    @Test
    public void getAllProposedConceptPackageReviews_basicRetrieval() throws Exception {
        List<ProposedConceptReviewPackage> packages = service.getAllProposedConceptReviewPackages();
        log.info("Retrieved: " + packages.size() + " packages");
        Assert.assertEquals(2, packages.size());
    }

    @Test
    public void getAllProposedConceptPackageReviews_emptyRetrieval() throws Exception {
        List<ProposedConceptReviewPackage> packages = service.getAllProposedConceptReviewPackages();
        for (ProposedConceptReviewPackage currentPackage : packages) {
            service.deleteProposedConceptReviewPackage(currentPackage);
        }

        packages = service.getAllProposedConceptReviewPackages();
        log.info("Retrieved: " + packages.size() + " packages");
        Assert.assertEquals(0, packages.size());
    }

    @Test
    public void getAllProposedConceptPackageReviews_insertedRetrieval() throws Exception {
        List<ProposedConceptReviewPackage> packages = service.getAllProposedConceptReviewPackages();
        log.info("Before - retrieved: " + packages.size() + " packages");
        Assert.assertEquals(2, packages.size());

        ProposedConceptReviewPackage testPackage = getMockProposedConceptPackageReview(null, "new package");
        service.saveProposedConceptReviewPackage(testPackage);
        packages = service.getAllProposedConceptReviewPackages();
        log.info("After - retrieved: " + packages.size() + " packages");
        Assert.assertEquals(3, packages.size());
    }

    @Test
    public void getProposedConceptPackageReviewById_basicRetrieval() throws Exception {
        ProposedConceptReviewPackage testPackage = service.getProposedConceptReviewPackageById(1);
        log.info("Retrieved: " + testPackage);
        Assert.assertEquals("Concept Proposal Package Review 1", testPackage.getName());
        Assert.assertEquals("proposer@conceptreview.com", testPackage.getEmail());
        Assert.assertEquals("d0dd9f3c-15e7-11e2-892e-0800200c9a66", testPackage.getUuid());
        Assert.assertEquals("d0dd9f30-15e7-11e2-892e-0800200c9a66", testPackage.getProposedConceptPackageUuid());
        Assert.assertEquals("Description for concept proposal package 1", testPackage.getDescription());
        Assert.assertEquals(1, testPackage.getVersion().intValue());
        Assert.assertEquals(PackageStatus.RECEIVED, testPackage.getStatus());
        Assert.assertEquals(3, testPackage.getProposedConcepts().size());
        Assert.assertNull(testPackage.getCreator().getName());
    }

    @Test
    public void getProposedConceptPackageReviewById_emptyRetrieval() throws Exception {
        ProposedConceptReviewPackage testPackage = service.getProposedConceptReviewPackageById(0);
        log.info("Retrieved: " + testPackage);
        Assert.assertNull(testPackage);
    }

    @Test
    public void getProposedConceptPackageReviewById_nullRetrieval() throws Exception {
        ProposedConceptReviewPackage testPackage = service.getProposedConceptReviewPackageById(null);
        log.info("Retrieved: " + testPackage);
        Assert.assertNull(testPackage);
    }

    @Test
    public void getProposedConceptPackageReviewByProposalUuid_basicRetrieval() throws Exception {
        ProposedConceptReviewPackage testPackage = service.getProposedConceptReviewPackageByProposalUuid("d0dd9f30-15e7-11e2-892e-0800200c9a66");
        log.info("Retrieved: " + testPackage);
        Assert.assertEquals("Concept Proposal Package Review 1", testPackage.getName());
    }

    @Test
    public void getProposedConceptPackageReviewByProposalUuid_emptyRetrieval() throws Exception {
        ProposedConceptReviewPackage testPackage = service.getProposedConceptReviewPackageByProposalUuid("doesnotexist");
        log.info("Retrieved: " + testPackage);
        Assert.assertNull(testPackage);
    }

    @Test
    public void getProposedConceptPackageReviewByProposalUuid_nullRetrieval() throws Exception {
        ProposedConceptReviewPackage testPackage = service.getProposedConceptReviewPackageByProposalUuid(null);
        log.info("Retrieved: " + testPackage);
        Assert.assertNull(testPackage);
    }

    @Test
    public void saveProposedConceptPackageReview_basicSave() throws Exception {
        ProposedConceptReviewPackage testPackage = getMockProposedConceptPackageReview(null, "new package");
        log.info("Before: " + testPackage);
        service.saveProposedConceptReviewPackage(testPackage);
        log.info("After: " + testPackage);
        Assert.assertTrue(testPackage.getId().intValue() >= 3);
    }

    @Test
    public void saveProposedConceptPackageReview_saveWithChildren() throws Exception {
        ProposedConceptReviewPackage testPackage = getMockProposedConceptPackageReview(null, "new package");
        Concept testConcept1 = conceptService.getConcept(3);
        Concept testConcept2 = conceptService.getConcept(4);
        log.warn("Concept 1: " + testConcept1);
        log.warn("Concept 2: " + testConcept2);
        ProposedConceptReview concept1 = getMockProposedConceptReview();
        ProposedConceptReview concept2 = getMockProposedConceptReview();
        testPackage.addProposedConcept(concept1);
        testPackage.addProposedConcept(concept2);

        log.info("Before: " + testPackage);
        service.saveProposedConceptReviewPackage(testPackage);
        log.info("After: " + testPackage);
        Assert.assertTrue(testPackage.getId().intValue() >= 3);
    }


    @Test
    public void saveProposedConceptPackageReview_basicUpdate() throws Exception {
        ProposedConceptReviewPackage testPackage = service.getProposedConceptReviewPackageById(1);
        log.info("Retrieved: " + testPackage);
        Assert.assertEquals("Concept Proposal Package Review 1", testPackage.getName());

        String newName = "New Name";
        testPackage.setName(newName);
        service.saveProposedConceptReviewPackage(testPackage);

        testPackage = service.getProposedConceptReviewPackageById(1);
        log.info("Retrieved: " + testPackage);
        Assert.assertEquals(newName, testPackage.getName());
    }

    @Test
    public void saveProposedConceptPackageReview_updateAddChild() throws Exception {
        ProposedConceptReviewPackage testPackage = service.getProposedConceptReviewPackageById(1);
        Concept testConcept = conceptService.getConcept(3);
        ProposedConceptReview concept1 = getMockProposedConceptReview();
        log.info("Retrieved: " + testPackage);
        Assert.assertEquals("Concept Proposal Package Review 1", testPackage.getName());

        String newName = "New Name";
        testPackage.setName(newName);
        testPackage.addProposedConcept(concept1);
        service.saveProposedConceptReviewPackage(testPackage);

        testPackage = service.getProposedConceptReviewPackageById(1);
        log.info("Retrieved: " + testPackage);
        Assert.assertEquals(newName, testPackage.getName());
        Assert.assertEquals(4, testPackage.getProposedConcepts().size());
    }

    @Test
    public void saveProposedConceptPackageReview_updateRemoveChild() throws Exception {
        ProposedConceptReviewPackage testPackage = service.getProposedConceptReviewPackageById(1);
        log.info("Retrieved: " + testPackage);
        Assert.assertEquals("Concept Proposal Package Review 1", testPackage.getName());

        String newName = "New Name";
        ProposedConceptReview testReview = (ProposedConceptReview) testPackage.getProposedConcepts().iterator().next();
        log.debug("Removing proposed concept review: " + testReview);

        testPackage.setName(newName);
        testPackage.removeProposedConcept(testReview);
        service.saveProposedConceptReviewPackage(testPackage);

        testPackage = service.getProposedConceptReviewPackageById(1);
        log.info("Retrieved: " + testPackage);
        Assert.assertEquals(newName, testPackage.getName());
        Assert.assertEquals(2, testPackage.getProposedConcepts().size());
    }

    @Test
    public void deleteProposedConceptPackageReview_basicDelete() throws Exception {
        ProposedConceptReviewPackage testPackage = service.getProposedConceptReviewPackageById(1);
        log.info("Retrieved: " + testPackage);
        Assert.assertNotNull(testPackage);
        service.deleteProposedConceptReviewPackage(testPackage);

        testPackage = service.getProposedConceptReviewPackageById(1);
        log.info("Retrieved: " + testPackage);
        Assert.assertNull(testPackage);
    }

    @Test
    public void deleteProposedConceptPackageReview_multipleDelete() throws Exception {
        ProposedConceptReviewPackage testPackage = service.getProposedConceptReviewPackageById(1);
        log.info("Before: " + testPackage);
        Assert.assertNotNull(testPackage);
        service.deleteProposedConceptReviewPackage(testPackage);
        log.info("After: " + testPackage);

        service.deleteProposedConceptReviewPackage(testPackage);
    }

    @Test
    public void deleteProposedConceptPackageReview_nullDelete() throws Exception {
        ProposedConceptReviewPackage testPackage = null;
        log.info("Package: " + testPackage);
        service.deleteProposedConceptReviewPackage(testPackage);
    }

	
	
	/*
	 * 2 Nov 12
	 * David
	 * 
	 * Correcting tests that test nullable settings are set correctly
	 */

    @Test
    public void saveProposedConceptPackageReview_noNullFieldsException() {
        ProposedConceptReviewPackage testPackage = getMockProposedConceptPackageReview(null, "new package");
        testPackage.setDescription(null);
        log.info("Before: " + testPackage);
        service.saveProposedConceptReviewPackage(testPackage);
    }

    @Test(expected = PropertyValueException.class)
    public void saveProposedConceptPackageReview_nullFieldsException() throws Exception {
        ProposedConceptReviewPackage testPackage = getMockProposedConceptPackageReview(null, "new package");
        testPackage.setStatus(null);
        log.info("Before: " + testPackage);
        service.saveProposedConceptReviewPackage(testPackage);
    }
	@Test
	public void saveProposedConceptPackageReview_anUnprocessedConceptShouldLeavePackageAsIncompleted() {
		ProposedConceptReviewPackage testPackage = getMockProposedConceptPackageReview(null, "new package");

		ProposedConceptReview conceptReview = getMockProposedConceptReview();
		conceptReview.setStatus(ProposalStatus.RECEIVED);
		testPackage.addProposedConcept(conceptReview);

		testPackage.setStatus(PackageStatus.RECEIVED);
		log.info("Before: " + testPackage);

		service.saveProposedConceptReviewPackage(testPackage);
		final ProposedConceptReviewPackage retrievedPackage = service.getProposedConceptReviewPackageById(testPackage.getId());

		assertThat(retrievedPackage.getStatus(), is(equalTo(PackageStatus.RECEIVED)));
	}
	@Test
	public void saveProposedConceptPackageReview_unprocessedConceptsShouldLeavePackageAsIncompleted() {
		ProposedConceptReviewPackage testPackage = getMockProposedConceptPackageReview(null, "new package");

		ProposedConceptReview conceptReview = getMockProposedConceptReview();
		conceptReview.setStatus(ProposalStatus.RECEIVED);
		testPackage.addProposedConcept(conceptReview);

		ProposedConceptReview conceptReview2 = getMockProposedConceptReview();
		conceptReview2.setStatus(ProposalStatus.CLOSED_EXISTING);
		testPackage.addProposedConcept(conceptReview2);

		testPackage.setStatus(PackageStatus.RECEIVED);
		log.info("Before: " + testPackage);

		service.saveProposedConceptReviewPackage(testPackage);
		final ProposedConceptReviewPackage retrievedPackage = service.getProposedConceptReviewPackageById(testPackage.getId());

		assertThat(retrievedPackage.getStatus(), is(equalTo(PackageStatus.RECEIVED)));
	}
	@Test
	public void saveProposedConceptPackageReview_aProcessedConceptShouldMarkPackageAsCompleted() {
		ProposedConceptReviewPackage testPackage = getMockProposedConceptPackageReview(null, "new package");

		ProposedConceptReview conceptReview = getMockProposedConceptReview();
		conceptReview.setStatus(ProposalStatus.CLOSED_EXISTING);
		testPackage.addProposedConcept(conceptReview);

		testPackage.setStatus(PackageStatus.RECEIVED);
		log.info("Before: " + testPackage);

		service.saveProposedConceptReviewPackage(testPackage);
		final ProposedConceptReviewPackage retrievedPackage = service.getProposedConceptReviewPackageById(testPackage.getId());

		assertThat(retrievedPackage.getStatus(), is(equalTo(PackageStatus.CLOSED)));
	}
	@Test
	public void saveProposedConceptPackageReview_allProcessedConceptsShouldMarkPackageAsCompleted() {
		ProposedConceptReviewPackage testPackage = getMockProposedConceptPackageReview(null, "new package");

		ProposedConceptReview conceptReview = getMockProposedConceptReview();
		conceptReview.setStatus(ProposalStatus.CLOSED_EXISTING);
		testPackage.addProposedConcept(conceptReview);

		ProposedConceptReview conceptReview2 = getMockProposedConceptReview();
		conceptReview2.setStatus(ProposalStatus.CLOSED_NEW);
		testPackage.addProposedConcept(conceptReview2);


		testPackage.setStatus(PackageStatus.RECEIVED);
		log.info("Before: " + testPackage);
		service.saveProposedConceptReviewPackage(testPackage);
		final ProposedConceptReviewPackage retrievedPackage = service.getProposedConceptReviewPackageById(testPackage.getId());

		assertThat(retrievedPackage.getStatus(), is(equalTo(PackageStatus.CLOSED)));
	}

    @Test
    public void saveAndFetchProposedConceptReviewPackage_shouldMatchPersistedFields() {

        ProposedConceptReviewPackage testPackage = new ProposedConceptReviewPackage();
        testPackage.setName("name");
        testPackage.setEmail("asdf@asdf.com");
        ProposedConceptReview conceptReview = new ProposedConceptReview();
        conceptReview.setComment("This is a proposer's comment");

        final ConceptDatatype datatype = Context.getConceptService().getConceptDatatypeByUuid(ConceptDatatype.BOOLEAN_UUID);
        conceptReview.setDatatype(datatype);

        ConceptClass conceptClass = Context.getConceptService().getConceptClassByName("Test");
        conceptReview.setConceptClass(conceptClass);

        conceptReview.setReviewComment("This is a reviewer's comment");
        testPackage.addProposedConcept(conceptReview);
        service.saveProposedConceptReviewPackage(testPackage);

        final ProposedConceptReviewPackage retrievedPackage = service.getProposedConceptReviewPackageById(testPackage.getId());
        ArrayList<ProposedConceptReview> reviews = new ArrayList<ProposedConceptReview>(retrievedPackage.getProposedConcepts());

        assertThat(retrievedPackage.getName(), is(equalTo("name")));
        assertThat(retrievedPackage.getEmail(), is(equalTo("asdf@asdf.com")));
        assertThat(reviews.get(0).getComment(), is(equalTo("This is a proposer's comment")));
        assertThat(reviews.get(0).getDatatype(), is(equalTo(datatype)));
        assertThat(reviews.get(0).getConceptClass(), is(equalTo(conceptClass)));
        assertThat(reviews.get(0).getReviewComment(), is(equalTo("This is a reviewer's comment")));
    }

    @Test
    public void saveAndFetchProposedConceptWithNumericDatatype_shouldMatchNumericData() {

        ProposedConceptReviewPackage testPackage = new ProposedConceptReviewPackage();
        testPackage.setName("name");
        testPackage.setEmail("asdf@asdf.com");
        ProposedConceptReview conceptReview = new ProposedConceptReview();
        conceptReview.setComment("This is a proposer's comment");

        final ConceptDatatype datatype = Context.getConceptService().getConceptDatatypeByUuid(ConceptDatatype.NUMERIC_UUID);
        conceptReview.setDatatype(datatype);

        ProposedConceptReviewNumeric conceptNumeric = new ProposedConceptReviewNumeric();
        conceptNumeric.setHiAbsolute(20.0);
        conceptNumeric.setHiCritical(21.0);
        conceptNumeric.setHiNormal(19.0);
        conceptNumeric.setLowAbsolute(18.0);
        conceptNumeric.setLowNormal(17.0);
        conceptNumeric.setLowCritical(16.0);
        conceptNumeric.setPrecise(true);
        conceptNumeric.setUnits("ppm");
        conceptReview.setNumericDetails(conceptNumeric);

        testPackage.addProposedConcept(conceptReview);
        service.saveProposedConceptReviewPackage(testPackage);

        final ProposedConceptReviewPackage retrievedPackage = service.getProposedConceptReviewPackageById(testPackage.getId());
        ArrayList<ProposedConceptReview> reviews = new ArrayList<ProposedConceptReview>(retrievedPackage.getProposedConcepts());
        assertThat(reviews.get(0).getNumericDetails().getHiAbsolute(), is(equalTo(20.0)));
        assertThat(reviews.get(0).getNumericDetails().getHiCritical(), is(equalTo(21.0)));
        assertThat(reviews.get(0).getNumericDetails().getHiNormal(), is(equalTo(19.0)));
        assertThat(reviews.get(0).getNumericDetails().getLowAbsolute(), is(equalTo(18.0)));
        assertThat(reviews.get(0).getNumericDetails().getLowNormal(), is(equalTo(17.0)));
        assertThat(reviews.get(0).getNumericDetails().getLowCritical(), is(equalTo(16.0)));
        assertThat(reviews.get(0).getNumericDetails().getPrecise(), is(true));
        assertThat(reviews.get(0).getNumericDetails().getUnits(), is(equalTo("ppm")));

    }

    @Test
    public void saveAndFetchProposedConceptWithCodedDatatype_shouldMatchCodedData() {

        ProposedConceptReviewPackage testPackage = new ProposedConceptReviewPackage();
        testPackage.setName("name");
        testPackage.setEmail("asdf@asdf.com");
        ProposedConceptReview conceptReview = new ProposedConceptReview();
        conceptReview.setComment("This is a proposer's comment");

        final ConceptDatatype datatype = Context.getConceptService().getConceptDatatypeByUuid(ConceptDatatype.CODED_UUID);
        conceptReview.setDatatype(datatype);

        List<ProposedConceptReviewAnswer> answerList = new ArrayList<ProposedConceptReviewAnswer>();
        ProposedConceptReviewAnswer conceptAnswer = new ProposedConceptReviewAnswer();
        conceptAnswer.setAnswerConceptUuid("concept");
        conceptAnswer.setAnswerDrugUuid("drug");
        conceptAnswer.setSortWeight(1.0);
        answerList.add(conceptAnswer);
        conceptReview.setCodedDetails(answerList);

        testPackage.addProposedConcept(conceptReview);
        service.saveProposedConceptReviewPackage(testPackage);

        final ProposedConceptReviewPackage retrievedPackage = service.getProposedConceptReviewPackageById(testPackage.getId());
        ArrayList<ProposedConceptReview> reviews = new ArrayList<ProposedConceptReview>(retrievedPackage.getProposedConcepts());
        assertThat(reviews.get(0).getCodedDetails().get(0).getAnswerConceptUuid(), is("concept"));
        assertThat(reviews.get(0).getCodedDetails().get(0).getAnswerDrugUuid(), is("drug"));
        assertThat(reviews.get(0).getCodedDetails().get(0).getSortWeight(), is(1.0));

    }

}