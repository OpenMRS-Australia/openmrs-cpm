package org.openmrs.module.cpm.web.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.ProposedConceptResponsePackage;
import org.openmrs.module.cpm.api.ProposedConceptService;
import org.openmrs.module.cpm.web.dto.SubmissionDto;
import org.openmrs.module.cpm.web.dto.SubmissionResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DictionaryManagerController {

    @RequestMapping(value = "/cpm/dictionarymanager/proposals", method = RequestMethod.POST)
    public @ResponseBody SubmissionResponseDto submitProposal(@RequestBody final SubmissionDto incomingProposal) {

		final ProposedConceptService service = Context.getService(ProposedConceptService.class);
		final ProposedConceptResponsePackage proposedConceptResponsePackage = new ProposedConceptResponsePackage();
		proposedConceptResponsePackage.setName(incomingProposal.getName());
		proposedConceptResponsePackage.setEmail(incomingProposal.getEmail());
		proposedConceptResponsePackage.setDescription(incomingProposal.getDescription());

		// this is currently throwing an exception due to the user authenticated with basic auth not having
		// the appropriate ConceptProposalConsts.MODULE_PRIVILEGE
//		service.saveProposedConceptResponsePackage(proposedConceptResponsePackage);

		SubmissionResponseDto responseDto = new SubmissionResponseDto();
        responseDto.setStatus("OK");
        return responseDto;
    }
}
