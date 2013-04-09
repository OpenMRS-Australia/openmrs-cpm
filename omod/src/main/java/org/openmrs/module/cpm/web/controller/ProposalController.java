package org.openmrs.module.cpm.web.controller;

import com.google.common.collect.Lists;
import org.apache.commons.codec.binary.Base64;
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
import org.openmrs.module.cpm.web.dto.SubmissionDto;
import org.openmrs.module.cpm.web.dto.SubmissionResponseDto;
import org.openmrs.module.cpm.web.dto.concept.DescriptionDto;
import org.openmrs.module.cpm.web.dto.concept.NameDto;
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

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

@Controller
public class ProposalController {

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

	private ArrayList<NameDto> getNameDtos(Concept concept) {
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

	private ArrayList<DescriptionDto> getDescriptionDtos(Concept concept) {
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

		updateProposedConcepts(conceptPackage, newPackage);

		Context.getService(ProposedConceptService.class).saveProposedConceptPackage(conceptPackage);

		// Return the DTO with the new ID for the benefit of the client
		newPackage.setId(conceptPackage.getId());
		return newPackage;
	}

	@RequestMapping(value = "/cpm/proposals/{proposalId}", method = RequestMethod.PUT)
	public @ResponseBody ProposedConceptPackageDto updateProposal(@PathVariable final String proposalId,
                                                                  @RequestBody final ProposedConceptPackageDto updatedPackage,
                                                                  final HttpServletRequest request) {

		final ProposedConceptService proposedConceptService = Context.getService(ProposedConceptService.class);
		final ProposedConceptPackage conceptPackage = proposedConceptService.getProposedConceptPackageById(Integer.valueOf(proposalId));

		if (conceptPackage.getStatus() == PackageStatus.DRAFT && updatedPackage.getStatus() == PackageStatus.TBS) {
			return submitProposedConcept(conceptPackage, request);
		}


		// TODO: some server side validation here... not null fields, valid email?

		conceptPackage.setName(updatedPackage.getName());
		conceptPackage.setEmail(updatedPackage.getEmail());
		conceptPackage.setDescription(updatedPackage.getDescription());

        updateProposedConcepts(conceptPackage, updatedPackage);

        proposedConceptService.saveProposedConceptPackage(conceptPackage);
		return updatedPackage;
	}

	private ProposedConceptPackageDto submitProposedConcept(final ProposedConceptPackage conceptPackage,
                                                            final HttpServletRequest httpServletRequest) {

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

			// concept details
			final Concept concept = proposedConcept.getConcept();
			conceptDto.setNames(getNameDtos(concept));
			conceptDto.setDescriptions(getDescriptionDtos(concept));

            ConceptDatatype conceptDatatype = concept.getDatatype();
            if(conceptDatatype !=null){
                conceptDto.setDatatype(conceptDatatype.getName());
            }
            conceptDto.setUuid(concept.getUuid());
			
            // proposer's comment
			conceptDto.setComment(proposedConcept.getComment());
			
			list.add(conceptDto);
		}
		submission.setConcepts(list);

		final HttpHeaders headers = createHeaders(service.getGlobalProperty("cpm.username"), service.getGlobalProperty("cpm.password"));
		final HttpEntity requestEntity = new HttpEntity<SubmissionDto>(submission, headers);

        final String contextPath = httpServletRequest.getContextPath();
		final String url = service.getGlobalProperty("cpm.url") +  contextPath + "/ws/cpm/dictionarymanager/proposals";

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
					proposedConcept.setComment(newProposedConcept.getComment());
                    conceptPackage.addProposedConcept(proposedConcept);
                }
            }
    }

}
