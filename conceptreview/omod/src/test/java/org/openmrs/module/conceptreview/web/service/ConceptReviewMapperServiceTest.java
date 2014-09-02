package org.openmrs.module.conceptreview.web.service;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.dozer.DozerBeanMapper;
import org.dozer.MappingProcessor;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.ConceptName;
import org.openmrs.api.ConceptNameType;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.ProposalStatus;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptReviewDto;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptReviewPackageDto;
import org.openmrs.module.conceptpropose.web.dto.SubmissionDto;
import org.openmrs.module.conceptpropose.web.dto.concept.AnswerDto;
import org.openmrs.module.conceptpropose.web.dto.concept.DescriptionDto;
import org.openmrs.module.conceptpropose.web.dto.concept.NameDto;
import org.openmrs.module.conceptpropose.web.dto.concept.NumericDto;
import org.openmrs.module.conceptreview.*;
import org.openmrs.module.conceptreview.api.ProposedConceptReviewService;
import org.openmrs.util.LocaleUtility;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Context.class, LocaleUtility.class})
public class ConceptReviewMapperServiceTest {

	@Mock
	private ProposedConceptReviewService service;

	@Mock
	private ConceptService conceptService;

	@Mock
	private ProposedConceptReviewPackage conceptReviewPackage;

	@Mock
	private ConceptClass conceptClass;

	@Mock
	private Concept concept;

	@Mock
	private ConceptDatatype datatype;

	private Integer proposedConceptReviewPackageId = 1;

	private ConceptReviewMapperService mapperService;

	private Locale english = new Locale("en");

	@Before
	public void before() throws Exception {
		// uncomment to enable debug logging for Dozer
//		LogManager.getLogger(DozerBeanMapper.class).setLevel(Level.DEBUG);
//		LogManager.getLogger(MappingProcessor.class).setLevel(Level.DEBUG);

		DozerBeanMapper mapper = new DozerBeanMapper();
		mapperService = new ConceptReviewMapperService(mapper);
		List<String> files = new ArrayList<String>();
		files.add("conceptreview-dozer-mappings.xml");
		mapper.setMappingFiles(files);

		mockStatic(Context.class);
		mockStatic(LocaleUtility.class);

		PowerMockito.when(Context.class, "getService", ProposedConceptReviewService.class).thenReturn(service);
		when(service.getProposedConceptReviewPackageById(proposedConceptReviewPackageId)).thenReturn(conceptReviewPackage);


		// for dozer
		PowerMockito.when(Context.class, "getConceptService").thenReturn(conceptService);
		when(conceptService.getConceptClassByUuid(anyString())).thenReturn(conceptClass);
		when(conceptService.getConcept(anyString())).thenReturn(concept);
		when(conceptService.getConceptDatatypeByUuid(anyString())).thenReturn(datatype);
	}

	//
	// RESTful request from proposal server to review server
	//

	@Test
	public void convertSubmissionDtoToProposedConceptReviewPackage_regularProposal_shouldBindToDomain() throws Exception {

		whenNew(ProposedConceptReviewPackage.class).withNoArguments().thenReturn(conceptReviewPackage);
		final SubmissionDto dto = setupRegularProposalFixtureWithJson();


		ProposedConceptReviewPackage value = mapperService.convertSubmissionDtoToProposedConceptReviewPackage(dto);


		assertThat(value.getName(), is("A proposal"));
		assertThat(value.getEmail(), is("asdf@asdf.com"));
		assertThat(value.getDescription(), is("A description"));

		final List<ProposedConceptReview> proposedConcepts = new ArrayList<ProposedConceptReview>(value.getProposedConcepts());
		final ProposedConceptReview ProposedConceptReview = proposedConcepts.get(0);
		assertThat(ProposedConceptReview.getProposedConceptUuid(), is("concept-uuid"));
		assertThat(ProposedConceptReview.getComment(), is("some comment"));
		assertThat(ProposedConceptReview.getConceptClass(), is(conceptClass));
		assertThat(ProposedConceptReview.getDatatype(), is(datatype));

		final List<ProposedConceptReviewName> names = ProposedConceptReview.getNames();
		assertThat(names.size(), is(1));
		assertThat(names.get(0).getName(), is("Concept name"));

		final List<ProposedConceptReviewDescription> descriptions = ProposedConceptReview.getDescriptions();
		assertThat(descriptions.size(), is(1));
		assertThat(descriptions.get(0).getDescription(), is("Concept description"));
	}

	@Test
	public void convertSubmissionDtoToProposedConceptReviewPackage_numericProposal_shouldBindToDomain() throws Exception {

		whenNew(ProposedConceptReviewPackage.class).withNoArguments().thenReturn(conceptReviewPackage);
		final SubmissionDto dto = setupNumericProposalFixture();

		ProposedConceptReviewPackage value = mapperService.convertSubmissionDtoToProposedConceptReviewPackage(dto);

		final List<ProposedConceptReview> proposedConcepts = new ArrayList<ProposedConceptReview>(value.getProposedConcepts());
		final ProposedConceptReview ProposedConceptReview = proposedConcepts.get(0);

		final ProposedConceptReviewNumeric numericDetails = ProposedConceptReview.getNumericDetails();
		assertThat(numericDetails.getUnits(), is("ml"));
		assertThat(numericDetails.getHiNormal(), is(100.5));
		assertThat(numericDetails.getHiCritical(), is(110.0));
		assertThat(numericDetails.getHiAbsolute(), is(1000.0));
		assertThat(numericDetails.getLowNormal(), is(20.3));
		assertThat(numericDetails.getLowCritical(), is(15.0));
		assertThat(numericDetails.getLowAbsolute(), is(0.0));
	}

	@Test
	public void convertSubmissionDtoToProposedConceptReviewPackage_codedProposal_shouldBindToDomain() throws IOException {

		SubmissionDto dto = setupCodedProposalFixture();


		final ProposedConceptReviewPackage aPackage = mapperService.convertSubmissionDtoToProposedConceptReviewPackage(dto);


		ArrayList<ProposedConceptReview> proposedConceptReviews = new ArrayList<ProposedConceptReview>(aPackage.getProposedConcepts());
		assertThat(proposedConceptReviews.size(), is(1));
		final ProposedConceptReview proposedConceptReview = proposedConceptReviews.get(0);
		assertThat(proposedConceptReview.getCodedDetails().size(), is(1));
	}

	private SubmissionDto setupCodedProposalFixture() throws IOException {
		final String fixture =
				"{" +
						"  'name': 'A proposal'," +
						"  'email': 'asdf@asdf.com'," +
						"  'description': 'A description'," +
						"  'concepts': [" +
						"    {" +
						"      'uuid': 'concept-uuid'," +
						"      'conceptClass': 'concept-class-uuid'," +
						"      'datatype': '8d4a4488-c2cc-11de-8d13-0010c6dffd0f'," +
						"      'comment': 'some comment'," +
						"      'names': [" +
						"        {" +
						"          'name': 'Concept name'," +
						"          'locale': 'en'" +
						"        }" +
						"      ]," +
						"      'descriptions': [" +
						"        {" +
						"          'description': 'Concept description'," +
						"          'locale': 'en'" +
						"        }" +
						"      ]," +
						"      'answers': [" +
						"        {" +
						"          'conceptUuid': 'some-concept-uuid'," +
						"          'answerConceptUuid': 'some-answer-concept-uuid'," +
						"          'answerDrugUuid': 'some-answer-drug-uuid'," +
						"          'sortWeight': '1'" +
						"        }" +
						"      ]" +
						"    }" +
						"  ]" +
						"}";
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(fixture.replace("'", "\""), SubmissionDto.class);
	}

	private SubmissionDto setupNumericProposalFixture() throws IOException {
		final String fixture =
				"{" +
						"  'name': 'A proposal'," +
						"  'email': 'asdf@asdf.com'," +
						"  'description': 'A description'," +
						"  'concepts': [" +
						"    {" +
						"      'uuid': 'concept-uuid'," +
						"      'conceptClass': 'concept-class-uuid'," +
						"      'datatype': '8d4a4488-c2cc-11de-8d13-0010c6dffd0f'," +
						"      'comment': 'some comment'," +
						"      'names': [" +
						"        {" +
						"          'name': 'Concept name'," +
						"          'locale': 'en'" +
						"        }" +
						"      ]," +
						"      'descriptions': [" +
						"        {" +
						"          'description': 'Concept description'," +
						"          'locale': 'en'" +
						"        }" +
						"      ]," +
						"      'numericDetails': {" +
						"        'units': 'ml'," +
						"        'precise': true," +
						"        'hiNormal': 100.5," +
						"        'hiCritical': 110," +
						"        'hiAbsolute': 1000," +
						"        'lowNormal': 20.3," +
						"        'lowCritical': 15," +
						"        'lowAbsolute': 0" +
						"      }" +
						"    }" +
						"  ]" +
						"}";
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(fixture.replace("'", "\""), SubmissionDto.class);
	}

	private SubmissionDto setupRegularProposalFixtureWithJson() throws Exception {

		String regularFixture =
				"{" +
						"  'name': 'A proposal'," +
						"  'email': 'asdf@asdf.com'," +
						"  'description': 'A description'," +
						"  'concepts': [" +
						"    {" +
						"      'uuid': 'concept-uuid'," +
						"      'conceptClass': 'concept-class-uuid'," +
						"      'datatype': 'datatype-uuid'," +
						"      'comment': 'some comment'," +
						"      'names': [" +
						"        {" +
						"          'name': 'Concept name'," +
						"          'locale': 'en'" +
						"        }" +
						"      ]," +
						"      'descriptions': [" +
						"        {" +
						"          'description': 'Concept description'," +
						"          'locale': 'en'" +
						"        }" +
						"      ]" +
						"    }" +
						"  ]" +
						"}";

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(regularFixture.replace("'", "\""), SubmissionDto.class);
	}


	//
	// RESTful requests from ReviewController to browser
	//

	@Test
	public void convertProposedConceptReviewPackageToProposedConceptReviewDto_regularProposal_shouldBind() {

		// "LOWER EXTREMITIES" / "Legs" concept, "N/A" datatype, "Anatomy" class

		ProposedConceptReviewPackage reviewPackage = new ProposedConceptReviewPackage();
		reviewPackage.setId(1);
		reviewPackage.setName("Some name");
		reviewPackage.setDescription("Some description");
		reviewPackage.setEmail("asdf@asdf.com");
		reviewPackage.setDateCreated(new Date());
		ProposedConceptReview proposedConcept = new ProposedConceptReview();
		proposedConcept.setId(2);
		ProposedConceptReviewName preferredName = new ProposedConceptReviewName();
		preferredName.setName("LOWER EXTREMITIES");
		preferredName.setType(ConceptNameType.FULLY_SPECIFIED);
		final ArrayList<ProposedConceptReviewName> names = newArrayList();
		proposedConcept.setNames(names);
		names.add(preferredName);
		ProposedConceptReviewName synonym = new ProposedConceptReviewName();
		synonym.setName("Legs");
		names.add(synonym);
		ProposedConceptReviewDescription description = new ProposedConceptReviewDescription();
		description.setDescription("Anatomic location.");
		ArrayList<ProposedConceptReviewDescription> descriptions = newArrayList();
		descriptions.add(description);
		proposedConcept.setDescriptions(descriptions);
		ConceptClass aClass = new ConceptClass();
		aClass.setName("Anatomy");
		proposedConcept.setConceptClass(aClass);
		ConceptDatatype aDatatype = new ConceptDatatype();
		aDatatype.setName("N/A");
		proposedConcept.setDatatype(aDatatype);
		proposedConcept.setStatus(ProposalStatus.CLOSED_EXISTING);
		proposedConcept.setComment("The proposer's comment");
		proposedConcept.setReviewComment("The reviewer's comment");
		Concept existingConcept = new Concept();
		existingConcept.setId(123);
		proposedConcept.setConcept(existingConcept);
		reviewPackage.addProposedConcept(proposedConcept);


		final ProposedConceptReviewPackageDto dto = mapperService.convertProposedConceptReviewPackageToProposedConceptReviewDto(reviewPackage);


		assertThat(dto.getId(), is(1));
		assertThat(dto.getName(), is("Some name"));
		assertThat(dto.getDescription(), is("Some description"));
		assertThat(dto.getEmail(), is("asdf@asdf.com"));
		assertThat(dto.getAge(), is("0"));
		final ArrayList<ProposedConceptReviewDto> concepts = newArrayList(dto.getConcepts());
		assertThat(concepts.size(), is(1));
		final ProposedConceptReviewDto conceptReviewDto = concepts.get(0);
		assertThat(conceptReviewDto.getId(), is(2));
		assertThat(conceptReviewDto.getConceptId(), is(123));
		assertThat(conceptReviewDto.getPreferredName(), is("LOWER EXTREMITIES"));
		final ArrayList<NameDto> nameDtos = newArrayList(conceptReviewDto.getNames());
		assertThat(nameDtos.size(), is(2));
		assertThat(nameDtos.get(0).getName(), is("LOWER EXTREMITIES"));
		assertThat(nameDtos.get(1).getName(), is("Legs"));
		final ArrayList<DescriptionDto> descriptionDtos = newArrayList(conceptReviewDto.getDescriptions());
		assertThat(descriptionDtos.size(), is(1));
		assertThat(descriptionDtos.get(0).getDescription(), is("Anatomic location."));
		assertThat(conceptReviewDto.getDatatype(), is("N/A"));
		assertThat(conceptReviewDto.getConceptClass(), is("Anatomy"));
		assertThat(conceptReviewDto.getStatus(), is(ProposalStatus.CLOSED_EXISTING));
		assertThat(conceptReviewDto.getComment(), is("The proposer's comment"));
		assertThat(conceptReviewDto.getReviewComment(), is("The reviewer's comment"));
	}

	@Test
	public void convertProposedConceptReviewPackageToProposedConceptReviewDto_numericProposal_shouldBind() {

		ProposedConceptReviewPackage reviewPackage = new ProposedConceptReviewPackage();
		ProposedConceptReview proposedConcept = new ProposedConceptReview();
		ProposedConceptReviewNumeric numericDetails = new ProposedConceptReviewNumeric();
		numericDetails.setHiNormal(10.0);
		numericDetails.setHiAbsolute(11.0);
		numericDetails.setHiCritical(12.0);
		numericDetails.setLowNormal(5.0);
		numericDetails.setLowAbsolute(4.0);
		numericDetails.setLowCritical(3.0);
		numericDetails.setPrecise(true);
		numericDetails.setUnits("ml");
		proposedConcept.setNumericDetails(numericDetails);
		ConceptDatatype numericDatatype = new ConceptDatatype();
		numericDatatype.setName("Numeric");
		proposedConcept.setDatatype(numericDatatype);
		reviewPackage.addProposedConcept(proposedConcept);


		final ProposedConceptReviewPackageDto dto = mapperService.convertProposedConceptReviewPackageToProposedConceptReviewDto(reviewPackage);


		final ProposedConceptReviewDto conceptReviewDto = dto.getConcepts().get(0);
		assertThat(conceptReviewDto.getDatatype(), is("Numeric"));
		final NumericDto numericDto = conceptReviewDto.getNumericDetails();
		assertThat(numericDto.getHiNormal(), is(10.0));
		assertThat(numericDto.getHiAbsolute(), is(11.0));
		assertThat(numericDto.getHiCritical(), is(12.0));
		assertThat(numericDto.getLowNormal(), is(5.0));
		assertThat(numericDto.getLowAbsolute(), is(4.0));
		assertThat(numericDto.getLowCritical(), is(3.0));
		assertThat(numericDto.getPrecise(), is(true));
		assertThat(numericDto.getUnits(), is("ml"));
	}

	@Test
	public void convertProposedConceptReviewPackageToProposedConceptReviewDto_codedProposal_shouldBind() {

		Concept answerConcept = new Concept();
		ConceptName name = new ConceptName("Some answer", english);
		answerConcept.setPreferredName(name);
		when(conceptService.getConceptByUuid("answer-uuid")).thenReturn(answerConcept);
		ProposedConceptReviewPackage reviewPackage = new ProposedConceptReviewPackage();
		reviewPackage.setId(1);
		ProposedConceptReview proposedConcept = new ProposedConceptReview();
		ConceptDatatype codedDatatype = new ConceptDatatype();
		codedDatatype.setName("Coded");
		proposedConcept.setDatatype(codedDatatype);
		List<ProposedConceptReviewAnswer> codedDetails = newArrayList();
		ProposedConceptReviewAnswer answer = new ProposedConceptReviewAnswer();
		answer.setAnswerConceptUuid("answer-uuid");
		codedDetails.add(answer);
		proposedConcept.setCodedDetails(codedDetails);
		reviewPackage.addProposedConcept(proposedConcept);

		final ProposedConceptReviewPackageDto dto = mapperService.convertProposedConceptReviewPackageToProposedConceptReviewDto(reviewPackage);

		final List<ProposedConceptReviewDto> concepts = dto.getConcepts();
		assertThat(concepts.get(0).getDatatype(), is("Coded"));
		final Collection<AnswerDto> answerDtos = concepts.get(0).getAnswers();
		assertThat(answerDtos.size(), is(1));
		final ArrayList<AnswerDto> answers = newArrayList(answerDtos);
		assertThat(answers.size(), is(1));
		assertThat(answers.get(0).getAnswerConceptPreferredName(), is("Some answer"));
	}


	//
	// RESTful requests from AngularJS to ReviewController
	//

	@Test
	public void convertReviewDtoToProposedConceptReviewPackage_regularProposal_shouldBindReviewCommentAndStatus() {

		ProposedConceptReviewDto concept = new ProposedConceptReviewDto();
		concept.setReviewComment("A reviewer's comment");
		concept.setStatus(ProposalStatus.CLOSED_NEW);
		ProposedConceptReviewPackageDto dto = new ProposedConceptReviewPackageDto();
		dto.setConcepts(newArrayList(concept));

		final ProposedConceptReviewPackage responsePackage = mapperService.convertProposedConceptReviewDtoToProposedConceptReviewPackage(dto);

		final ArrayList<ProposedConceptReview> responses = newArrayList(responsePackage.getProposedConcepts());
		assertThat(responses.size(), is(1));
		assertThat(responses.get(0).getReviewComment(), is("A reviewer's comment"));
		assertThat(responses.get(0).getStatus(), is(ProposalStatus.CLOSED_NEW));
	}

	@Test
	public void convertProposedConceptReviewPackageToReviewDto_regularProposal_shouldBindReviewCommentAndStatus() {

		final ProposedConceptReviewPackage mockPackage = mock(ProposedConceptReviewPackage.class);
		final Set<ProposedConceptReview> mockProposedConceptReviews = new HashSet<ProposedConceptReview>();
		final ProposedConceptReview mockResponse = mock(ProposedConceptReview.class);
		when(mockResponse.getReviewComment()).thenReturn("A reviewer's comment");
		when(mockResponse.getStatus()).thenReturn(ProposalStatus.CLOSED_EXISTING);
		mockProposedConceptReviews.add(mockResponse);
		when(mockPackage.getProposedConcepts()).thenReturn(mockProposedConceptReviews);

		final ProposedConceptReviewPackageDto dto = mapperService.convertProposedConceptReviewPackageToProposedConceptReviewDto(mockPackage);

		final List<ProposedConceptReviewDto> concepts = dto.getConcepts();
		assertThat(concepts.size(), is(1));
		assertThat(concepts.get(0).getReviewComment(), is("A reviewer's comment"));
		assertThat(concepts.get(0).getStatus(), is(ProposalStatus.CLOSED_EXISTING));
	}
}
