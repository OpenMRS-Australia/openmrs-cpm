package org.openmrs.module.cpm.web.controller;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.model.Proposal;
import org.openmrs.module.cpm.service.ProposalService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CpmController {

	@RequestMapping(value = "module/cpm/concept.form", method = RequestMethod.GET)
	public String createConceptForm() {
		return "/module/cpm/conceptCreate";
	}

	@RequestMapping(value = "module/cpm/monitor.list", method = RequestMethod.GET)
	public String monitorProposalsList() {
		return "/module/cpm/conceptList";
	}

	@ModelAttribute("proposalList")
	public List<Proposal> getListBackingObject() {
		return Context.getService(ProposalService.class).findAll();
	}

	@RequestMapping(value = "module/cpm/proposals.list", method = RequestMethod.GET)
	public void listConcepts() {
	}
}
