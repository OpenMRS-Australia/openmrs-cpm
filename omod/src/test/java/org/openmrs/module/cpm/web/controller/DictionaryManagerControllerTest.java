package org.openmrs.module.cpm.web.controller;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openmrs.ConceptClass;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.ProposedConceptResponse;
import org.openmrs.module.cpm.ProposedConceptResponsePackage;
import org.openmrs.module.cpm.api.ProposedConceptService;
import org.openmrs.module.cpm.web.dto.ProposedConceptDto;
import org.openmrs.module.cpm.web.dto.SubmissionDto;
import org.openmrs.module.cpm.web.dto.concept.DescriptionDto;
import org.openmrs.module.cpm.web.dto.concept.NameDto;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DictionaryManagerController.class, Context.class})
public class DictionaryManagerControllerTest {

	@Mock
	private ProposedConceptResponse responseMock;

	@Mock
	private ConceptClass conceptClassMock;

	@Mock
	private ConceptService conceptServiceMock;

	@Mock
	private ProposedConceptService proposedConceptServiceMock;

	@Mock
	private ProposedConceptResponsePackage proposedConceptResponsePackageMock;

	private DictionaryManagerController controller;

	@Before
	public void before() {
		controller = new DictionaryManagerController();
	}

	@Test
	public void submitProposal_regularProposal_shouldPersistDetails() throws Exception {

		final SubmissionDto dto = setupRegularProposalFixture();

		controller.submitProposal(dto);

		verify(responseMock).setConceptClass(conceptClassMock);
	}

	private SubmissionDto setupRegularProposalFixture() throws Exception {

		mockStatic(Context.class);
		when(Context.getConceptService()).thenReturn(conceptServiceMock);
		when(conceptServiceMock.getConceptClassByUuid("blah")).thenReturn(conceptClassMock);

		when(Context.getService(ProposedConceptService.class)).thenReturn(proposedConceptServiceMock);

		whenNew(ProposedConceptResponsePackage.class).withNoArguments().thenReturn(proposedConceptResponsePackageMock);
		when(proposedConceptResponsePackageMock.getId()).thenReturn(1);

		whenNew(ProposedConceptResponse.class).withNoArguments().thenReturn(responseMock);


		ProposedConceptDto concept = new ProposedConceptDto();
		concept.setConceptClass("blah");
		List<NameDto> names = new ArrayList<NameDto>();
		NameDto name = new NameDto();
		name.setName("name");
		name.setLocale("en");
		names.add(name);
		concept.setNames(names);

		List<DescriptionDto> descriptions = new ArrayList<DescriptionDto>();
		DescriptionDto description = new DescriptionDto();
		description.setDescription("description");
		description.setLocale("en");
		descriptions.add(description);
		concept.setDescriptions(descriptions);

		ArrayList<ProposedConceptDto> concepts = new ArrayList<ProposedConceptDto>();
		concepts.add(concept);

		SubmissionDto dto = new SubmissionDto();
		dto.setConcepts(concepts);

		return dto;
	}
}
