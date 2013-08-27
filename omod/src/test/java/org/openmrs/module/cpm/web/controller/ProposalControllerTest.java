package org.openmrs.module.cpm.web.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openmrs.*;
import org.openmrs.api.ConceptNameType;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.PackageStatus;
import org.openmrs.module.cpm.ProposalStatus;
import org.openmrs.module.cpm.ProposedConcept;
import org.openmrs.module.cpm.ProposedConceptPackage;
import org.openmrs.module.cpm.api.ProposedConceptService;
import org.openmrs.module.cpm.web.dto.ProposedConceptDto;
import org.openmrs.module.cpm.web.dto.ProposedConceptPackageDto;
import org.openmrs.module.cpm.web.dto.concept.DescriptionDto;
import org.openmrs.module.cpm.web.dto.concept.NameDto;
import org.openmrs.module.cpm.web.dto.concept.SearchConceptResultDto;
import org.openmrs.module.cpm.web.dto.factory.DescriptionDtoFactory;
import org.openmrs.module.cpm.web.dto.factory.NameDtoFactory;
import org.openmrs.util.LocaleUtility;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Context.class, LocaleUtility.class, ProposalController.class})
public class ProposalControllerTest {

	@Mock
	ProposedConceptService service;

    @Mock
    ConceptService  conceptService;

    @Mock
	ProposedConceptPackage conceptPackage;

	@Mock
	SubmitProposal submitProposal;

	@Mock
	UpdateProposedConceptPackage updateProposedConceptPackage;


    @InjectMocks
    ProposalController controller = new ProposalController(submitProposal, updateProposedConceptPackage,
            new DescriptionDtoFactory(), new NameDtoFactory() );

	@Before
	public void before() throws Exception {
		mockStatic(Context.class);
        mockStatic(LocaleUtility.class);

		PowerMockito.when(Context.class, "getService", ProposedConceptService.class).thenReturn(service);
		when(service.getProposedConceptPackageById(1)).thenReturn(conceptPackage);


		whenNew(ProposedConceptPackage.class).withNoArguments().thenReturn(conceptPackage);
	}

	@Test
	@Ignore
	public void getProposalById_simpleProposal_shouldBindToDto() {

		when(conceptPackage.getName()).thenReturn("A sample proposal");
		when(conceptPackage.getDescription()).thenReturn("A sample proposal description");
		when(conceptPackage.getStatus()).thenReturn(PackageStatus.DRAFT);
		when(conceptPackage.getEmail()).thenReturn("asdf@asdf.com");
		Set<ProposedConcept> proposedConcepts = new HashSet<ProposedConcept>();
		ProposedConcept proposedConcept = mock(ProposedConcept.class);
		when(proposedConcept.getStatus()).thenReturn(ProposalStatus.DRAFT);
		when(proposedConcept.getComment()).thenReturn("A concept comment");

		Concept concept = mock(Concept.class);
		when(concept.getId()).thenReturn(123);
		when(concept.getConceptId()).thenReturn(123);
		ConceptName name = mock(ConceptName.class);
		when(name.getConceptNameType()).thenReturn(ConceptNameType.FULLY_SPECIFIED);
		when(name.getLocale()).thenReturn(Locale.ENGLISH);
		when(name.getName()).thenReturn("A concept name");
		when(concept.getName()).thenReturn(name);
		ConceptDescription description = mock(ConceptDescription.class);
		when(description.getDescription()).thenReturn("A concept description");
		when(description.getLocale()).thenReturn(Locale.ENGLISH);
		when(concept.getDescription()).thenReturn(description);
		ConceptDatatype datatype = mock(ConceptDatatype.class);
		when(datatype.getName()).thenReturn("Numeric");
		when(concept.getDatatype()).thenReturn(datatype);
		when(proposedConcept.getConcept()).thenReturn(concept);

		proposedConcepts.add(proposedConcept);
		when(conceptPackage.getProposedConcepts()).thenReturn(proposedConcepts);

		final ProposedConceptPackageDto packageDto = controller.getProposalById("1");

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
		assertThat(names.get(0).getLocale(), is("EN"));
		assertThat(names.get(0).getType(), is(ConceptNameType.FULLY_SPECIFIED));

		final ArrayList<DescriptionDto> descriptionDtos = new ArrayList<DescriptionDto>(conceptDto.getDescriptions());
		assertThat(descriptionDtos.size(), is(1));
		assertThat(descriptionDtos.get(0).getDescription(), is("A concept description"));
		assertThat(descriptionDtos.get(0).getLocale(), is("EN"));
	}

	@Test
	public void addProposal_newDraftProposal_shouldOnlyCreateNewProposal() {
		final ProposedConceptPackageDto newDraftProposal = new ProposedConceptPackageDto();
		newDraftProposal.setName("new draft proposal");
		newDraftProposal.setEmail("some@email.com");
		newDraftProposal.setDescription("new draft proposal description");
		final List<ProposedConceptDto> concepts = new ArrayList<ProposedConceptDto>();
		final ProposedConceptDto concept = new ProposedConceptDto();
		concept.setUuid("concept-uuid");
		concepts.add(concept);
		newDraftProposal.setConcepts(concepts);
		when(conceptPackage.getId()).thenReturn(1);

		final ProposedConceptPackageDto response = controller.addProposal(newDraftProposal);

		verify(conceptPackage).setName("new draft proposal");
		verify(conceptPackage).setEmail("some@email.com");
		verify(conceptPackage).setDescription("new draft proposal description");
		verify(updateProposedConceptPackage).updateProposedConcepts(conceptPackage, newDraftProposal);
		verify(service).saveProposedConceptPackage(conceptPackage);
		assertThat(newDraftProposal.getId(), is(1));

		verify(submitProposal, never()).submitProposedConcept(conceptPackage);
	}

	@Test
	public void addProposal_newProposalToBeSubmittedStraightAway_shouldCreateNewProposalAndSubmit() {
		final ProposedConceptPackageDto newDraftProposal = new ProposedConceptPackageDto();
		newDraftProposal.setName("new draft proposal");
		newDraftProposal.setEmail("some@email.com");
		newDraftProposal.setDescription("new draft proposal description");
		newDraftProposal.setStatus(PackageStatus.TBS);
		final List<ProposedConceptDto> concepts = new ArrayList<ProposedConceptDto>();
		final ProposedConceptDto concept = new ProposedConceptDto();
		concept.setUuid("concept-uuid");
		concepts.add(concept);
		newDraftProposal.setConcepts(concepts);
		when(conceptPackage.getId()).thenReturn(1);

		final ProposedConceptPackageDto response = controller.addProposal(newDraftProposal);

		InOrder inOrder = inOrder(conceptPackage, updateProposedConceptPackage, service, submitProposal);
		inOrder.verify(conceptPackage).setName("new draft proposal");
		inOrder.verify(conceptPackage).setEmail("some@email.com");
		inOrder.verify(conceptPackage).setDescription("new draft proposal description");
		inOrder.verify(updateProposedConceptPackage).updateProposedConcepts(conceptPackage, newDraftProposal);
		inOrder.verify(service).saveProposedConceptPackage(conceptPackage);
		assertThat(newDraftProposal.getId(), is(1));
		inOrder.verify(submitProposal).submitProposedConcept(conceptPackage);
	}

	@Test
	public void updateProposal_sendProposal_shouldPersistChangesAndSendProposal() throws Exception {

		when(conceptPackage.getStatus()).thenReturn(PackageStatus.DRAFT);

		ProposedConceptPackageDto dto = new ProposedConceptPackageDto();
		dto.setStatus(PackageStatus.TBS);
		controller.updateProposal("1", dto);

		InOrder inOrder = inOrder(updateProposedConceptPackage, submitProposal);
		inOrder.verify(updateProposedConceptPackage).updateProposedConcepts(conceptPackage, dto);
		inOrder.verify(submitProposal).submitProposedConcept(conceptPackage);
	}

	@Test
	public void updateProposal_saveProposal_shouldPersistChanges() {

		ProposedConceptPackageDto dto = new ProposedConceptPackageDto();
		controller.updateProposal("1", dto);

		verify(updateProposedConceptPackage).updateProposedConcepts(conceptPackage, dto);
		verify(submitProposal, times(0)).submitProposedConcept(conceptPackage);
    }

    @Test
    public void shouldFindConcepts() throws  Exception{


        ConceptSearchResult result = new ConceptSearchResult();
        Concept concept = new Concept();
        concept.setConceptId(123);
        concept.setDateChanged(new Date());
        concept.setId(111);
        ConceptName conceptName = new ConceptName();
        conceptName.setName("My concept name");
        conceptName.setLocale(Locale.UK);
        Collection<ConceptName>  conceptNames = Lists.newArrayList();
        concept.setNames(conceptNames);
        ConceptDatatype datatype = new ConceptDatatype();
        datatype.setName("my datatype");
        concept.setPreferredName(conceptName);
        concept.setDatatype(datatype);
        Collection<ConceptDescription> descriptions = Lists.newArrayList();
        concept.setDescriptions(descriptions);

        result.setConcept(concept);

        List<ConceptSearchResult> resultList = Lists.newArrayList(result);

        PowerMockito.when(Context.class, "getConceptService").thenReturn(conceptService);

        PowerMockito.when(LocaleUtility.class, "getLocalesInOrder").thenReturn(Sets.newHashSet(Locale.US));

        when(conceptService.getConcepts(anyString(), any(Locale.class), anyBoolean())).thenReturn(resultList);


        String requestNum = "100";
        SearchConceptResultDto resultDto = controller.findConcepts("dummyQuery", requestNum);

        Assert.assertEquals(resultDto.getRequestNum(), requestNum);
        Assert.assertEquals(resultDto.getConcepts().size(), resultList.size());
        Assert.assertTrue(resultDto.getConcepts().get(0).getId() == concept.getId());


    }
}
