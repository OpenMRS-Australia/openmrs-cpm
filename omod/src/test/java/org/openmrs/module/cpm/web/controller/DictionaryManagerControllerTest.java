package org.openmrs.module.cpm.web.controller;

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
import org.openmrs.module.cpm.ProposedConceptResponseNumeric;
import org.openmrs.module.cpm.ProposedConceptResponsePackage;
import org.openmrs.module.cpm.api.ProposedConceptService;
import org.openmrs.module.cpm.web.dto.SubmissionDto;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

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
	private ProposedConceptResponseNumeric responseNumericMock;

	@Mock
	private ConceptService conceptServiceMock;

	@Mock
	private ProposedConceptService proposedConceptServiceMock;

	@Mock
	private ProposedConceptResponsePackage proposedConceptResponsePackageMock;

	private DictionaryManagerController controller;

	@Before
	public void before() throws Exception {
		controller = new DictionaryManagerController();

		mockStatic(Context.class);
		when(Context.getConceptService()).thenReturn(conceptServiceMock);
		when(Context.getService(ProposedConceptService.class)).thenReturn(proposedConceptServiceMock);
		whenNew(ProposedConceptResponsePackage.class).withNoArguments().thenReturn(proposedConceptResponsePackageMock);
		whenNew(ProposedConceptResponse.class).withNoArguments().thenReturn(responseMock);
	}

	@Test
	public void submitProposal_regularProposal_shouldPersistDetails() throws Exception {
		final SubmissionDto dto = setupRegularProposalFixtureWithJson();
		setupRegularFixtureMocks();

		controller.submitProposal(dto);

		verify(responseMock).setConceptClass(conceptClassMock);
		verify(responseMock).setComment("some comment");
		verify(responseMock).setProposedConceptUuid("concept-uuid");
		verify(responseMock).setDatatype(dataTypeMock);
		verify(responseMock).setConceptClass(conceptClassMock);
	}

	private void setupRegularFixtureMocks() throws Exception {
		when(conceptServiceMock.getConceptDatatypeByUuid("datatype-uuid")).thenReturn(dataTypeMock);
		when(conceptServiceMock.getConceptClassByUuid("concept-class-uuid")).thenReturn(conceptClassMock);
		when(dataTypeMock.getUuid()).thenReturn("uuid!");
		when(proposedConceptResponsePackageMock.getId()).thenReturn(1);
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
	public void submitProposal_numericProposal_shouldPersistDetails() throws Exception {
		final SubmissionDto dto = setupNumericProposalFixture();
		setupRegularFixtureMocks();
		setupNumericFixtureMocks();

		controller.submitProposal(dto);

		verify(responseMock).setConceptClass(conceptClassMock);
		verify(responseMock).setComment("some comment");
		verify(responseMock).setProposedConceptUuid("concept-uuid");
		verify(responseMock).setDatatype(dataTypeMock);
		verify(responseMock).setConceptClass(conceptClassMock);

		verify(responseNumericMock).setUnits("ml");
		verify(responseNumericMock).setHiNormal(100.5);
		verify(responseNumericMock).setHiCritical(110.0);
		verify(responseNumericMock).setHiAbsolute(1000.0);
		verify(responseNumericMock).setLowNormal(20.3);
		verify(responseNumericMock).setLowCritical(15.0);
		verify(responseNumericMock).setLowAbsolute(0.0);
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

	private void setupNumericFixtureMocks() throws Exception {
		when(conceptServiceMock.getConceptDatatypeByUuid("8d4a4488-c2cc-11de-8d13-0010c6dffd0f")).thenReturn(dataTypeMock);
		when(dataTypeMock.getUuid()).thenReturn("8d4a4488-c2cc-11de-8d13-0010c6dffd0f");
		whenNew(ProposedConceptResponseNumeric.class).withNoArguments().thenReturn(responseNumericMock);
	}
}
