package org.openmrs.module.conceptreview.web.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.api.ConceptNameType;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptreview.web.service.ConceptReviewMapperService;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.openmrs.module.conceptpropose.web.dto.SubmissionDto;
import org.openmrs.module.conceptpropose.web.dto.SubmissionResponseDto;
import org.openmrs.module.conceptpropose.SubmissionResponseStatus;
import org.openmrs.module.conceptreview.*;
import org.openmrs.module.conceptreview.api.ProposedConceptReviewService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
	private ProposedConceptReviewService proposedConceptReviewServiceMock;

	@Mock
	private ConceptReviewMapperService mapperServiceMock;


	@InjectMocks
	private DictionaryManagerController controller = new DictionaryManagerController();

	@Before
	public void before() throws Exception {
		mockStatic(Context.class);
		when(Context.getConceptService()).thenReturn(conceptServiceMock);
		when(Context.getService(ProposedConceptReviewService.class)).thenReturn(proposedConceptReviewServiceMock);

		when(conceptServiceMock.getConceptDatatypeByUuid(anyString())).thenReturn(dataTypeMock);
		when(conceptServiceMock.getConceptClassByUuid(anyString())).thenReturn(conceptClassMock);
		when(dataTypeMock.getUuid()).thenReturn("uuid!");
	}

	@Test
	public void submitProposal_regularProposal_shouldPersistDetails() throws Exception {

		final SubmissionDto dto = new SubmissionDto();
		ProposedConceptReviewPackage expectedReview = new ProposedConceptReviewPackage();
		expectedReview.setId(123);
		setupRegularFixtureMocks(expectedReview);

		when(proposedConceptReviewServiceMock.saveProposedConceptReviewPackage(Matchers.any(ProposedConceptReviewPackage.class))).thenReturn(expectedReview);


		SubmissionResponseDto response = controller.submitProposal(dto);


		verify(mapperServiceMock).convertSubmissionDtoToProposedConceptReviewPackage(dto);

		ArgumentCaptor<ProposedConceptReviewPackage> captor = ArgumentCaptor.forClass(ProposedConceptReviewPackage.class);
		verify(proposedConceptReviewServiceMock).saveProposedConceptReviewPackage(captor.capture());
		final ProposedConceptReviewPackage actual = captor.getValue();
		assertThat(actual, is(expectedReview));

		assertThat(response.getMessage(), is("All Good!"));
		assertThat(response.getStatus(), is(SubmissionResponseStatus.SUCCESS));
		assertThat(response.getId(), is(expectedReview.getId()));
	}

	@Test
	public void submitProposal_regularProposal_shouldReturnError() throws Exception {

		final SubmissionDto dto = new SubmissionDto();
		ProposedConceptReviewPackage expectedResponse = new ProposedConceptReviewPackage();
		expectedResponse.setId(321);
		setupRegularFixtureMocks(expectedResponse);
		when(proposedConceptReviewServiceMock.saveProposedConceptReviewPackage(Matchers.any(ProposedConceptReviewPackage.class))).thenThrow(new RuntimeException());


		SubmissionResponseDto response = controller.submitProposal(dto);


		verify(mapperServiceMock).convertSubmissionDtoToProposedConceptReviewPackage(dto);
		ArgumentCaptor<ProposedConceptReviewPackage> captor = ArgumentCaptor.forClass(ProposedConceptReviewPackage.class);
		verify(proposedConceptReviewServiceMock).saveProposedConceptReviewPackage(captor.capture());
		final ProposedConceptReviewPackage actual = captor.getValue();

		assertThat(actual, is(expectedResponse));
		assertThat(response.getStatus(), is(SubmissionResponseStatus.FAILURE));
	}

	private void setupRegularFixtureMocks(ProposedConceptReviewPackage expectedReview) throws Exception {

		when(mapperServiceMock.convertSubmissionDtoToProposedConceptReviewPackage(Matchers.any(SubmissionDto.class))).thenReturn(expectedReview);
	}




}
