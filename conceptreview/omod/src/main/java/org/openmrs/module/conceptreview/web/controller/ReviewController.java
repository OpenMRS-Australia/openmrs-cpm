package org.openmrs.module.conceptreview.web.controller;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.directwebremoting.util.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.openmrs.Concept;
import org.openmrs.ConceptSearchResult;
import org.openmrs.PersonName;
import org.openmrs.User;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptReviewDto;
import org.openmrs.module.conceptpropose.web.dto.UserDto;
import org.openmrs.module.conceptpropose.web.dto.concept.ConceptDto;
import org.openmrs.module.conceptpropose.web.dto.concept.SearchConceptResultDto;

import org.openmrs.module.conceptpropose.web.dto.factory.DescriptionDtoFactory;
import org.openmrs.module.conceptpropose.web.dto.factory.NameDtoFactory;
import org.openmrs.module.conceptreview.ProposedConceptReviewComment;
import org.openmrs.util.OpenmrsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.openmrs.module.conceptreview.ProposedConceptReview;
import org.openmrs.module.conceptreview.ProposedConceptReviewPackage;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptReviewPackageDto;
import org.openmrs.module.conceptreview.api.ProposedConceptReviewService;
import org.openmrs.module.conceptreview.web.dto.factory.DtoFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.Set;

@Controller
public class ReviewController {
    private final Logger log = Logger.getLogger(ReviewController.class);

    private final NameDtoFactory nameDtoFactory;

    private final DescriptionDtoFactory descriptionDtoFactory;
    @Autowired
    public ReviewController (
            final DescriptionDtoFactory descriptionDtoFactory,
            final NameDtoFactory nameDtoFactory) {
        this.descriptionDtoFactory = descriptionDtoFactory;
        this.nameDtoFactory = nameDtoFactory;
    }
	//
	// Pages
	//

	@RequestMapping(value = "module/conceptreview/proposalReview.list", method = RequestMethod.GET)
	public String listProposalReview() {
		return "/module/conceptreview/proposalReview";
	}

	//
	// REST endpoints
	//

	@RequestMapping(value = "/conceptreview/proposalReviews", method = RequestMethod.GET)
	public @ResponseBody ArrayList<ProposedConceptReviewPackageDto> getProposalReviews() {

		final List<ProposedConceptReviewPackage> openConceptProposalReviewPackages = Context.getService(ProposedConceptReviewService.class).getOpenProposedConceptReviewPackages();
		final ArrayList<ProposedConceptReviewPackageDto> response = new ArrayList<ProposedConceptReviewPackageDto>();

		for (final ProposedConceptReviewPackage conceptProposalReviewPackage : openConceptProposalReviewPackages) {

			final ProposedConceptReviewPackageDto conceptProposalReviewPackageDto = createProposedConceptReviewPackageDto(conceptProposalReviewPackage);
			response.add(conceptProposalReviewPackageDto);
		}

		return response;
	}

    @RequestMapping(value = "/conceptreview/completedProposalReviews", method = RequestMethod.GET)
    public @ResponseBody ArrayList<ProposedConceptReviewPackageDto> getCompletedProposalReviews() {

        final List<ProposedConceptReviewPackage> completedConceptProposalReviewPackages = Context.getService(ProposedConceptReviewService.class).getCompletedProposedConceptReviewPackages();
        final ArrayList<ProposedConceptReviewPackageDto> response = new ArrayList<ProposedConceptReviewPackageDto>();

        for (final ProposedConceptReviewPackage conceptProposalReviewPackage : completedConceptProposalReviewPackages) {

            final ProposedConceptReviewPackageDto conceptProposalReviewPackageDto = createProposedConceptReviewPackageDto(conceptProposalReviewPackage);
            response.add(conceptProposalReviewPackageDto);
        }

        return response;
    }

	@RequestMapping(value = "/conceptreview/proposalReviews/{proposalId}", method = RequestMethod.GET)
	public @ResponseBody ProposedConceptReviewPackageDto getProposalReview(@PathVariable int proposalId) {
		return createProposedConceptReviewPackageDto(Context.
				getService(ProposedConceptReviewService.class).
				getProposedConceptReviewPackageById(proposalId));
	}

	@RequestMapping(value = "/conceptreview/proposalReviews/{proposalId}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteProposalReview(@PathVariable int proposalId) {
		Context.getService(ProposedConceptReviewService.class).deleteProposedConceptReviewPackageById(proposalId);
	}

	@RequestMapping(value = "/conceptreview/userDetails", method = RequestMethod.GET)
	public @ResponseBody UserDto getUserDetails() {
		User user = Context.getAuthenticatedUser();
		return new UserDto(getDisplayName(user), getNotificationEmail(user));
	}
	private String getDisplayName(User user) {
		PersonName name = user.getPersonName();
		ArrayList<String> components = Lists.newArrayList(name.getGivenName(), name.getMiddleName(), name.getFamilyName());
		String displayName = "";
		for (String component : components) {
			if (!Strings.isNullOrEmpty(component)) {
				displayName += String.format("%s ", component);
			}
		}
		return displayName.trim();
	}
	private String getNotificationEmail(User user) {
		Map<String, String> userProperties = user.getUserProperties();
		return userProperties.get(OpenmrsConstants.USER_PROPERTY_NOTIFICATION_ADDRESS);
	}

	@RequestMapping(value = "/conceptreview/proposalReviews/{proposalId}/concepts/{conceptId}", method = RequestMethod.GET)
	public @ResponseBody
	ProposedConceptReviewDto getConceptReview(@PathVariable int proposalId, @PathVariable int conceptId) {
		final ProposedConceptReviewService service = Context.getService(ProposedConceptReviewService.class);
		final ProposedConceptReview proposedConcept = service.getProposedConceptReviewPackageById(proposalId).getProposedConcept(conceptId);
		return DtoFactory.createProposedConceptReviewDto(proposedConcept);
	}

	@RequestMapping(value = "/conceptreview/proposalReviews/{proposalId}/concepts/{conceptId}", method = RequestMethod.PUT)
	public @ResponseBody
	ProposedConceptReviewDto updateConceptReview(@PathVariable int proposalId, @PathVariable int conceptId, @RequestBody ProposedConceptReviewDto updatedProposalReview) {
		final ProposedConceptReviewService service = Context.getService(ProposedConceptReviewService.class);
		final ProposedConceptReviewPackage aPackage = service.getProposedConceptReviewPackageById(proposalId);
		final ProposedConceptReview proposedConcept = aPackage.getProposedConcept(conceptId);
		if (proposedConcept != null) {
			proposedConcept.setReviewComment(updatedProposalReview.getReviewComment());
			if(updatedProposalReview.getNewCommentText() != null && updatedProposalReview.getNewCommentText() != "") {
				final ProposedConceptReviewComment newComment = new ProposedConceptReviewComment(updatedProposalReview.getNewCommentName(), updatedProposalReview.getNewCommentEmail(), updatedProposalReview.getNewCommentText());
				newComment.setProposedConceptReview(proposedConcept);
				proposedConcept.getComments().add(newComment);
			}
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
		dto.setStatus(responsePackage.getStatus());

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

    @RequestMapping(value = "/conceptreview/concepts", method = RequestMethod.GET)
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
