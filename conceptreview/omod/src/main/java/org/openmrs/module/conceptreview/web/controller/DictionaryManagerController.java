package org.openmrs.module.conceptreview.web.controller;

import org.directwebremoting.util.Logger;
import org.joda.time.DateTime;
import org.openmrs.api.APIAuthenticationException;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.ProposalStatus;
import org.openmrs.module.conceptpropose.SubmissionResponseStatus;
import org.openmrs.module.conceptpropose.web.dto.*;
import org.openmrs.module.conceptreview.ProposedConceptReview;
import org.openmrs.module.conceptreview.ProposedConceptReviewComment;
import org.openmrs.module.conceptreview.ProposedConceptReviewPackage;
import org.openmrs.module.conceptreview.api.ProposedConceptReviewService;
import org.openmrs.module.conceptreview.web.dto.factory.DtoFactory;
import org.openmrs.module.conceptreview.web.service.ConceptReviewMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class DictionaryManagerController {

	public static class ProposalSubmissionException extends RuntimeException {

		public ProposalSubmissionException(final String message) {
			super(message);
		}

	}

	@Autowired
	private ConceptReviewMapperService mapperService;

    protected final Logger log = Logger.getLogger(getClass());

	//
	// Proposer-Reviewer webservice endpoints
    //

    @RequestMapping(value = "/conceptreview/dictionarymanager/proposals", method = RequestMethod.POST)
    public @ResponseBody
	SubmissionResponseDto submitProposal(@RequestBody final SubmissionDto incomingProposal) throws IOException {

		final ProposedConceptReviewPackage result = mapperService.convertSubmissionDtoToProposedConceptReviewPackage(incomingProposal);
		final ProposedConceptReviewService service = Context.getService(ProposedConceptReviewService.class);
		SubmissionResponseDto responseDto = new SubmissionResponseDto();

	    for(ProposedConceptReview review : result.getProposedConcepts()) {
		    //Mark the initial status as received
		    review.setStatus(ProposalStatus.RECEIVED);
	    }

	    try {
			service.saveProposedConceptReviewPackage(result);
		}
		catch (Exception ex) {
			//TODO: update error handling, more specific catch block rather than the generic Exception, add proper logging etc.
			ex.printStackTrace();
			log.error("Cant save incoming proposal:" + incomingProposal);
			responseDto.setStatus(SubmissionResponseStatus.FAILURE);
			responseDto.setMessage(ex.getMessage());
			return responseDto;
		}
		responseDto.setStatus(SubmissionResponseStatus.SUCCESS);
		responseDto.setMessage("All Good!");
		responseDto.setId(result.getId());

		return responseDto;
	}

    @RequestMapping(value = "/conceptreview/dictionarymanager/proposalstatus/{sourceUUID}", method = RequestMethod.POST)
    public @ResponseBody ProposedConceptReviewPackageDto getSubmissionStatus(@PathVariable String sourceUUID) {
        final ProposedConceptReviewService service = Context.getService(ProposedConceptReviewService.class);
        final ProposedConceptReviewPackage aPackage = service.getProposedConceptReviewPackageByProposalUuid(sourceUUID);
        return DtoFactory.createProposedConceptReviewDto(aPackage);
    }

    @RequestMapping(value = "/conceptreview/dictionarymanager/status", method = RequestMethod.GET)
    public @ResponseBody String checkStatus() throws IOException {
        final String status = "Running";
        return status;
    }
    @RequestMapping(value = "/conceptreview/dictionarymanager/comment/{sourceUUID}/{sourceConceptUUID}", method = RequestMethod.POST)
    public @ResponseBody ProposedConceptReviewDto addComment(@PathVariable String sourceUUID, @PathVariable String sourceConceptUUID, @RequestBody final CommentDto incomingComment) throws IOException {
        final ProposedConceptReviewService service = Context.getService(ProposedConceptReviewService.class);
        final ProposedConceptReviewPackage aPackage = service.getProposedConceptReviewPackageByProposalUuid(sourceUUID);
        final ProposedConceptReview proposedConcept = service.getProposedConceptReviewByProposalUuidAndConceptUuid(sourceUUID, sourceConceptUUID);
        final ProposedConceptReviewComment newComment = new ProposedConceptReviewComment(incomingComment.getName(), incomingComment.getEmail(), incomingComment.getComment());
        if (proposedConcept != null) {
            newComment.setProposedConceptReview(proposedConcept);
            proposedConcept.getComments().add(newComment);
            service.saveProposedConceptReviewPackage(aPackage);
        }
        return DtoFactory.createProposedConceptReviewDto(proposedConcept);
    }

	@RequestMapping(value = "/conceptreview/dictionarymanager/discussion/{sourceUUID}/{sourceConceptUUID}", method = RequestMethod.POST)
	public @ResponseBody ProposedConceptReviewDto readComment(@PathVariable String sourceUUID, @PathVariable String sourceConceptUUID) throws IOException {
		final ProposedConceptReviewService service = Context.getService(ProposedConceptReviewService.class);
		final ProposedConceptReviewPackage aPackage = service.getProposedConceptReviewPackageByProposalUuid(sourceUUID);
		final ProposedConceptReview proposedConcept = service.getProposedConceptReviewByProposalUuidAndConceptUuid(sourceUUID, sourceConceptUUID);
		return DtoFactory.createProposedConceptReviewDto(proposedConcept);
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
