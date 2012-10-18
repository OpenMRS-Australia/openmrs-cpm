package org.openmrs.module.cpm.web.controller;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.ConceptProposalPackage;
import org.openmrs.module.cpm.api.ConceptProposalService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CpmController {

	@RequestMapping(value = "module/cpm/proposals.list", method = RequestMethod.GET)
	public String listProposals() {
		return "/module/cpm/proposals";
	}

	@RequestMapping(value = "module/cpm/rest/proposals.list", method = RequestMethod.GET)
	public @ResponseBody List<ConceptProposalPackage> getProposals() {
		return Context.getService(ConceptProposalService.class).getAllConceptProposalPackages();
	}

}
