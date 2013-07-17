package org.openmrs.module.cpm.web.controller;

import org.apache.commons.codec.binary.Base64;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.ConceptNumeric;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.PackageStatus;
import org.openmrs.module.cpm.ProposedConcept;
import org.openmrs.module.cpm.ProposedConceptPackage;
import org.openmrs.module.cpm.api.ProposedConceptService;
import org.openmrs.module.cpm.web.common.CpmConstants;
import org.openmrs.module.cpm.web.dto.ProposedConceptDto;
import org.openmrs.module.cpm.web.dto.SubmissionDto;
import org.openmrs.module.cpm.web.dto.SubmissionResponseDto;
import org.openmrs.module.cpm.web.dto.concept.NumericDto;
import org.openmrs.module.cpm.web.dto.factory.DescriptionDtoFactory;
import org.openmrs.module.cpm.web.dto.factory.NameDtoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class SubmitProposal {

   	private final RestOperations submissionRestTemplate;

    private final DescriptionDtoFactory descriptionDtoFactory;

    private final NameDtoFactory nameDtoFactory;


    @Autowired
    public SubmitProposal(final RestOperations submissionRestTemplate,
                          final DescriptionDtoFactory descriptionDtoFactory,
                          final NameDtoFactory nameDtoFactory) {
        this.submissionRestTemplate = submissionRestTemplate;
        this.descriptionDtoFactory = descriptionDtoFactory;
        this.nameDtoFactory = nameDtoFactory;
    }


	void submitProposedConcept(final ProposedConceptPackage conceptPackage) {

		checkNotNull(submissionRestTemplate);

		//
		// Could not figure out how to get Spring to send a basic authentication request using the "proper" object approach
		// see: https://github.com/johnsyweb/openmrs-cpm/wiki/Gotchas
		//

		AdministrationService service = Context.getAdministrationService();
        SubmissionDto submission = createSubmissionDto(conceptPackage);

		final HttpHeaders headers = createHeaders(service.getGlobalProperty(CpmConstants.SETTINGS_USER_NAME_PROPERTY),
                service.getGlobalProperty(CpmConstants.SETTINGS_PASSWORD_PROPERTY));
		final HttpEntity requestEntity = new HttpEntity<SubmissionDto>(submission, headers);

		final String url = service.getGlobalProperty(CpmConstants.SETTINGS_URL_PROPERTY) + "/ws/cpm/dictionarymanager/proposals";
		submissionRestTemplate.exchange(url, HttpMethod.POST, requestEntity, SubmissionResponseDto.class);

//		final SubmissionResponseDto result = submissionRestTemplate.postForObject("http://localhost:8080/openmrs/ws/cpm/dictionarymanager/proposals", submission, SubmissionResponseDto.class);

		conceptPackage.setStatus(PackageStatus.SUBMITTED);
		Context.getService(ProposedConceptService.class).saveProposedConceptPackage(conceptPackage);

	}

    private SubmissionDto createSubmissionDto(final ProposedConceptPackage conceptPackage){

        SubmissionDto submission = new SubmissionDto();
        submission.setName(conceptPackage.getName());
        submission.setEmail(conceptPackage.getEmail());
        submission.setDescription(conceptPackage.getDescription());

        final ArrayList<ProposedConceptDto> list = new ArrayList<ProposedConceptDto>();
        for (ProposedConcept proposedConcept : conceptPackage.getProposedConcepts()) {
            final Concept concept = proposedConcept.getConcept();

            final ProposedConceptDto conceptDto = createProposedConceptDto(proposedConcept, concept);

            ConceptDatatype conceptDatatype = concept.getDatatype();
            populateNumberDto(conceptDto, concept, conceptDatatype);

            final ConceptClass conceptClass = concept.getConceptClass();
            if (conceptClass != null) {
                conceptDto.setConceptClass(conceptClass.getUuid());
            }

            list.add(conceptDto);
        }
        submission.setConcepts(list);
        return submission;
    }

    private void populateNumberDto (ProposedConceptDto conceptDto,  Concept concept, ConceptDatatype conceptDatatype) {
        final String uuid = conceptDatatype.getUuid();
        conceptDto.setDatatype(uuid);

        // when datatype is numeric, add numeric metadata to payload

        if (uuid.equals(ConceptDatatype.NUMERIC_UUID)) {
            ConceptService conceptService = Context.getConceptService();
            final ConceptNumeric conceptNumeric = conceptService.getConceptNumeric(concept.getId());

            NumericDto numericDto = new NumericDto();
            numericDto.setUnits(conceptNumeric.getUnits());
            numericDto.setPrecise(conceptNumeric.getPrecise());
            numericDto.setHiNormal(conceptNumeric.getHiNormal());
            numericDto.setHiCritical(conceptNumeric.getHiCritical());
            numericDto.setHiAbsolute(conceptNumeric.getHiAbsolute());
            numericDto.setLowNormal(conceptNumeric.getLowNormal());
            numericDto.setLowCritical(conceptNumeric.getLowCritical());
            numericDto.setLowAbsolute(conceptNumeric.getLowAbsolute());

            conceptDto.setNumericDetails(numericDto);
        }
    }

    private ProposedConceptDto createProposedConceptDto(ProposedConcept proposedConcept, Concept concept){
        ProposedConceptDto conceptDto = new ProposedConceptDto();

        // concept details
        conceptDto.setNames(nameDtoFactory.create(concept));
        conceptDto.setDescriptions(descriptionDtoFactory.create(concept));
        conceptDto.setUuid(concept.getUuid());
        conceptDto.setComment(proposedConcept.getComment()); // proposer's comment

        return conceptDto;
    }

	private HttpHeaders createHeaders(final String username, final String password){
		final HttpHeaders httpHeaders = new HttpHeaders();
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.encodeBase64(
				auth.getBytes(Charset.forName("US-ASCII")));
		String authHeader = "Basic " + new String( encodedAuth );
		httpHeaders.set("Authorization", authHeader);
		return httpHeaders;
	}
}