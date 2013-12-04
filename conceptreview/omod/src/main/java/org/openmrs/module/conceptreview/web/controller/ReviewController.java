package org.openmrs.module.conceptreview.web.controller;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptReviewDto;
import org.openmrs.module.conceptreview.ProposedConceptReview;
import org.openmrs.module.conceptreview.ProposedConceptReviewPackage;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptReviewPackageDto;
import org.openmrs.module.conceptreview.api.ProposedConceptReviewService;
import org.openmrs.module.conceptreview.web.dto.factory.DtoFactory;
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

	@RequestMapping(value = "module/conceptpropose/proposalReview.list", method = RequestMethod.GET)
	public String listProposalReview() {
		return "/module/conceptpropose/proposalReview";
	}

	//
	// REST endpoints
	//

	@RequestMapping(value = "/conceptpropose/proposalReviews", method = RequestMethod.GET)
	public @ResponseBody ArrayList<ProposedConceptReviewPackageDto> getProposalReviews() {

		final List<ProposedConceptReviewPackage> allConceptProposalReviewPackages = Context.getService(ProposedConceptReviewService.class).getAllProposedConceptReviewPackages();
		final ArrayList<ProposedConceptReviewPackageDto> response = new ArrayList<ProposedConceptReviewPackageDto>();

		for (final ProposedConceptReviewPackage conceptProposalReviewPackage : allConceptProposalReviewPackages) {

			final ProposedConceptReviewPackageDto conceptProposalReviewPackageDto = createProposedConceptReviewPackageDto(conceptProposalReviewPackage);
			response.add(conceptProposalReviewPackageDto);
		}

		return response;
	}

	@RequestMapping(value = "/conceptpropose/proposalReviews/{proposalId}", method = RequestMethod.GET)
	public @ResponseBody ProposedConceptReviewPackageDto getProposalReview(@PathVariable int proposalId) {
		return createProposedConceptReviewPackageDto(Context.
				getService(ProposedConceptReviewService.class).
				getProposedConceptReviewPackageById(proposalId));
	}

	@RequestMapping(value = "/conceptpropose/proposalReviews/{proposalId}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteProposalReview(@PathVariable int proposalId) {
		Context.getService(ProposedConceptReviewService.class).deleteProposedConceptReviewPackageById(proposalId);
	}

	@RequestMapping(value = "/conceptpropose/proposalReviews/{proposalId}/concepts/{conceptId}", method = RequestMethod.GET)
	public @ResponseBody
	ProposedConceptReviewDto getConceptReview(@PathVariable int proposalId, @PathVariable int conceptId) {
		final ProposedConceptReviewService service = Context.getService(ProposedConceptReviewService.class);
		final ProposedConceptReview proposedConcept = service.getProposedConceptReviewPackageById(proposalId).getProposedConcept(conceptId);
		return DtoFactory.createProposedConceptReviewDto(proposedConcept);
	}

	@RequestMapping(value = "/conceptpropose/proposalReviews/{proposalId}/concepts/{conceptId}", method = RequestMethod.PUT)
	public @ResponseBody
	ProposedConceptReviewDto updateConceptReview(@PathVariable int proposalId, @PathVariable int conceptId, @RequestBody ProposedConceptReviewDto updatedProposalReview) {
		final ProposedConceptReviewService service = Context.getService(ProposedConceptReviewService.class);
		final ProposedConceptReviewPackage aPackage = service.getProposedConceptReviewPackageById(proposalId);
		final ProposedConceptReview proposedConcept = aPackage.getProposedConcept(conceptId);
		if (proposedConcept != null) {
			proposedConcept.setReviewComment(updatedProposalReview.getReviewComment());
			proposedConcept.setStatus(updatedProposalReview.getStatus());

			if (updatedProposalReview.getConceptId() != 0) {
				proposedConcept.setConcept(Context.getConceptService().getConcept(updatedProposalReview.getConceptId()));
			}

			service.saveProposedConceptReviewPackage(aPackage);
		}
		return DtoFactory.createProposedConceptReviewDto(proposedConcept);
	}

	private ProposedConceptReviewPackageDto createProposedConceptReviewPackageDto(final ProposedConceptReviewPackage responsePackage) {

		final ProposedConceptReviewPackageDto dto = new ProposedConceptReviewPackageDto();
		dto.setId(responsePackage.getId());
		dto.setName(responsePackage.getName());
		dto.setEmail(responsePackage.getEmail());
		dto.setDescription(responsePackage.getDescription());

		if (responsePackage.getDateCreated() == null) {
			throw new NullPointerException("Date created is null");
		}
		Days d = Days.daysBetween(new DateTime(responsePackage.getDateCreated()), new DateTime(new Date()));
		dto.setAge(String.valueOf(d.getDays()));

		final Set<ProposedConceptReview> proposedConcepts = responsePackage.getProposedConcepts();
		final List<ProposedConceptReviewDto> list = new ArrayList<ProposedConceptReviewDto>();
		if (proposedConcepts != null) {
			for (final ProposedConceptReview conceptProposal : proposedConcepts) {
				list.add(DtoFactory.createProposedConceptReviewDto(conceptProposal));
			}
		}

		dto.setConcepts(list);
		return dto;
	}
}
