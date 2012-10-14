package org.openmrs.module.cpm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CpmController {

	@RequestMapping(value = "module/cpm/proposals.list", method = RequestMethod.GET)
	public String listProposals() {
		return "/module/cpm/proposals";
	}

}
