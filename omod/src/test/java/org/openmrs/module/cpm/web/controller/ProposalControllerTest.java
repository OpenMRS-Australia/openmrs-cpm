package org.openmrs.module.cpm.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.PackageStatus;
import org.openmrs.module.cpm.ProposedConceptPackage;
import org.openmrs.module.cpm.api.ProposedConceptService;
import org.openmrs.module.cpm.web.dto.ProposedConceptPackageDto;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.web.client.RestOperations;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Context.class)
public class ProposalControllerTest {

	@Mock
	ProposedConceptService service;

	@Mock
	ProposedConceptPackage conceptPackage;

	@Mock
	SubmitProposal submitProposal;

	@Mock
	UpdateProposedConceptPackage updateProposedConceptPackage;

	@InjectMocks
	ProposalController controller = new ProposalController();

	@Before
	public void before() throws Exception {
		mockStatic(Context.class);
		PowerMockito.when(Context.class, "getService", ProposedConceptService.class).thenReturn(service);
		when(service.getProposedConceptPackageById(1)).thenReturn(conceptPackage);
	}

	@Test
	public void updateProposal_sendProposal_shouldPersistChangesAndSendProposal() throws Exception {

		when(conceptPackage.getStatus()).thenReturn(PackageStatus.DRAFT);

		ProposedConceptPackageDto dto = new ProposedConceptPackageDto();
		dto.setStatus(PackageStatus.TBS);
		controller.updateProposal("1", dto);

		InOrder inOrder = inOrder(updateProposedConceptPackage, submitProposal);
		inOrder.verify(updateProposedConceptPackage).updateProposedConcepts(conceptPackage, dto);
		inOrder.verify(submitProposal).submitProposedConcept(conceptPackage, null);
	}

	@Test
	public void updateProposal_saveProposal_shouldPersistChanges() {

		ProposedConceptPackageDto dto = new ProposedConceptPackageDto();
		controller.updateProposal("1", dto);

		verify(updateProposedConceptPackage).updateProposedConcepts(conceptPackage, dto);
		verify(submitProposal, times(0)).submitProposedConcept(conceptPackage, null);
	}
}
