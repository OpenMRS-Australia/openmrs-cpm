package org.openmrs.module.conceptreview.web.service;

import org.codehaus.jackson.map.ObjectMapper;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.ProposalStatus;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptReviewDto;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptReviewPackageDto;
import org.openmrs.module.conceptpropose.web.dto.SubmissionDto;
import org.openmrs.module.conceptreview.*;
import org.openmrs.module.conceptreview.api.ProposedConceptReviewService;
import org.openmrs.util.LocaleUtility;
import org.powermock.api.mockito.PowerMockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

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


	@Before
	public void before() throws Exception {

		DozerBeanMapper mapper = new DozerBeanMapper();
		mapperService = new ConceptReviewMapperService(mapper);
		List<String> files = new ArrayList<String>();
		files.add("dozer-mappings.xml");
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
	public void convertSubmissionDtoToProposedConceptReviewPackage_shouldBindToDomain() throws Exception {

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
	public void convertSubmissionDtoToProposedConceptReviewPackage_numericProposal() throws Exception {

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
