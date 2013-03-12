package org.openmrs.module.cpm.web.controller;

import com.google.common.collect.Lists;
import org.apache.commons.codec.binary.Base64;
import org.openmrs.Concept;
import org.openmrs.ConceptName;
import org.openmrs.ConceptSearchResult;
import org.openmrs.GlobalProperty;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.PackageStatus;
import org.openmrs.module.cpm.ProposedConcept;
import org.openmrs.module.cpm.ProposedConceptPackage;
import org.openmrs.module.cpm.ProposedConceptResponse;
import org.openmrs.module.cpm.ProposedConceptResponsePackage;
import org.openmrs.module.cpm.web.dto.Settings;
import org.openmrs.module.cpm.api.ProposedConceptService;
import org.openmrs.module.cpm.web.dto.ConceptDto;
import org.openmrs.module.cpm.web.dto.ProposedConceptDto;
import org.openmrs.module.cpm.web.dto.ProposedConceptPackageDto;
import org.openmrs.module.cpm.web.dto.ProposedConceptResponsePackageDto;
import org.openmrs.module.cpm.web.dto.SubmissionDto;
import org.openmrs.module.cpm.web.dto.SubmissionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestOperations;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

@Controller
public class CpmController {

	@Autowired
	private RestOperations submissionRestTemplate;

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
		dto.setName(concept.getName().getName());

		dto.setId(concept.getConceptId());

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

		updateProposedConcepts(conceptPackage, newPackage);

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

		if (conceptPackage.getStatus() == PackageStatus.DRAFT && updatedPackage.getStatus() == PackageStatus.TBS) {
			return submitProposedConcept(conceptPackage);
		}


		// TODO: some server side validation here... not null fields, valid email?

		conceptPackage.setName(updatedPackage.getName());
		conceptPackage.setEmail(updatedPackage.getEmail());
		conceptPackage.setDescription(updatedPackage.getDescription());

        updateProposedConcepts(conceptPackage, updatedPackage);

        proposedConceptService.saveProposedConceptPackage(conceptPackage);
		return updatedPackage;
	}

	private ProposedConceptPackageDto submitProposedConcept(final ProposedConceptPackage conceptPackage) {

		//
		// Could not figure out how to get Spring to send a basic authentication request using the "proper" object approach
		// see: https://github.com/johnsyweb/openmrs-cpm/wiki/Gotchas
		//

		AdministrationService service = Context.getAdministrationService();

		SubmissionDto submission = new SubmissionDto();
		submission.setName(conceptPackage.getName());
		submission.setEmail(conceptPackage.getEmail());
		submission.setDescription(conceptPackage.getDescription());

		final ArrayList<ProposedConceptDto> list = new ArrayList<ProposedConceptDto>();
		for (ProposedConcept proposedConcept: conceptPackage.getProposedConcepts()) {
			final ProposedConceptDto conceptDto = new ProposedConceptDto();

			// TODO: need to figure out how comments are going to be managed
//			conceptDto.setComments(proposedConcept.getComments());

			// concept details
			final Concept concept = proposedConcept.getConcept();
			conceptDto.setName(concept.getName().getName());
			conceptDto.setDescription(concept.getDescription().getDescription());
			conceptDto.setDatatype(concept.getDatatype().getName());
			conceptDto.setUuid(concept.getUuid());

			list.add(conceptDto);
		}
		submission.setConcepts(list);

		final HttpHeaders headers = createHeaders(service.getGlobalProperty("cpm.username"), service.getGlobalProperty("cpm.password"));
		final HttpEntity requestEntity = new HttpEntity<SubmissionDto>(submission, headers);

		final String url = service.getGlobalProperty("cpm.url") + "/openmrs/ws/cpm/dictionarymanager/proposals";
		submissionRestTemplate.exchange(url, HttpMethod.POST, requestEntity, SubmissionResponseDto.class);

//		final SubmissionResponseDto result = submissionRestTemplate.postForObject("http://localhost:8080/openmrs/ws/cpm/dictionarymanager/proposals", submission, SubmissionResponseDto.class);

		conceptPackage.setStatus(PackageStatus.SUBMITTED);
		Context.getService(ProposedConceptService.class).saveProposedConceptPackage(conceptPackage);

		return createProposedConceptPackageDto(conceptPackage);
	}

	private HttpHeaders createHeaders( final String username, final String password ){
		final HttpHeaders httpHeaders = new HttpHeaders();
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.encodeBase64(
		auth.getBytes(Charset.forName("US-ASCII")));
		String authHeader = "Basic " + new String( encodedAuth );
		httpHeaders.set("Authorization", authHeader);
		return httpHeaders;
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
			conceptProposalDto.setId(conceptProposal.getConcept().getConceptId());
			conceptProposalDto.setName(conceptProposal.getConcept().getName().getName());
			conceptProposalDto.setDatatype(conceptProposal.getConcept().getDatatype().getName());
			conceptProposalDto.setStatus(conceptProposal.getStatus());

			// TODO: comments

			list.add(conceptProposalDto);
		}

		conceptProposalPackageDto.setConcepts(list);
		return conceptProposalPackageDto;
	}

	@RequestMapping(value = "/cpm/proposalResponses", method = RequestMethod.GET)
	public @ResponseBody ArrayList<ProposedConceptResponsePackageDto> getProposalResponses() {

		final List<ProposedConceptResponsePackage> allConceptProposalResponsePackages = Context.getService(ProposedConceptService.class).getAllProposedConceptResponsePackages();
		final ArrayList<ProposedConceptResponsePackageDto> response = new ArrayList<ProposedConceptResponsePackageDto>();

		for (final ProposedConceptResponsePackage conceptProposalResponsePackage : allConceptProposalResponsePackages) {

			final ProposedConceptResponsePackageDto conceptProposalResponsePackageDto = createProposedConceptResponsePackageDto(conceptProposalResponsePackage);
			response.add(conceptProposalResponsePackageDto);
		}

		return response;
	}

    private void updateProposedConcepts(ProposedConceptPackage conceptPackage,
                                        ProposedConceptPackageDto packageDto){
        checkNotNull(conceptPackage,"ProposedConceptPackage should not be null");
        checkNotNull(packageDto,"ProposedConceptPackageDto should not be null");
        removeDeletedProposedConcepts(conceptPackage, packageDto);
        addOrModifyProposedConcepts(conceptPackage,packageDto);
    }

    private void removeDeletedProposedConcepts(ProposedConceptPackage conceptPackage,
                                               ProposedConceptPackageDto packageDto){
        //remove concept(s)
        if(conceptPackage.getProposedConcepts().size() > 0){
            List<Integer> newConceptIds = Lists.newArrayList();
            for (ProposedConceptDto p : packageDto.getConcepts()){
                newConceptIds.add(p.getId());
            }
            List<Integer> existingConceptIds = Lists.newArrayList();
            for (ProposedConcept p : conceptPackage.getProposedConcepts()){
                existingConceptIds.add(p.getId());
            }
            //Find concepts that have been deleted
            for (Integer existingId : existingConceptIds){
                if(!newConceptIds.contains(existingId)){
                    //delete the concept
                    ProposedConcept removedConcept = conceptPackage.getProposedConcept(existingId);
                    conceptPackage.removeProposedConcept(removedConcept);
                }
            }
        }
    }

    private void addOrModifyProposedConcepts( ProposedConceptPackage conceptPackage,
                                           final ProposedConceptPackageDto packageDto){
            //Add and modify concepts
            for (final ProposedConceptDto newProposedConcept : packageDto.getConcepts()) {
                ProposedConcept proposedConcept = new ProposedConcept();
                final ConceptService conceptService = Context.getConceptService();

                if(conceptPackage.getProposedConcept(newProposedConcept.getId()) != null){
                    //Modify already persisted concept
                    proposedConcept = conceptPackage.getProposedConcept(newProposedConcept.getId());
					// todo save comments
                } else {
                    //New concept added to the ProposedConceptPackage
                    final Concept concept = conceptService.getConcept(newProposedConcept.getId());
                    checkNotNull(concept,"Concept should not be null") ;
                    proposedConcept.setConcept(concept);
                    conceptPackage.addProposedConcept(proposedConcept);
                }
            }
    }

	private ProposedConceptResponsePackageDto createProposedConceptResponsePackageDto(final ProposedConceptResponsePackage conceptProposalResponsePackage) {

		final ProposedConceptResponsePackageDto conceptProposalPackageDto = new ProposedConceptResponsePackageDto();
		conceptProposalPackageDto.setId(conceptProposalResponsePackage.getId());
		conceptProposalPackageDto.setName(conceptProposalResponsePackage.getName());
		conceptProposalPackageDto.setEmail(conceptProposalResponsePackage.getEmail());
		conceptProposalPackageDto.setDescription(conceptProposalResponsePackage.getDescription());

		final Set<ProposedConceptResponse> proposedConcepts = conceptProposalResponsePackage.getProposedConcepts();
		final List<ProposedConceptDto> list = new ArrayList<ProposedConceptDto>();

		for (final ProposedConceptResponse conceptProposal : proposedConcepts) {

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
