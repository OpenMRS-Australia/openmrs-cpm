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
import org.openmrs.module.conceptreview.web.service.ConceptReviewMapperService;
import org.springframework.beans.factory.annotation.Autowired;
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

		try {
			service.saveProposedConceptReviewPackage(result);
		}
		catch (Exception ex) {
			//TODO: update error handling, more specific catch block rather than the generic Exception, add proper logging etc.
			ex.printStackTrace();
			responseDto.setStatus(SubmissionResponseStatus.FAILURE);
			responseDto.setMessage(ex.getMessage());
			return responseDto;
		}
		responseDto.setStatus(SubmissionResponseStatus.SUCCESS);
		responseDto.setMessage("All Good!");
		responseDto.setId(result.getId());

		return responseDto;
	}

    @RequestMapping(value = "/conceptreview/dictionarymanager/proposalstatus/{proposalId}", method = RequestMethod.GET)
    public @ResponseBody SubmissionStatusDto getSubmissionStatus(@PathVariable int proposalId) {

        final ProposedConceptReviewService service = Context.getService(ProposedConceptReviewService.class);
        final ProposedConceptReviewPackage aPackage = service.getProposedConceptReviewPackageById(proposalId);

        return new SubmissionStatusDto(aPackage.getStatus());
    }

    @RequestMapping(value = "/conceptreview/dictionarymanager/status", method = RequestMethod.GET)
    public @ResponseBody String checkStatus() throws IOException {
        final String status = "Running";
        return status;
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
