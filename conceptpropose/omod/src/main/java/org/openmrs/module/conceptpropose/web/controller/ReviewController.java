package org.openmrs.module.conceptpropose.web.controller;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.ProposedConceptResponse;
import org.openmrs.module.conceptpropose.ProposedConceptResponsePackage;
import org.openmrs.module.conceptpropose.api.ProposedConceptService;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptResponseDto;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptResponsePackageDto;
import org.openmrs.module.conceptpropose.web.dto.factory.DtoFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class ReviewController {

	//
	// Pages
	//

	@RequestMapping(value = "module/cpm/proposalReview.list", method = RequestMethod.GET)
	public String listProposalReview() {
		return "/module/cpm/proposalReview";
	}

	//
	// REST endpoints
	//

	@RequestMapping(value = "/cpm/proposalReviews", method = RequestMethod.GET)
	public @ResponseBody ArrayList<ProposedConceptResponsePackageDto> getProposalResponses() {

		final List<ProposedConceptResponsePackage> allConceptProposalResponsePackages = Context.getService(ProposedConceptService.class).getAllProposedConceptResponsePackages();
		final ArrayList<ProposedConceptResponsePackageDto> response = new ArrayList<ProposedConceptResponsePackageDto>();

		for (final ProposedConceptResponsePackage conceptProposalResponsePackage : allConceptProposalResponsePackages) {

			final ProposedConceptResponsePackageDto conceptProposalResponsePackageDto = createProposedConceptResponsePackageDto(conceptProposalResponsePackage);
			response.add(conceptProposalResponsePackageDto);
		}

		return response;
	}

	@RequestMapping(value = "/cpm/proposalReviews/{proposalId}", method = RequestMethod.GET)
	public @ResponseBody ProposedConceptResponsePackageDto getProposalResponse(@PathVariable int proposalId) {
		return createProposedConceptResponsePackageDto(Context.
				getService(ProposedConceptService.class).
				getProposedConceptResponsePackageById(proposalId));
	}

	@RequestMapping(value = "/cpm/proposalReviews/{proposalId}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteProposalResponse(@PathVariable int proposalId) {
		Context.getService(ProposedConceptService.class).deleteProposedConceptResponsePackageById(proposalId);
	}

	@RequestMapping(value = "/cpm/proposalReviews/{proposalId}/concepts/{conceptId}", method = RequestMethod.GET)
	public @ResponseBody ProposedConceptResponseDto getConceptResponse(@PathVariable int proposalId, @PathVariable int conceptId) {
		final ProposedConceptService service = Context.getService(ProposedConceptService.class);
		final ProposedConceptResponse proposedConcept = service.getProposedConceptResponsePackageById(proposalId).getProposedConcept(conceptId);
		return DtoFactory.createProposedConceptResponseDto(proposedConcept);
	}

	@RequestMapping(value = "/cpm/proposalReviews/{proposalId}/concepts/{conceptId}", method = RequestMethod.PUT)
	public @ResponseBody
	ProposedConceptResponseDto updateConceptResponse(@PathVariable int proposalId, @PathVariable int conceptId, @RequestBody ProposedConceptResponseDto updatedProposalResponse) {
		final ProposedConceptService service = Context.getService(ProposedConceptService.class);
		final ProposedConceptResponsePackage aPackage = service.getProposedConceptResponsePackageById(proposalId);
		final ProposedConceptResponse proposedConcept = aPackage.getProposedConcept(conceptId);
		if (proposedConcept != null) {
			proposedConcept.setReviewComment(updatedProposalResponse.getReviewComment());
			proposedConcept.setStatus(updatedProposalResponse.getStatus());

			if (updatedProposalResponse.getConceptId() != 0) {
				proposedConcept.setConcept(Context.getConceptService().getConcept(updatedProposalResponse.getConceptId()));
			}

			service.saveProposedConceptResponsePackage(aPackage);
		}
		return DtoFactory.createProposedConceptResponseDto(proposedConcept);
	}

	private ProposedConceptResponsePackageDto createProposedConceptResponsePackageDto(final ProposedConceptResponsePackage responsePackage) {

		final ProposedConceptResponsePackageDto dto = new ProposedConceptResponsePackageDto();
		dto.setId(responsePackage.getId());
		dto.setName(responsePackage.getName());
		dto.setEmail(responsePackage.getEmail());
		dto.setDescription(responsePackage.getDescription());

		if (responsePackage.getDateCreated() == null) {
			throw new NullPointerException("Date created is null");
		}
		Days d = Days.daysBetween(new DateTime(responsePackage.getDateCreated()), new DateTime(new Date()));
		dto.setAge(String.valueOf(d.getDays()));

		final Set<ProposedConceptResponse> proposedConcepts = responsePackage.getProposedConcepts();
		final List<ProposedConceptResponseDto> list = new ArrayList<ProposedConceptResponseDto>();
		if (proposedConcepts != null) {
			for (final ProposedConceptResponse conceptProposal : proposedConcepts) {
				list.add(DtoFactory.createProposedConceptResponseDto(conceptProposal));
			}
		}

		dto.setConcepts(list);
		return dto;
	}
}
