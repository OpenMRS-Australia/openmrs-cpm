package org.openmrs.module.cpm.web.controller;

import java.util.List;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;
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
		return "/module/cpm/monitor";
	}

	@RequestMapping(value = "module/cpm/concept.list", method = RequestMethod.GET)
	public String listConcepts() {
		return "/module/cpm/conceptList";
	}

	@ModelAttribute("conceptList")
	public List<Concept> getListBackingObject() {
		return Context.getConceptService().getAllConcepts();
	}

}
