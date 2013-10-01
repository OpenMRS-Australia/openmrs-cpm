package org.openmrs.module.cpm.web.service;

import org.codehaus.jackson.map.ObjectMapper;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openmrs.*;
import org.openmrs.api.ConceptNameType;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.*;
import org.openmrs.module.cpm.api.ProposedConceptService;
import org.openmrs.module.cpm.web.controller.BaseCpmOmodTest;
import org.openmrs.module.cpm.web.controller.ProposalController;
import org.openmrs.module.cpm.web.controller.SubmitProposal;
import org.openmrs.module.cpm.web.controller.UpdateProposedConceptPackage;
import org.openmrs.module.cpm.web.dto.ProposedConceptDto;
import org.openmrs.module.cpm.web.dto.ProposedConceptPackageDto;
import org.openmrs.module.cpm.web.dto.SubmissionDto;
import org.openmrs.module.cpm.web.dto.concept.DescriptionDto;
import org.openmrs.module.cpm.web.dto.concept.NameDto;
import org.openmrs.util.LocaleUtility;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Context.class, LocaleUtility.class, ProposalController.class})
public class CpmMapperServiceTest {

	@Mock
	private ProposedConceptService service;

	@Mock
	private ConceptService conceptService;

	@Mock
	private ProposedConceptPackage conceptPackage;

	@Mock
	private SubmitProposal submitProposal;

	@Mock
	private UpdateProposedConceptPackage updateProposedConceptPackage;

	private Integer proposedConceptPackageId = 1;

	@Mock
	private ConceptClass conceptClass;

	@Mock
	private Concept concept;

	@Mock
	private ConceptDatatype datatype;

	@Mock
	private ProposedConceptResponsePackage conceptResponsePackage;

	@Before
	public void before() throws Exception {

		DozerBeanMapper mapper = new DozerBeanMapper();
		mapperService = new CpmMapperService(mapper);
		List<String> files = new ArrayList<String>();
		files.add("dozer-mappings.xml");
		mapper.setMappingFiles(files);

		mockStatic(Context.class);
		mockStatic(LocaleUtility.class);

		PowerMockito.when(Context.class, "getService", ProposedConceptService.class).thenReturn(service);
		when(service.getProposedConceptPackageById(proposedConceptPackageId)).thenReturn(conceptPackage);


		// for dozer
		PowerMockito.when(Context.class, "getConceptService").thenReturn(conceptService);
		when(conceptService.getConceptClassByUuid(anyString())).thenReturn(conceptClass);
		when(conceptService.getConcept(anyString())).thenReturn(concept);
		when(conceptService.getConceptDatatypeByUuid(anyString())).thenReturn(datatype);
	}


	private CpmMapperService mapperService;

	@Test
	public void convertDtoToProposedConceptResponsePackage_shouldBindToDomain() throws Exception {

		whenNew(ProposedConceptPackage.class).withNoArguments().thenReturn(conceptPackage);
		final SubmissionDto dto = setupRegularProposalFixtureWithJson();


		ProposedConceptResponsePackage value = mapperService.convertDtoToProposedConceptResponsePackage(dto);


		assertThat(value.getName(), is("A proposal"));
		assertThat(value.getEmail(), is("asdf@asdf.com"));
		assertThat(value.getDescription(), is("A description"));

		final List<ProposedConceptResponse> proposedConcepts = new ArrayList<ProposedConceptResponse>(value.getProposedConcepts());
		final ProposedConceptResponse proposedConceptResponse = proposedConcepts.get(0);
		assertThat(proposedConceptResponse.getProposedConceptUuid(), is("concept-uuid"));
		assertThat(proposedConceptResponse.getComment(), is("some comment"));
		assertThat(proposedConceptResponse.getConceptClass(), is(conceptClass));
		assertThat(proposedConceptResponse.getDatatype(), is(datatype));

		final List<ProposedConceptResponseName> names = proposedConceptResponse.getNames();
		assertThat(names.size(), is(1));
		assertThat(names.get(0).getName(), is("Concept name"));

		final List<ProposedConceptResponseDescription> descriptions = proposedConceptResponse.getDescriptions();
		assertThat(descriptions.size(), is(1));
		assertThat(descriptions.get(0).getDescription(), is("Concept description"));
	}


	@Test
	public void convertProposedConceptPackageToDto_shouldBindToDto() {
		ProposedConceptPackage proposedConceptPackage = createProposedConceptPackage();

		final ProposedConceptPackageDto packageDto = mapperService.convertProposedConceptPackageToDto(proposedConceptPackage);


		assertThat(packageDto.getName(), is("A sample proposal"));
		assertThat(packageDto.getDescription(), is("A sample proposal description"));
		assertThat(packageDto.getEmail(), is("asdf@asdf.com"));
		assertThat(packageDto.getStatus(), is(PackageStatus.DRAFT));

		final List<ProposedConceptDto> concepts = packageDto.getConcepts();
		assertThat(concepts.size(), is(1));
		final ProposedConceptDto conceptDto = concepts.get(0);
		assertThat(conceptDto.getStatus(), is(ProposalStatus.DRAFT));
		assertThat(conceptDto.getComment(), is("A concept comment"));
		assertThat(conceptDto.getPreferredName(), is("A concept name"));
		assertThat(conceptDto.getDatatype(), is("Numeric"));

		final ArrayList<NameDto> names = new ArrayList<NameDto>(conceptDto.getNames());
		assertThat(names.size(), is(1));
		assertThat(names.get(0).getName(), is("A concept name"));
		assertThat(names.get(0).getLocale(), is("en"));
		assertThat(names.get(0).getType(), is(ConceptNameType.FULLY_SPECIFIED));

		final ArrayList<DescriptionDto> descriptionDtos = new ArrayList<DescriptionDto>(conceptDto.getDescriptions());
		assertThat(descriptionDtos.size(), is(1));
		assertThat(descriptionDtos.get(0).getDescription(), is("A concept description"));
		assertThat(descriptionDtos.get(0).getLocale(), is("en"));
	}

	private ProposedConceptPackage createProposedConceptPackage(){

		ProposedConceptPackage conceptPackage = new ProposedConceptPackage();
		conceptPackage.setName("A sample proposal");
		conceptPackage.setDescription("A sample proposal description");
		conceptPackage.setStatus(PackageStatus.DRAFT);
		conceptPackage.setEmail("asdf@asdf.com");

		Set<ProposedConcept> proposedConcepts = new HashSet<ProposedConcept>();
		ProposedConcept proposedConcept = new ProposedConcept();
		proposedConcept.setStatus(ProposalStatus.DRAFT);
		proposedConcept.setComment("A concept comment");

		Concept concept = new Concept();
		concept.setId(123);
		concept.setConceptId(321);

		ConceptName name = new ConceptName();
		name.setConceptNameType(ConceptNameType.FULLY_SPECIFIED);
		name.setLocale(Locale.ENGLISH);
		name.setName("A concept name");

		Collection<ConceptName> conceptNames = new ArrayList<ConceptName>();
		conceptNames.add(name);
		concept.setNames(conceptNames);

		ConceptDescription description = new ConceptDescription();
		description.setDescription("A concept description");
		description.setLocale(Locale.ENGLISH);
		Collection<ConceptDescription> conceptDescriptions = new ArrayList<ConceptDescription>();
		conceptDescriptions.add(description);
		concept.setDescriptions(conceptDescriptions);

		ConceptDatatype datatype = new ConceptDatatype();
		datatype.setName("Numeric");
		concept.setDatatype(datatype);
		proposedConcept.setConcept(concept);
		proposedConcepts.add(proposedConcept);
		conceptPackage.setProposedConcepts(proposedConcepts);

		return conceptPackage;
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


	@Test
	public void convertDtoToProposedConceptResponsePackage_numericProposal() throws Exception {

		whenNew(ProposedConceptResponsePackage.class).withNoArguments().thenReturn(conceptResponsePackage);
		final SubmissionDto dto = setupNumericProposalFixture();

		ProposedConceptResponsePackage value = mapperService.convertDtoToProposedConceptResponsePackage(dto);

		final List<ProposedConceptResponse> proposedConcepts = new ArrayList<ProposedConceptResponse>(value.getProposedConcepts());
		final ProposedConceptResponse proposedConceptResponse = proposedConcepts.get(0);

		final ProposedConceptResponseNumeric numericDetails = proposedConceptResponse.getNumericDetails();
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


}
