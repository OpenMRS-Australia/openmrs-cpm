package org.openmrs.module.conceptpropose.web.controller;

import org.apache.http.HttpStatus;
import org.directwebremoting.util.Logger;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.PackageStatus;
import org.openmrs.module.conceptpropose.ProposedConceptPackage;
import org.openmrs.module.conceptpropose.api.ProposedConceptService;
import org.openmrs.module.conceptpropose.web.authentication.factory.AuthHttpHeaderFactory;
import org.openmrs.module.conceptpropose.web.common.CpmConstants;
import org.openmrs.module.conceptpropose.web.dto.SubmissionDto;
import org.openmrs.module.conceptpropose.web.dto.SubmissionResponseDto;
import org.openmrs.module.conceptpropose.web.dto.factory.SubmissionDtoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class SubmitProposal {

    protected final Logger log = Logger.getLogger(getClass());

    private final RestOperations submissionRestTemplate;

    private final SubmissionDtoFactory submissionDtoFactory;

    private final AuthHttpHeaderFactory httpHeaderFactory;


    @Autowired
    public SubmitProposal(final RestOperations submissionRestTemplate,
                          final SubmissionDtoFactory submissionDtoFactory,
                          final AuthHttpHeaderFactory httpHeaderFactory) {
        this.submissionRestTemplate = submissionRestTemplate;
        this.httpHeaderFactory = httpHeaderFactory;
        this.submissionDtoFactory = submissionDtoFactory;
    }


	void submitProposedConcept(final ProposedConceptPackage conceptPackage) {

		checkNotNull(submissionRestTemplate);

		//
		// Could not figure out how to get Spring to send a basic authentication request using the "proper" object approach
		// see: https://github.com/johnsyweb/openmrs-cpm/wiki/Gotchas
		//

		AdministrationService service = Context.getAdministrationService();
        SubmissionDto submission = submissionDtoFactory.create(conceptPackage);

		HttpHeaders headers = httpHeaderFactory.create(service.getGlobalProperty(CpmConstants.SETTINGS_USER_NAME_PROPERTY),
                service.getGlobalProperty(CpmConstants.SETTINGS_PASSWORD_PROPERTY));
       // headers = createHeaders(service.getGlobalProperty(CpmConstants.SETTINGS_USER_NAME_PROPERTY),
        //        service.getGlobalProperty(CpmConstants.SETTINGS_PASSWORD_PROPERTY));
		final HttpEntity requestEntity = new HttpEntity<SubmissionDto>(submission, headers);

		final String url = service.getGlobalProperty(CpmConstants.SETTINGS_URL_PROPERTY) + "/ws/conceptreview/dictionarymanager/proposals";
		ResponseEntity responseEntity = submissionRestTemplate.exchange(url, HttpMethod.POST, requestEntity, SubmissionResponseDto.class);

//		final SubmissionResponseDto result = submissionRestTemplate.postForObject("http://localhost:8080/openmrs/ws/conceptpropose/dictionarymanager/proposals", submission, SubmissionResponseDto.class);
//
//        TODO: Find out how to determine success/failure for the submission returned by dictionarymanagercontroller
       if (responseEntity == null || !responseEntity.getStatusCode().equals(HttpStatus.SC_OK) ) {
//            throw new ConceptProposalSubmissionException("Error in submitting proposed concept");
            log.error("REsponseEntity status code is " + responseEntity.getStatusCode() );
        }
        conceptPackage.setStatus(PackageStatus.SUBMITTED);
        Context.getService(ProposedConceptService.class).saveProposedConceptPackage(conceptPackage);

	}







}