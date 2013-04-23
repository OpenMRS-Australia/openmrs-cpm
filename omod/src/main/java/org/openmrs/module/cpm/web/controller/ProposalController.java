package org.openmrs.module.cpm.web.controller;

import org.openmrs.*;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.PackageStatus;
import org.openmrs.module.cpm.ProposedConcept;
import org.openmrs.module.cpm.ProposedConceptPackage;
import org.openmrs.module.cpm.api.ProposedConceptService;
import org.openmrs.module.cpm.web.dto.concept.ConceptDto;
import org.openmrs.module.cpm.web.dto.ProposedConceptDto;
import org.openmrs.module.cpm.web.dto.ProposedConceptPackageDto;
import org.openmrs.module.cpm.web.dto.Settings;
import org.openmrs.module.cpm.web.dto.concept.DescriptionDto;
import org.openmrs.module.cpm.web.dto.concept.NameDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

@Controller
public class ProposalController {

	@Autowired
	private RestOperations submissionRestTemplate;

	private final SubmitProposal submitProposal = new SubmitProposal();
	private final UpdateProposedConceptPackage updateProposedConceptPackage = new UpdateProposedConceptPackage();

	//
	// Pages
	//

	@RequestMapping(value = "module/cpm/proposals.list", method = RequestMethod.GET)
	public String listProposals() {
		return "/module/cpm/proposals";
	}

	//
	// Service endpoints
	//

	@RequestMapping(value = "/cpm/settings", method = RequestMethod.GET)
	public @ResponseBody Settings getSettings() {
		AdministrationService service = Context.getAdministrationService();
		Settings settings = new Settings();
		settings.setUrl(service.getGlobalProperty("cpm.url"));
		settings.setUsername(service.getGlobalProperty("cpm.username"));
		settings.setPassword(service.getGlobalProperty("cpm.password"));
		return settings;
	}

	@RequestMapping(value = "/cpm/settings", method = RequestMethod.POST)
	public @ResponseBody Settings postNewSettings(@RequestBody Settings settings) {
		AdministrationService service = Context.getAdministrationService();
		service.saveGlobalProperty(new GlobalProperty("cpm.url", settings.getUrl()));
		service.saveGlobalProperty(new GlobalProperty("cpm.username", settings.getUsername()));
		service.saveGlobalProperty(new GlobalProperty("cpm.password", settings.getPassword()));
		return settings;
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
		dto.setId(concept.getConceptId());
		dto.setNames(getNameDtos(concept));
		dto.setPreferredName(concept.getName().getName());
		dto.setDatatype(concept.getDatatype().getName());
		dto.setDescriptions(getDescriptionDtos(concept));
        if(concept.getDescription()!=null)  {
		    dto.setCurrLocaleDescription(concept.getDescription().getDescription());
        }

		return dto;
	}

	public static ArrayList<NameDto> getNameDtos(Concept concept) {
		ArrayList<NameDto> nameDtos = new ArrayList<NameDto>();
		for (ConceptName name: concept.getNames()) {
			NameDto nameDto = new NameDto();
			nameDto.setName(name.getName());
			nameDto.setType(name.getConceptNameType());
			nameDto.setLocale(name.getLocale().toString());
			nameDtos.add(nameDto);
		}
		return nameDtos;
	}

	public static ArrayList<DescriptionDto> getDescriptionDtos(Concept concept) {
		ArrayList<DescriptionDto> descriptionDtos = new ArrayList<DescriptionDto>();
		for (ConceptDescription description: concept.getDescriptions()) {
			DescriptionDto descriptionDto = new DescriptionDto();
			descriptionDto.setDescription(description.getDescription());
			descriptionDto.setLocale(description.getLocale().toString());
			descriptionDtos.add(descriptionDto);
		}
		return descriptionDtos;
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
			submitProposal.submitProposedConcept(conceptPackage, submissionRestTemplate);
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
			conceptProposalDto.setNames(getNameDtos(concept));
			conceptProposalDto.setPreferredName(concept.getName().getName());
			conceptProposalDto.setDescriptions(getDescriptionDtos(concept));
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

	public UpdateProposedConceptPackage getUpdateProposedConceptPackage() {
		return updateProposedConceptPackage;
	}

}
