package org.openmrs.module.conceptreview.web.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.ProposalStatus;
import org.openmrs.module.conceptreview.ProposedConceptResponse;
import org.openmrs.module.conceptreview.ProposedConceptResponsePackage;
import org.openmrs.module.conceptpropose.api.ProposedConceptService;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptResponseDto;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptResponsePackageDto;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Context.class)
public class ReviewControllerTest {

	@Mock
    ProposedConceptService service;

	@Mock
    ProposedConceptResponsePackage proposedConceptResponsePackage;

	@Mock
    ProposedConceptResponse proposedConceptResponse;

	@InjectMocks
	ReviewController controller = new ReviewController();

	@Before
	public void before() throws Exception {
		mockStatic(Context.class);
		PowerMockito.when(Context.class, "getService", ProposedConceptService.class).thenReturn(service);

		when(service.getProposedConceptResponsePackageById(1)).thenReturn(proposedConceptResponsePackage);
		when(service.getAllProposedConceptResponsePackages()).thenReturn(Lists.newArrayList(proposedConceptResponsePackage));
		when(proposedConceptResponsePackage.getProposedConcept(1)).thenReturn(proposedConceptResponse);
	}

	@Test
	public void updateConceptResponse_shouldPersistReviewComment() {
		final ProposedConceptResponseDto dto = new ProposedConceptResponseDto();
		dto.setReviewComment("A review comment");
		dto.setStatus(ProposalStatus.SUBMITTED);

		controller.updateConceptResponse(1, 1, dto);

		verify(proposedConceptResponse).setReviewComment("A review comment");
		verify(proposedConceptResponse).setStatus(ProposalStatus.SUBMITTED);
		verify(proposedConceptResponse, times(0)).setConcept(any(Concept.class));
		verify(service, times(1)).saveProposedConceptResponsePackage(proposedConceptResponsePackage);
	}
	
	@Test
	public void getProposalResponse_shouldRetrieveProposalResponse() {
		setupProposalMock();
		
		ProposedConceptResponsePackageDto proposalDto = controller.getProposalResponse(1);
		
		verifyProposalDto(proposalDto);
	}
	
	@Test
	public void getProposalResponses_shouldRetrieveProposalResponses() {
		setupProposalMock();
		
		ArrayList<ProposedConceptResponsePackageDto> proposals = controller.getProposalResponses();
		
		assertThat(proposals.size(), is(1));
		verifyProposalDto(proposals.get(0));
	}
	
	private void setupProposalMock() {
		when(proposedConceptResponsePackage.getId()).thenReturn(1);
		when(proposedConceptResponsePackage.getName()).thenReturn("test proposal");
		when(proposedConceptResponsePackage.getEmail()).thenReturn("test@test.com");
		when(proposedConceptResponsePackage.getDescription()).thenReturn("test proposal description");
		DateTime dateCreated = new DateTime(new Date()).minusDays(5);
		when(proposedConceptResponsePackage.getDateCreated()).thenReturn(dateCreated.toDate());
		when(proposedConceptResponsePackage.getProposedConcepts()).thenReturn(Sets.newHashSet(proposedConceptResponse));
	}
	
	private void verifyProposalDto(ProposedConceptResponsePackageDto proposalDto) {
		assertThat(proposalDto.getId(), is(1));
		assertThat(proposalDto.getName(), is("test proposal"));
		assertThat(proposalDto.getEmail(), is("test@test.com"));
		assertThat(proposalDto.getDescription(), is("test proposal description"));
		assertThat(proposalDto.getAge(), is("5"));
		assertThat(proposalDto.getConcepts().size(), is(1));
	}
}
