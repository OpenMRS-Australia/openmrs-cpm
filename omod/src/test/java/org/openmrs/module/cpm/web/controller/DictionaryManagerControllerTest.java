package org.openmrs.module.cpm.web.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.*;
import org.openmrs.module.cpm.api.ProposedConceptService;
import org.openmrs.module.cpm.web.dto.SubmissionDto;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DictionaryManagerController.class, Context.class})
public class DictionaryManagerControllerTest {

	@Mock
	private ConceptDatatype dataTypeMock;

	@Mock
	private ConceptClass conceptClassMock;

	@Mock
	private ConceptService conceptServiceMock;

	@Mock
	private ProposedConceptService proposedConceptServiceMock;

	private DictionaryManagerController controller;

	@Before
	public void before() throws Exception {
		controller = new DictionaryManagerController();

		mockStatic(Context.class);
		when(Context.getConceptService()).thenReturn(conceptServiceMock);
		when(Context.getService(ProposedConceptService.class)).thenReturn(proposedConceptServiceMock);
	}

	@Test
	public void submitProposal_regularProposal_shouldPersistDetails() throws Exception {
		final SubmissionDto dto = setupRegularProposalFixtureWithJson();
		setupRegularFixtureMocks();

		controller.submitProposal(dto);

		ArgumentCaptor<ProposedConceptResponsePackage> captor = ArgumentCaptor.forClass(ProposedConceptResponsePackage.class);
		verify(proposedConceptServiceMock).saveProposedConceptResponsePackage(captor.capture());
		final ProposedConceptResponsePackage value = captor.getValue();
		assertThat(value.getName(), is("A proposal"));
		assertThat(value.getEmail(), is("asdf@asdf.com"));
		assertThat(value.getDescription(), is("A description"));

		final ArrayList<ProposedConceptResponse> proposedConcepts = new ArrayList<ProposedConceptResponse>(value.getProposedConcepts());
		final ProposedConceptResponse proposedConceptResponse = proposedConcepts.get(0);
		assertThat(proposedConceptResponse.getProposedConceptUuid(), is("concept-uuid"));
		assertThat(proposedConceptResponse.getComment(), is("some comment"));
		assertThat(proposedConceptResponse.getConceptClass(), is(conceptClassMock));
		assertThat(proposedConceptResponse.getDatatype(), is(dataTypeMock));

		final List<ProposedConceptResponseName> names = proposedConceptResponse.getNames();
		assertThat(names.get(0).getName(), is("Concept name"));

		final List<ProposedConceptResponseDescription> descriptions = proposedConceptResponse.getDescriptions();
		assertThat(descriptions.get(0).getDescription(), is("Concept description"));
	}

	private void setupRegularFixtureMocks() throws Exception {
		when(conceptServiceMock.getConceptDatatypeByUuid("datatype-uuid")).thenReturn(dataTypeMock);
		when(conceptServiceMock.getConceptClassByUuid("concept-class-uuid")).thenReturn(conceptClassMock);
		when(dataTypeMock.getUuid()).thenReturn("uuid!");
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

		ArgumentCaptor<ProposedConceptResponsePackage> captor = ArgumentCaptor.forClass(ProposedConceptResponsePackage.class);
		verify(proposedConceptServiceMock).saveProposedConceptResponsePackage(captor.capture());
		final ProposedConceptResponsePackage value = captor.getValue();
		final ArrayList<ProposedConceptResponse> proposedConcepts = new ArrayList<ProposedConceptResponse>(value.getProposedConcepts());
		final ProposedConceptResponse proposedConceptResponse = proposedConcepts.get(0);
		assertThat(proposedConceptResponse.getDatatype(), is(dataTypeMock));

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

	private void setupNumericFixtureMocks() throws Exception {
		when(conceptServiceMock.getConceptDatatypeByUuid("8d4a4488-c2cc-11de-8d13-0010c6dffd0f")).thenReturn(dataTypeMock);
		when(dataTypeMock.getUuid()).thenReturn("8d4a4488-c2cc-11de-8d13-0010c6dffd0f");
	}
}
