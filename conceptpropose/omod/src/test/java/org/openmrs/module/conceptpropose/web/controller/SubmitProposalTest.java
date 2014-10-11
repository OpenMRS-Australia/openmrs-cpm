package org.openmrs.module.conceptpropose.web.controller;

import org.mockito.Matchers;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.SubmissionResponseStatus;
import org.openmrs.module.conceptpropose.api.ProposedConceptService;
import org.openmrs.module.conceptpropose.web.dto.SubmissionResponseDto;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.http.HttpHeaders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openmrs.module.conceptpropose.PackageStatus;
import org.openmrs.module.conceptpropose.ProposedConcept;
import org.openmrs.module.conceptpropose.ProposedConceptPackage;
import org.openmrs.module.conceptpropose.web.authentication.factory.AuthHttpHeaderFactory;
import org.openmrs.module.conceptpropose.web.common.CpmConstants;
import org.openmrs.module.conceptpropose.web.dto.SubmissionDto;
import org.openmrs.module.conceptpropose.web.dto.factory.SubmissionDtoFactory;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestOperations;
import org.mockito.Mock;

import java.util.*;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;


@RunWith(PowerMockRunner.class)
@PrepareForTest({Context.class})
public class SubmitProposalTest {

    @Mock
    private ProposedConceptService service;

    @Mock
    private AdministrationService adminService;

    @Mock
    private RestOperations submissionRestTemplate;

    @Mock
    private SubmissionDtoFactory submissionDtoFactory;

    @Mock
    private SubmissionDto submissionDto;

    @Mock
    private SubmissionResponseDto submissionResponseDto;

    @Mock
    private AuthHttpHeaderFactory httpHeaderFactory;

    @Mock
    private HttpHeaders httpHeaders;

    private SubmitProposal submitProposal;

    @Before
    public void setup() throws Exception {
        mockStatic(Context.class);

        PowerMockito.when(Context.class, "getAdministrationService").thenReturn(adminService);
        PowerMockito.when(Context.class, "getService", ProposedConceptService.class).thenReturn(service);

        submitProposal = new SubmitProposal(submissionRestTemplate, submissionDtoFactory, httpHeaderFactory);
    }

    @Test
    public void test_submitProposalNoConcepts() {
        ProposedConceptPackage conceptPackage = new ProposedConceptPackage();
        conceptPackage.setStatus(PackageStatus.TBS);
        conceptPackage.setName("new draft proposal");
        conceptPackage.setEmail("some@email.com");
        conceptPackage.setDescription("new draft proposal description");
        final Set<ProposedConcept> concepts = new HashSet<ProposedConcept>();

        conceptPackage.setProposedConcepts(concepts);

        boolean exceptionWasThrown = false;

        try {
            submitProposal.submitProposedConcept(conceptPackage);
        } catch (ProposalController.ProposalSubmissionException e) {
            exceptionWasThrown = true;
            assertThat(e.getHttpStatus(), is(HttpStatus.UNPROCESSABLE_ENTITY));
        }

        assertThat(exceptionWasThrown, is(true));
        assertThat(conceptPackage.getStatus(), is(PackageStatus.DRAFT));
    }

    @Test
    public void test_submitProposalWithConcept() throws Exception {
        ProposedConceptPackage conceptPackage = new ProposedConceptPackage();
        conceptPackage.setStatus(PackageStatus.TBS);
        conceptPackage.setName("new draft proposal");
        conceptPackage.setEmail("some@email.com");
        conceptPackage.setDescription("new draft proposal description");
        final Set<ProposedConcept> concepts = new HashSet<ProposedConcept>();
        final ProposedConcept concept = new ProposedConcept();
        concept.setUuid("concept-uuid");
        concepts.add(concept);

        conceptPackage.setProposedConcepts(concepts);

        boolean exceptionWasThrown = false;

        when(submissionDtoFactory.create(conceptPackage)).thenReturn(submissionDto);
        when(adminService.getGlobalProperty(CpmConstants.SETTINGS_USER_NAME_PROPERTY)).thenReturn("Fred");
        when(adminService.getGlobalProperty(CpmConstants.SETTINGS_PASSWORD_PROPERTY)).thenReturn("Password1");
        when(adminService.getGlobalProperty(CpmConstants.SETTINGS_URL_PROPERTY)).thenReturn("whatever");
        when(submissionRestTemplate.postForObject(Matchers.anyString(),
                anyObject(), eq(SubmissionResponseDto.class))).thenReturn(submissionResponseDto);
        when(submissionResponseDto.getStatus()).thenReturn(SubmissionResponseStatus.SUCCESS);
        when(service.saveProposedConceptPackage(conceptPackage)).thenReturn(conceptPackage);

        try {
            submitProposal.submitProposedConcept(conceptPackage);
        } catch (ProposalController.ProposalSubmissionException e) {
            exceptionWasThrown = true;
        }

        assertThat(exceptionWasThrown, is(false));
        assertThat(conceptPackage.getStatus(), is(PackageStatus.SUBMITTED));
    }
}
