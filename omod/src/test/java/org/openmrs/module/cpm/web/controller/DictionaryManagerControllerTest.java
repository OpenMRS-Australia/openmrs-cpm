package org.openmrs.module.cpm.web.controller;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
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
	private ConceptDatatype dataTypeMock;

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

		final SubmissionDto dto = setupRegularProposalFixtureWithJson();

		controller.submitProposal(dto);

		verify(responseMock).setConceptClass(conceptClassMock);
	}

	private SubmissionDto setupRegularProposalFixtureWithJson() throws Exception {

		mockStatic(Context.class);
		when(Context.getConceptService()).thenReturn(conceptServiceMock);
		when(conceptServiceMock.getConceptDatatypeByUuid("some datatype")).thenReturn(dataTypeMock);
		when(conceptServiceMock.getConceptClassByUuid("blah")).thenReturn(conceptClassMock);
		when(dataTypeMock.getUuid()).thenReturn("uuid!");

		when(Context.getService(ProposedConceptService.class)).thenReturn(proposedConceptServiceMock);

		whenNew(ProposedConceptResponsePackage.class).withNoArguments().thenReturn(proposedConceptResponsePackageMock);
		when(proposedConceptResponsePackageMock.getId()).thenReturn(1);

		whenNew(ProposedConceptResponse.class).withNoArguments().thenReturn(responseMock);

		String regularFixture =
				"{" +
				"  \"name\": \"A proposal\"," +
				"  \"email\": \"asdf@asdf.com\"," +
				"  \"description\": \"A description\"," +
				"  \"concepts\": [" +
				"    {" +
				"      \"conceptClass\": \"blah\"," +
				"      \"datatype\": \"some datatype\"," +
				"      \"names\": [" +
				"        {" +
				"          \"name\": \"Concept name\"," +
				"          \"locale\": \"en\"" +
				"        }" +
				"      ]," +
				"      \"descriptions\": [" +
				"        {" +
				"          \"description\": \"Concept description\"," +
				"          \"locale\": \"en\"" +
				"        }" +
				"      ]" +
				"    }" +
				"  ]" +
				"}";

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(regularFixture, SubmissionDto.class);
	}
}
