package org.openmrs.module.conceptpropose.web.controller;

import org.openmrs.Concept;
import org.openmrs.ConceptSearchResult;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.PackageStatus;
import org.openmrs.module.conceptpropose.ProposedConcept;
import org.openmrs.module.conceptpropose.ProposedConceptPackage;
import org.openmrs.module.conceptpropose.api.ProposedConceptService;
import org.openmrs.module.conceptpropose.web.common.CpmConstants;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptDto;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptPackageDto;
import org.openmrs.module.conceptpropose.web.dto.concept.ConceptDto;
import org.openmrs.module.conceptpropose.web.dto.concept.SearchConceptResultDto;
import org.openmrs.module.conceptpropose.web.dto.factory.DescriptionDtoFactory;
import org.openmrs.module.conceptpropose.web.dto.factory.NameDtoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Controller
public class ProposalController {


	private final SubmitProposal submitProposal;

	private final UpdateProposedConceptPackage updateProposedConceptPackage;

    private final DescriptionDtoFactory descriptionDtoFactory;

    private final NameDtoFactory nameDtoFactory;


    @Autowired
    public ProposalController (final SubmitProposal submitProposal,
                               final UpdateProposedConceptPackage updateProposedConceptPackage,
                               final DescriptionDtoFactory descriptionDtoFactory,
                               final NameDtoFactory nameDtoFactory) {
        this.submitProposal = submitProposal;
        this.updateProposedConceptPackage = updateProposedConceptPackage;
        this.descriptionDtoFactory = descriptionDtoFactory;
        this.nameDtoFactory = nameDtoFactory;
    }

	//
	// Pages
	//

	@RequestMapping(value = "module/cpm/proposals.list", method = RequestMethod.GET)
	public String listProposals() {
		return CpmConstants.LIST_PROPOSAL_URL;
	}

	//
	// Service endpoints
	//


    @RequestMapping(value = "/cpm/concepts", method = RequestMethod.GET)
    public @ResponseBody SearchConceptResultDto findConcepts(@RequestParam final String query,
                                                             @RequestParam final String requestNum) {
        final ArrayList<ConceptDto> results = new ArrayList<ConceptDto>();
        final ConceptService conceptService = Context.getConceptService();

        if (query.equals("")) {
            final List<Concept> allConcepts = conceptService.getAllConcepts("name", true, false);
            for (final Concept concept : allConcepts) {
                ConceptDto conceptDto = createConceptDto(concept);
                    results.add(conceptDto);
            }
        } else {
            final List<ConceptSearchResult> concepts = conceptService.getConcepts(query, Context.getLocale(), false);
            for (final ConceptSearchResult conceptSearchResult : concepts) {
                ConceptDto conceptDto = createConceptDto(conceptSearchResult.getConcept());
                results.add(conceptDto);

                }

        }
        SearchConceptResultDto resultDto = new SearchConceptResultDto();
        resultDto.setConcepts(results);
        resultDto.setRequestNum(requestNum);
        return resultDto;
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

		updateProposedConceptPackage.updateProposedConcepts(conceptPackage, newPackage);

		Context.getService(ProposedConceptService.class).saveProposedConceptPackage(conceptPackage);

		// Return the DTO with the new ID for the benefit of the client
		newPackage.setId(conceptPackage.getId());

		if (newPackage.getStatus() == PackageStatus.TBS) {
			submitProposal.submitProposedConcept(conceptPackage);
		}

		return newPackage;
	}

	@RequestMapping(value = "/cpm/proposals/{proposalId}", method = RequestMethod.PUT)
	public @ResponseBody ProposedConceptPackageDto updateProposal(@PathVariable final String proposalId,
                                                                  @RequestBody final ProposedConceptPackageDto updatedPackage) {

		final ProposedConceptService proposedConceptService = Context.getService(ProposedConceptService.class);
		final ProposedConceptPackage conceptPackage = proposedConceptService.getProposedConceptPackageById(Integer.valueOf(proposalId));

		// TODO: some server side validation here... not null fields, valid email?

		conceptPackage.setName(updatedPackage.getName());
		conceptPackage.setEmail(updatedPackage.getEmail());
		conceptPackage.setDescription(updatedPackage.getDescription());
		updateProposedConceptPackage.updateProposedConcepts(conceptPackage, updatedPackage);
        proposedConceptService.saveProposedConceptPackage(conceptPackage);

		if (conceptPackage.getStatus() == PackageStatus.DRAFT && updatedPackage.getStatus() == PackageStatus.TBS) {
			submitProposal.submitProposedConcept(conceptPackage);
		}

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

			final Concept concept = conceptProposal.getConcept();
			final ProposedConceptDto conceptProposalDto = new ProposedConceptDto();
			conceptProposalDto.setId(concept.getConceptId());
			conceptProposalDto.setNames(nameDtoFactory.create(concept));
			conceptProposalDto.setPreferredName(concept.getName().getName());
			conceptProposalDto.setDescriptions(descriptionDtoFactory.create(concept));
            if(concept.getDescription() != null) {
                conceptProposalDto.setCurrLocaleDescription(concept.getDescription().getDescription());
            }
			conceptProposalDto.setDatatype(concept.getDatatype().getName());
			conceptProposalDto.setStatus(conceptProposal.getStatus());
            conceptProposalDto.setComment(conceptProposal.getComment());
			list.add(conceptProposalDto);
		}

		conceptProposalPackageDto.setConcepts(list);
		return conceptProposalPackageDto;
	}

	public SubmitProposal getSubmitProposal() {
		return submitProposal;
	}



    private ConceptDto createConceptDto(final Concept concept) {

        final ConceptDto dto = new ConceptDto();
        dto.setId(concept.getConceptId());
        dto.setNames(nameDtoFactory.create(concept));
        dto.setPreferredName(concept.getName().getName());
        dto.setDatatype(concept.getDatatype().getName());
        dto.setDescriptions(descriptionDtoFactory.create(concept));
        if(concept.getDescription()!=null)  {
            dto.setCurrLocaleDescription(concept.getDescription().getDescription());
        }

        return dto;
    }



}
