package org.openmrs.module.cpm.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.ProposedConcept;
import org.openmrs.module.cpm.ProposedConceptPackage;
import org.openmrs.module.cpm.ShareableComment;
import org.openmrs.module.cpm.api.ProposedConceptService;
import org.openmrs.module.cpm.web.dto.ProposedConceptDto;
import org.openmrs.module.cpm.web.dto.ProposedConceptPackageDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CpmController {

	@RequestMapping(value = "module/cpm/proposals.list", method = RequestMethod.GET)
	public String listProposals() {
		return "/module/cpm/proposals";
	}

	@RequestMapping(value = "/cpm/proposals", method = RequestMethod.GET)
	public @ResponseBody ArrayList<ProposedConceptPackageDto> getProposals() {

		final List<ProposedConceptPackage> allConceptProposalPackages = Context.getService(ProposedConceptService.class).getAllProposedConceptPackages();
		final ArrayList<ProposedConceptPackageDto> response = new ArrayList<ProposedConceptPackageDto>();

		for (final ProposedConceptPackage conceptProposalPackage : allConceptProposalPackages) {

			final ProposedConceptPackageDto conceptProposalPackageDto = new ProposedConceptPackageDto();
			conceptProposalPackageDto.setId(conceptProposalPackage.getId());
			conceptProposalPackageDto.setEmail(conceptProposalPackage.getEmail());
			conceptProposalPackageDto.setDescription(conceptProposalPackage.getDescription());
			conceptProposalPackageDto.setStatus(conceptProposalPackage.getStatus());

			final Set<ProposedConcept> proposedConcepts = conceptProposalPackage.getProposedConcepts();
			final List<ProposedConceptDto> list = new ArrayList<ProposedConceptDto>();

			for (final ProposedConcept conceptProposal : proposedConcepts) {

				final ProposedConceptDto conceptProposalDto = new ProposedConceptDto();
//				conceptProposalDto.setConceptId(conceptProposal.get??)
				conceptProposalDto.setName(conceptProposal.getName());
//				conceptProposalDto.setComments(conceptProposal.getComments()); type mismatch
				conceptProposalDto.setStatus(conceptProposal.getStatus());

				list.add(conceptProposalDto);
			}

			conceptProposalPackageDto.setConcepts(list);
			response.add(conceptProposalPackageDto);
		}

		return response;
	}

	@RequestMapping(value = "/cpm/proposals", method = RequestMethod.POST)
	public @ResponseBody ProposedConceptPackageDto addProposal(@RequestBody final ProposedConceptPackageDto newPackage) {

		// TODO: some server side validation here... not null fields, valid email?

		final ProposedConceptPackage conceptPackage = new ProposedConceptPackage();
		conceptPackage.setName(newPackage.getName());
		conceptPackage.setEmail(newPackage.getEmail());
		conceptPackage.setDescription(newPackage.getDescription());

		if (newPackage.getConcepts() != null) {
			for (final ProposedConceptDto newProposedConcept : newPackage.getConcepts()) {

				final ProposedConcept proposedConcept = new ProposedConcept();
				final ConceptService conceptService = Context.getConceptService();
				final Concept concept = conceptService.getConcept(newProposedConcept.getConceptId());
				proposedConcept.setConcept(concept);

				final Set<ShareableComment> comments = new HashSet<ShareableComment>();
				final ShareableComment comment = new ShareableComment();
				comment.setComment(newProposedConcept.getComments());
				proposedConcept.setComments(comments);
			}
		}

		Context.getService(ProposedConceptService.class).saveProposedConceptPackage(conceptPackage);

		// Return the DTO with the new ID for the benefit of the client
		newPackage.setId(conceptPackage.getId());
		return newPackage;
	}

}
