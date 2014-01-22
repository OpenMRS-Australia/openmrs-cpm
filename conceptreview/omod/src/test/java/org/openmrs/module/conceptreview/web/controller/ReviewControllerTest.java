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
import org.openmrs.module.conceptreview.ProposedConceptReview;
import org.openmrs.module.conceptreview.ProposedConceptReviewPackage;
import org.openmrs.module.conceptpropose.api.ProposedConceptService;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptReviewDto;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptReviewPackageDto;
import org.openmrs.module.conceptreview.api.ProposedConceptReviewService;
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
	ProposedConceptReviewService service;

	@Mock
	ProposedConceptReviewPackage proposedConceptReviewPackage;

	@Mock
	ProposedConceptReview proposedConceptReview;

	@InjectMocks
	ReviewController controller = new ReviewController();

	@Before
	public void before() throws Exception {
		mockStatic(Context.class);
		PowerMockito.when(Context.class, "getService", ProposedConceptReviewService.class).thenReturn(service);

		when(service.getProposedConceptReviewPackageById(1)).thenReturn(proposedConceptReviewPackage);
		when(service.getAllProposedConceptReviewPackages()).thenReturn(Lists.newArrayList(proposedConceptReviewPackage));
		when(proposedConceptReviewPackage.getProposedConcept(1)).thenReturn(proposedConceptReview);
	}

	@Test
	public void updateConceptReview_shouldPersistReviewComment() {
		final ProposedConceptReviewDto dto = new ProposedConceptReviewDto();
		dto.setReviewComment("A review comment");
		dto.setStatus(ProposalStatus.SUBMITTED);

		controller.updateConceptReview(1, 1, dto);

		verify(proposedConceptReview).setReviewComment("A review comment");
		verify(proposedConceptReview).setStatus(ProposalStatus.SUBMITTED);
		verify(proposedConceptReview, times(0)).setConcept(any(Concept.class));
		verify(service, times(1)).saveProposedConceptReviewPackage(proposedConceptReviewPackage);
	}
	
	@Test
	public void getProposalReview_shouldRetrieveProposalReview() {
		setupProposalMock();
		
		ProposedConceptReviewPackageDto proposalDto = controller.getProposalReview(1);
		
		verifyProposalDto(proposalDto);
	}
	
	@Test
	public void getProposalReviews_shouldRetrieveProposalReviews() {
		setupProposalMock();
		
		ArrayList<ProposedConceptReviewPackageDto> proposals = controller.getProposalReviews();
		
		assertThat(proposals.size(), is(1));
		verifyProposalDto(proposals.get(0));
	}
	
	private void setupProposalMock() {
		when(proposedConceptReviewPackage.getId()).thenReturn(1);
		when(proposedConceptReviewPackage.getName()).thenReturn("test proposal");
		when(proposedConceptReviewPackage.getEmail()).thenReturn("test@test.com");
		when(proposedConceptReviewPackage.getDescription()).thenReturn("test proposal description");
		DateTime dateCreated = new DateTime(new Date()).minusDays(5);
		when(proposedConceptReviewPackage.getDateCreated()).thenReturn(dateCreated.toDate());
		when(proposedConceptReviewPackage.getProposedConcepts()).thenReturn(Sets.newHashSet(proposedConceptReview));
	}
	
	private void verifyProposalDto(ProposedConceptReviewPackageDto proposalDto) {
		assertThat(proposalDto.getId(), is(1));
		assertThat(proposalDto.getName(), is("test proposal"));
		assertThat(proposalDto.getEmail(), is("test@test.com"));
		assertThat(proposalDto.getDescription(), is("test proposal description"));
		assertThat(proposalDto.getAge(), is("5"));
		assertThat(proposalDto.getConcepts().size(), is(1));
	}
}
