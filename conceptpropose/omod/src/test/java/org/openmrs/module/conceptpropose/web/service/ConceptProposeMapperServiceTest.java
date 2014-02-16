package org.openmrs.module.conceptpropose.web.service;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openmrs.*;
import org.openmrs.api.ConceptNameType;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.PackageStatus;
import org.openmrs.module.conceptpropose.ProposalStatus;
import org.openmrs.module.conceptpropose.ProposedConcept;
import org.openmrs.module.conceptpropose.ProposedConceptPackage;
import org.openmrs.module.conceptpropose.api.ProposedConceptService;
import org.openmrs.module.conceptpropose.web.controller.SubmitProposal;
import org.openmrs.module.conceptpropose.web.controller.UpdateProposedConceptPackage;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptDto;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptPackageDto;
import org.openmrs.module.conceptpropose.web.dto.SubmissionDto;
import org.openmrs.module.conceptpropose.web.dto.concept.DescriptionDto;
import org.openmrs.module.conceptpropose.web.dto.concept.NameDto;
import org.openmrs.util.LocaleUtility;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.*;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Context.class, LocaleUtility.class})
public class ConceptProposeMapperServiceTest {

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

	private ConceptProposeMapperService mapperService;


	@Before
	public void before() throws Exception {

		DozerBeanMapper mapper = new DozerBeanMapper();
		mapperService = new ConceptProposeMapperService(mapper);
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


	//
	// RESTful request from proposal server to review server
	//

	@Test
	public void convertProposedConceptPackageToSubmissionDto_shouldBindToDomain() throws Exception {

		// Some things to note here:
		//  * Locales will be serialised with toString()
		//  * ConceptDataType will be serialised with the UUID
		//  * ConceptClass will be serialised with the UUID

		// The details about the overall "package"
		final ProposedConceptPackage aPackage = new ProposedConceptPackage();
		aPackage.setDescription("A description about the related concepts");
		aPackage.setEmail("The proposer's email");
		aPackage.setName("Name of proposal");

		// The newly created concept being proposed
		final Concept concept = new Concept();
		final Collection<ConceptName> names = new ArrayList<ConceptName>();
		final ConceptName mainName = new ConceptName();
		mainName.setName("A newly created concept");
		mainName.setLocale(Locale.ENGLISH);
		names.add(mainName);
		concept.setNames(names);
		final ConceptDatatype conceptDatatype = new ConceptDatatype();
		conceptDatatype.setUuid("datatype-uuid");
		concept.setDatatype(conceptDatatype);
		final ConceptClass conceptClass = new ConceptClass();
		conceptClass.setUuid("concept-class-uuid");
		concept.setConceptClass(conceptClass);

		// The comment for the new concept
		final ProposedConcept proposedConcept = new ProposedConcept();
		proposedConcept.setConcept(concept);
		proposedConcept.setComment("A comment about the mocked concept");
		final Set<ProposedConcept> proposedConcepts = new HashSet<ProposedConcept>();
		proposedConcepts.add(proposedConcept);
		aPackage.setProposedConcepts(proposedConcepts);


		final SubmissionDto dto = mapperService.convertProposedConceptPackageToSubmissionDto(aPackage);


		assertThat(dto.getName(), is("Name of proposal"));
		assertThat(dto.getDescription(), is("A description about the related concepts"));
		assertThat(dto.getEmail(), is("The proposer's email"));
		final List<ProposedConceptDto> concepts = dto.getConcepts();
		assertThat(concepts.size(), is(1));
		final ProposedConceptDto newConcept = concepts.get(0);
		assertThat(newConcept.getComment(), is("A comment about the mocked concept"));
		ArrayList<NameDto> dtoNames = newArrayList(newConcept.getNames());
		assertThat(dtoNames.size(), is(1));
		assertThat(dtoNames.get(0).getName(), is("A newly created concept"));
		assertThat(dtoNames.get(0).getLocale(), is("en"));
		assertThat(newConcept.getDatatype(), is("datatype-uuid"));
		assertThat(newConcept.getConceptClass(), is("concept-class-uuid"));

	}



	//
	// RESTful requests from AngularJS to ProposalController
	//

	@Test
	public void convertDtoToProposedConceptPackage_regularProposal_shouldBindToEntity() {

		ProposedConceptPackageDto dto = new ProposedConceptPackageDto();
		dto.setName("asdf");
		dto.setEmail("some@email.com");
		dto.setDescription("a description!");
		dto.setStatus(PackageStatus.DRAFT);

		List<ProposedConceptDto> concepts = new ArrayList<ProposedConceptDto>();
		ProposedConceptDto aConcept = new ProposedConceptDto();
		aConcept.setDatatype("datatype-uuid");
		aConcept.setConceptClass("concept-class-uuid");

		Collection<NameDto> names = new ArrayList<NameDto>();
		NameDto name = new NameDto();
		name.setName("Concept name!");
		name.setLocale("en");
		names.add(name);
		aConcept.setNames(names);

		Collection<DescriptionDto> descriptions = new ArrayList<DescriptionDto>();
		DescriptionDto description = new DescriptionDto();
		description.setDescription("Concept description!");
		description.setLocale("en");
		descriptions.add(description);
		aConcept.setDescriptions(descriptions);

		concepts.add(aConcept);
		dto.setConcepts(concepts);


		ProposedConceptPackage proposedConceptPackage = mapperService.convertProposedConceptDtoToProposedConceptPackage(dto);


		assertThat(proposedConceptPackage, is(notNullValue()));
		assertThat(proposedConceptPackage.getName(), is("asdf"));
		assertThat(proposedConceptPackage.getEmail(), is("some@email.com"));
		assertThat(proposedConceptPackage.getDescription(), is("a description!"));
		assertThat(proposedConceptPackage.getStatus(), is(PackageStatus.DRAFT));

		final Set<ProposedConcept> proposedConcepts = proposedConceptPackage.getProposedConcepts();
		assertThat(proposedConcepts.size(), is(1));
	}

	@Test
	public void convertProposedConceptPackageToDto_regularProposal_shouldBindToDto() {
		ProposedConceptPackage proposedConceptPackage = createProposedConceptPackage();

		final ProposedConceptPackageDto packageDto = mapperService.convertProposedConceptPackageToProposedConceptDto(proposedConceptPackage);


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
}
