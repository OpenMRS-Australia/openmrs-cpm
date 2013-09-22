package org.openmrs.module.cpm.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.ProposedConceptResponsePackage;
import org.openmrs.module.cpm.SubmissionResponseStatus;
import org.openmrs.module.cpm.api.ProposedConceptService;
import org.openmrs.module.cpm.web.dto.SubmissionDto;
import org.openmrs.module.cpm.web.dto.SubmissionResponseDto;
import org.openmrs.module.cpm.web.service.CpmMapperService;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

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

	@Mock
	private CpmMapperService mapperServiceMock;


	@InjectMocks
	private DictionaryManagerController controller = new DictionaryManagerController();

	@Before
	public void before() throws Exception {
		mockStatic(Context.class);
		when(Context.getConceptService()).thenReturn(conceptServiceMock);
		when(Context.getService(ProposedConceptService.class)).thenReturn(proposedConceptServiceMock);

		when(conceptServiceMock.getConceptDatatypeByUuid(anyString())).thenReturn(dataTypeMock);
		when(conceptServiceMock.getConceptClassByUuid(anyString())).thenReturn(conceptClassMock);
		when(dataTypeMock.getUuid()).thenReturn("uuid!");
	}

	@Test
	public void submitProposal_regularProposal_shouldPersistDetails() throws Exception {
		final SubmissionDto dto = new SubmissionDto();
		ProposedConceptResponsePackage expectedResponse = new ProposedConceptResponsePackage();
		expectedResponse.setId(123);
		setupRegularFixtureMocks(expectedResponse);

		when(proposedConceptServiceMock.saveProposedConceptResponsePackage(Matchers.any(ProposedConceptResponsePackage.class))).thenReturn(expectedResponse);

		SubmissionResponseDto response = controller.submitProposal(dto);

		verify(mapperServiceMock).convertDtoToProposedConceptResponsePackage(dto);

		ArgumentCaptor<ProposedConceptResponsePackage> captor = ArgumentCaptor.forClass(ProposedConceptResponsePackage.class);
		verify(proposedConceptServiceMock).saveProposedConceptResponsePackage(captor.capture());
		final ProposedConceptResponsePackage actual = captor.getValue();
		assertThat(actual, is(expectedResponse));

		assertThat(response.getMessage(), is("All Good!"));
		assertThat(response.getStatus(), is(SubmissionResponseStatus.SUCCESS));
		assertThat(response.getId(), is(expectedResponse.getId()));
	}

	@Test
	public void submitProposal_regularProposal_shouldReturnError() throws Exception {
		final SubmissionDto dto = new SubmissionDto();
		ProposedConceptResponsePackage expectedResponse = new ProposedConceptResponsePackage();
		expectedResponse.setId(321);
		setupRegularFixtureMocks(expectedResponse);
		when(proposedConceptServiceMock.saveProposedConceptResponsePackage(Matchers.any(ProposedConceptResponsePackage.class))).thenThrow(new RuntimeException());


		SubmissionResponseDto response = controller.submitProposal(dto);

		verify(mapperServiceMock).convertDtoToProposedConceptResponsePackage(dto);
		ArgumentCaptor<ProposedConceptResponsePackage> captor = ArgumentCaptor.forClass(ProposedConceptResponsePackage.class);
		verify(proposedConceptServiceMock).saveProposedConceptResponsePackage(captor.capture());
		final ProposedConceptResponsePackage actual = captor.getValue();

		assertThat(actual, is(expectedResponse));
		assertThat(response.getStatus(), is(SubmissionResponseStatus.FAILURE));
	}

	private void setupRegularFixtureMocks(ProposedConceptResponsePackage expectedResponse) throws Exception {

		when(mapperServiceMock.convertDtoToProposedConceptResponsePackage(Matchers.any(SubmissionDto.class))).thenReturn(expectedResponse);
	}




}
