package org.openmrs.module.cpm.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.ConceptDescription;
import org.openmrs.ConceptName;
import org.openmrs.ConceptSearchResult;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.ProposedConcept;
import org.openmrs.module.cpm.ProposedConceptPackage;
import org.openmrs.module.cpm.ShareableComment;
import org.openmrs.module.cpm.api.ProposedConceptService;
import org.openmrs.module.cpm.web.dto.ConceptDto;
import org.openmrs.module.cpm.web.dto.ProposedConceptDto;
import org.openmrs.module.cpm.web.dto.ProposedConceptPackageDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CpmController {

	@RequestMapping(value = "module/cpm/proposals.list", method = RequestMethod.GET)
	public String listProposals() {
		return "/module/cpm/proposals";
	}

	@RequestMapping(value = "/cpm/concepts", method = RequestMethod.GET)
	public @ResponseBody List<ConceptDto> findConcepts(@RequestParam final String query) {
		final ArrayList<ConceptDto> results = new ArrayList<ConceptDto>();
		final ConceptService conceptService = Context.getConceptService();

		if (query.equals("")) {
			final List<Concept> allConcepts = conceptService.getAllConcepts("name", true, false);
//			final List<Concept> allConcepts = conceptService.getAllConcepts();
			for (final Concept concept : allConcepts) {
				results.add(createConceptDto(concept));
			}
		} else {
			final List<ConceptSearchResult> concepts = conceptService.getConcepts(query, Context.getLocale(), false);
			for (final ConceptSearchResult conceptSearchResult : concepts) {
				results.add(createConceptDto(conceptSearchResult.getConcept()));
			}
		}
		return results;
	}

	private ConceptDto createConceptDto(final Concept concept) {

		final ConceptDto dto = new ConceptDto();
		dto.setName(concept.getName().getName());

		String synonyms = "";
		boolean first = true;
		for (final ConceptName conceptName : concept.getNames()) {
			if (conceptName.getName().equals(concept.getName().getName())) {
				continue;
			}
			if (first) {
				first = false;
			} else {
				synonyms += ", ";
			}
			synonyms += conceptName.getName();
		}
		dto.setSynonyms(synonyms);

		dto.setDatatype(concept.getDatatype().getName());
		if (concept.getDescription() != null) {
			dto.setDescription(concept.getDescription().getDescription());
		}
		return dto;
	}

	@RequestMapping(value = "/cpm/proposals", method = RequestMethod.GET)
	public @ResponseBody ArrayList<ProposedConceptPackageDto> getProposals() {

		final List<ProposedConceptPackage> allConceptProposalPackages = Context.getService(ProposedConceptService.class).getAllProposedConceptPackages();
		final ArrayList<ProposedConceptPackageDto> response = new ArrayList<ProposedConceptPackageDto>();

		for (final ProposedConceptPackage conceptProposalPackage : allConceptProposalPackages) {

			final ProposedConceptPackageDto conceptProposalPackageDto = createProposedConceptPackageDto(conceptProposalPackage);
			response.add(conceptProposalPackageDto);
		}

		return response;
	}

	@RequestMapping(value = "/cpm/proposals/{proposalId}", method = RequestMethod.GET)
	public @ResponseBody ProposedConceptPackageDto getProposalById(@PathVariable final String proposalId) {
		return createProposedConceptPackageDto(Context.getService(ProposedConceptService.class).getProposedConceptPackageById(Integer.valueOf(proposalId)));
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

	@RequestMapping(value = "/cpm/proposals/{proposalId}", method = RequestMethod.PUT)
	public @ResponseBody ProposedConceptPackageDto updateProposal(@PathVariable final String proposalId, @RequestBody final ProposedConceptPackageDto updatedPackage) {

		// TODO: some server side validation here... not null fields, valid email?

		final ProposedConceptPackage conceptPackage = Context.getService(ProposedConceptService.class).getProposedConceptPackageById(Integer.valueOf(proposalId));

		conceptPackage.setName(updatedPackage.getName());
		conceptPackage.setEmail(updatedPackage.getEmail());
		conceptPackage.setDescription(updatedPackage.getDescription());

		// TODO: remap concepts
//		if (updatedPackage.getConcepts() != null) {
//			for (final ProposedConceptDto newProposedConcept : updatedPackage.getConcepts()) {
//
//				final ProposedConcept proposedConcept = new ProposedConcept();
//				final ConceptService conceptService = Context.getConceptService();
//				final Concept concept = conceptService.getConcept(newProposedConcept.getConceptId());
//				proposedConcept.setConcept(concept);
//
//				final Set<ShareableComment> comments = new HashSet<ShareableComment>();
//				final ShareableComment comment = new ShareableComment();
//				comment.setComment(newProposedConcept.getComments());
//				proposedConcept.setComments(comments);
//			}
//		}

		Context.getService(ProposedConceptService.class).saveProposedConceptPackage(conceptPackage);
		return updatedPackage;
	}

	@RequestMapping(value = "/cpm/proposals/{proposalId}", method = RequestMethod.DELETE)
	public void deleteProposal(@PathVariable final String proposalId) {
		final ProposedConceptService service = Context.getService(ProposedConceptService.class);
		service.deleteProposedConceptPackage(service.getProposedConceptPackageById(Integer.valueOf(proposalId)));
	}

	private ProposedConceptPackageDto createProposedConceptPackageDto(final ProposedConceptPackage conceptProposalPackage) {

		final ProposedConceptPackageDto conceptProposalPackageDto = new ProposedConceptPackageDto();
		conceptProposalPackageDto.setId(conceptProposalPackage.getId());
		conceptProposalPackageDto.setName(conceptProposalPackage.getName());
		conceptProposalPackageDto.setEmail(conceptProposalPackage.getEmail());
		conceptProposalPackageDto.setDescription(conceptProposalPackage.getDescription());
		conceptProposalPackageDto.setStatus(conceptProposalPackage.getStatus());

		final Set<ProposedConcept> proposedConcepts = conceptProposalPackage.getProposedConcepts();
		final List<ProposedConceptDto> list = new ArrayList<ProposedConceptDto>();

		for (final ProposedConcept conceptProposal : proposedConcepts) {

			final ProposedConceptDto conceptProposalDto = new ProposedConceptDto();
//			conceptProposalDto.setConceptId(conceptProposal.get??)
			conceptProposalDto.setName(conceptProposal.getName());
//			conceptProposalDto.setComments(conceptProposal.getComments()); type mismatch
			conceptProposalDto.setStatus(conceptProposal.getStatus());

			list.add(conceptProposalDto);
		}

		conceptProposalPackageDto.setConcepts(list);
		return conceptProposalPackageDto;
	}

}
