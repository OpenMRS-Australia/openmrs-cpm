package org.openmrs.module.cpm.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.PackageStatus;
import org.openmrs.module.cpm.ProposedConceptResponse;
import org.openmrs.module.cpm.ProposedConceptResponseName;
import org.openmrs.module.cpm.ProposedConceptResponsePackage;
import org.openmrs.module.cpm.api.ProposedConceptService;
import org.openmrs.module.cpm.web.dto.ProposedConceptDto;
import org.openmrs.module.cpm.web.dto.ProposedConceptPackageDto;
import org.openmrs.module.cpm.web.dto.ProposedConceptResponseDto;
import org.openmrs.module.cpm.web.dto.ProposedConceptResponsePackageDto;
import org.openmrs.module.cpm.web.dto.concept.NameDto;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
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

	@Mock
	SubmitProposal submitProposal;

	@Mock
	UpdateProposedConceptPackage updateProposedConceptPackage;

	@InjectMocks
	ReviewController controller = new ReviewController();

	@Before
	public void before() throws Exception {
		mockStatic(Context.class);
		PowerMockito.when(Context.class, "getService", ProposedConceptService.class).thenReturn(service);

		when(service.getProposedConceptResponsePackageById(1)).thenReturn(proposedConceptResponsePackage);
		when(proposedConceptResponsePackage.getProposedConcept(1)).thenReturn(proposedConceptResponse);
	}

	@Test
	public void updateConceptResponse_shouldPersistReviewComment() {
		final ProposedConceptResponseDto dto = new ProposedConceptResponseDto();
		dto.setReviewComment("A review comment");

		controller.updateConceptResponse(1, 1, dto);

		verify(proposedConceptResponse).setReviewComment("A review comment");
	}

	@Test
	public void getConceptResponse_shouldRetrieveReviewComment() {
		when(proposedConceptResponse.getReviewComment()).thenReturn("Another review comment");

		final ProposedConceptResponseDto conceptResponse = controller.getConceptResponse(1, 1);

		assertThat(conceptResponse.getReviewComment(), is(equalTo("Another review comment")));
	}
}
