package org.openmrs.module.conceptreview.web.controller;

import org.directwebremoting.util.Logger;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.api.APIAuthenticationException;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.SubmissionResponseStatus;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptDto;
import org.openmrs.module.conceptpropose.web.dto.SubmissionDto;
import org.openmrs.module.conceptpropose.web.dto.SubmissionResponseDto;
import org.openmrs.module.conceptpropose.web.dto.SubmissionStatusDto;
import org.openmrs.module.conceptpropose.web.dto.concept.DescriptionDto;
import org.openmrs.module.conceptpropose.web.dto.concept.NameDto;
import org.openmrs.module.conceptpropose.web.dto.concept.NumericDto;
import org.openmrs.module.conceptreview.ProposedConceptReview;
import org.openmrs.module.conceptreview.ProposedConceptReviewDescription;
import org.openmrs.module.conceptreview.ProposedConceptReviewName;
import org.openmrs.module.conceptreview.ProposedConceptReviewNumeric;
import org.openmrs.module.conceptreview.ProposedConceptReviewPackage;
import org.openmrs.module.conceptreview.api.ProposedConceptReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class DictionaryManagerController {

	public static class ProposalSubmissionException extends RuntimeException {

		public ProposalSubmissionException(final String message) {
			super(message);
		}

	}

    protected final Logger log = Logger.getLogger(getClass());

	//
	// Proposer-Reviewer webservice endpoints
    //

    @RequestMapping(value = "/conceptreview/dictionarymanager/proposals", method = RequestMethod.POST)
    public @ResponseBody
	SubmissionResponseDto submitProposal(@RequestBody final SubmissionDto incomingProposal) throws IOException {

        //TODO: method size getting large...
        final ProposedConceptReviewPackage proposedConceptReviewPackage = new ProposedConceptReviewPackage();
        SubmissionResponseDto responseDto = new SubmissionResponseDto();

        try{
            final ProposedConceptReviewService service = Context.getService(ProposedConceptReviewService.class);
            proposedConceptReviewPackage.setName(incomingProposal.getName());
            proposedConceptReviewPackage.setEmail(incomingProposal.getEmail());
            proposedConceptReviewPackage.setDescription(incomingProposal.getDescription());
            proposedConceptReviewPackage.setProposedConceptPackageUuid("is-this-really-needed?");

            if (incomingProposal.getConcepts() == null) {
                throw new ProposalSubmissionException("Concepts are missing in the submission");

            }
            for (ProposedConceptDto concept : incomingProposal.getConcepts()) {
                ProposedConceptReview response = new ProposedConceptReview();

                List<ProposedConceptReviewName> names = new ArrayList<ProposedConceptReviewName>();
                if (concept.getNames() == null || concept.getNames().isEmpty()) {
                    throw new ProposalSubmissionException("Missing concept names for conceptId:" + concept.getId());

                }
                for (NameDto nameDto: concept.getNames()) {
                    ProposedConceptReviewName name = new ProposedConceptReviewName();
                    name.setName(nameDto.getName());
                    name.setType(nameDto.getType());
                    name.setLocale(new Locale(nameDto.getLocale()));
                    names.add(name);
                }
                response.setNames(names);

                List<ProposedConceptReviewDescription> descriptions = new ArrayList<ProposedConceptReviewDescription>();
                if (concept.getDescriptions() == null || concept.getDescriptions().isEmpty()) {
                    throw new ProposalSubmissionException("Missing concept description for conceptId:" + concept.getId());

                }
                for (DescriptionDto descriptionDto: concept.getDescriptions()) {
                    ProposedConceptReviewDescription description = new ProposedConceptReviewDescription();
                    description.setDescription(descriptionDto.getDescription());
                    description.setLocale(new Locale(descriptionDto.getLocale()));
                    descriptions.add(description);
                }
                response.setDescriptions(descriptions);

                response.setProposedConceptUuid(concept.getUuid());
                response.setComment(concept.getComment());

                final ConceptDatatype conceptDatatype = Context.getConceptService().getConceptDatatypeByUuid(concept.getDatatype());
                if (conceptDatatype == null) {
                    throw new ProposalSubmissionException("Datatype is missing for conceptId:" + concept.getId());
                }
                response.setDatatype(conceptDatatype);

                if (conceptDatatype.getUuid().equals(ConceptDatatype.NUMERIC_UUID)) {

                    final NumericDto numericDetails = concept.getNumericDetails();
                    ProposedConceptReviewNumeric proposedConceptReviewNumeric = new ProposedConceptReviewNumeric();

                    proposedConceptReviewNumeric.setUnits(numericDetails.getUnits());
                    proposedConceptReviewNumeric.setPrecise(numericDetails.getPrecise());
                    proposedConceptReviewNumeric.setHiNormal(numericDetails.getHiNormal());
                    proposedConceptReviewNumeric.setHiCritical(numericDetails.getHiCritical());
                    proposedConceptReviewNumeric.setHiAbsolute(numericDetails.getHiAbsolute());
                    proposedConceptReviewNumeric.setLowNormal(numericDetails.getLowNormal());
                    proposedConceptReviewNumeric.setLowCritical(numericDetails.getLowCritical());
                    proposedConceptReviewNumeric.setLowAbsolute(numericDetails.getLowAbsolute());

                    response.setNumericDetails(proposedConceptReviewNumeric);
                }

                final ConceptClass conceptClass = Context.getConceptService().getConceptClassByUuid(concept.getConceptClass());
                if (conceptClass == null) {
                    throw new ProposalSubmissionException("Concept class expected for conceptId:"+ concept.getId());
                }
                response.setConceptClass(conceptClass);

                proposedConceptReviewPackage.addProposedConcept(response);
            }


            service.saveProposedConceptReviewPackage(proposedConceptReviewPackage);
            responseDto.setStatus(SubmissionResponseStatus.SUCCESS);
            responseDto.setMessage("All Good!");
            if (proposedConceptReviewPackage.getId() != null) {
            	responseDto.setId(proposedConceptReviewPackage.getId());
            }

        } catch (Exception ex) {
            //TODO: update error handling, more specific catch block rather than the generic Exception, add proper logging etc.
            ex.printStackTrace();
            responseDto.setStatus(SubmissionResponseStatus.FAILURE);
            responseDto.setMessage(ex.getMessage());
        }

        return responseDto;
    }

    @RequestMapping(value = "/conceptreview/dictionarymanager/proposalstatus/{proposalId}", method = RequestMethod.GET)
    public @ResponseBody SubmissionStatusDto getSubmissionStatus(@PathVariable int proposalId) {

        final ProposedConceptReviewService service = Context.getService(ProposedConceptReviewService.class);
        final ProposedConceptReviewPackage aPackage = service.getProposedConceptReviewPackageById(proposalId);

        return new SubmissionStatusDto(aPackage.getStatus());
    }

    @ExceptionHandler(APIAuthenticationException.class)
    public void apiAuthenticationExceptionHandler(Exception e, HttpServletResponse response) {

        if (Context.isAuthenticated()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.addHeader("WWW-Authenticate", "Basic realm=\"OpenMRS\"");
        }
    }
}
