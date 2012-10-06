package org.openmrs.module.cpm.web.controller;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.model.Proposal;
import org.openmrs.module.cpm.service.ProposalService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CpmController {

	@ModelAttribute("proposalList")
	public List<Proposal> getListBackingObject() {
		return Context.getService(ProposalService.class).findAll();
	}

	@RequestMapping(value = "module/cpm/proposals.list", method = RequestMethod.GET)
	public String listConcepts() {
		return "/module/cpm/proposals";
	}

	@RequestMapping(value = "module/cpm/rest/proposals.list", method = RequestMethod.GET)
	public @ResponseBody List<Proposal> getProposals() {
		return Context.getService(ProposalService.class).findAll();
	}

	@RequestMapping(value = "module/cpm/rest/proposals/{proposalId}.form", method = RequestMethod.GET)
	public @ResponseBody Proposal listAjaxProposals(@PathVariable final String proposalId) {
		return Context.getService(ProposalService.class).getProposalById(Integer.valueOf(proposalId));
	}
}
