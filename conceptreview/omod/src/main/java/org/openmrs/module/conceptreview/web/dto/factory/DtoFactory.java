package org.openmrs.module.conceptreview.web.dto.factory;

import org.openmrs.ConceptDatatype;
import org.openmrs.api.ConceptNameType;
import org.openmrs.module.conceptpropose.web.dto.CommentDto;
import org.openmrs.module.conceptreview.ProposedConceptReview;
import org.openmrs.module.conceptreview.ProposedConceptReviewDescription;
import org.openmrs.module.conceptreview.ProposedConceptReviewName;
import org.openmrs.module.conceptreview.ProposedConceptReviewNumeric;
import org.openmrs.module.conceptreview.ProposedConceptReviewComment;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptReviewDto;
import org.openmrs.module.conceptpropose.web.dto.concept.DescriptionDto;
import org.openmrs.module.conceptpropose.web.dto.concept.NameDto;
import org.openmrs.module.conceptpropose.web.dto.concept.NumericDto;

import java.util.ArrayList;
import java.util.List;

public class DtoFactory {

	public static CommentDto createCommentDto(final ProposedConceptReviewComment reviewComment) {
		CommentDto commentDto = new CommentDto();
		commentDto.setEmail(reviewComment.getEmail());
		commentDto.setName(reviewComment.getName());
		commentDto.setComment(reviewComment.getComment());
		commentDto.setDateCreated(reviewComment.getDateCreated());
		return commentDto;
	}
	public static List<CommentDto> createCommentDtos(final List<ProposedConceptReviewComment> reviewComments) {
		List<CommentDto> commentDtos = new ArrayList<CommentDto>();
		for(ProposedConceptReviewComment comment : reviewComments)
		{
			commentDtos.add(createCommentDto(comment));
		}
		return commentDtos;
	}

	public static ProposedConceptReviewDto createProposedConceptReviewDto(final ProposedConceptReview conceptProposal) {
		final ProposedConceptReviewDto conceptProposalDto = new ProposedConceptReviewDto();

		final List<ProposedConceptReviewName> names = conceptProposal.getNames();
		if (names != null) {
			for (ProposedConceptReviewName name : names) {
				if (name.getType() == ConceptNameType.FULLY_SPECIFIED) {
					conceptProposalDto.setPreferredName(name.getName());
					break;
				}
			}
		}

		conceptProposalDto.setId(conceptProposal.getId());
		conceptProposalDto.setNames(getNameDtos(conceptProposal));
		conceptProposalDto.setDescriptions(getDescriptionDtos(conceptProposal));
		conceptProposalDto.setStatus(conceptProposal.getStatus());
		conceptProposalDto.setComment(conceptProposal.getComment());
		conceptProposalDto.setReviewComment(conceptProposal.getReviewComment());
		conceptProposalDto.setComments(createCommentDtos(conceptProposal.getComments()));

		final ConceptDatatype conceptDatatype = conceptProposal.getDatatype();
		if (conceptDatatype != null) {
			conceptProposalDto.setDatatype(conceptDatatype.getName());

			if (conceptDatatype.getUuid().equals(ConceptDatatype.NUMERIC_UUID)) {
				ProposedConceptReviewNumeric conceptNumeric = conceptProposal.getNumericDetails();
				NumericDto numericDto = new NumericDto();
				numericDto.setUnits(conceptNumeric.getUnits());
				numericDto.setPrecise(conceptNumeric.getPrecise());
				numericDto.setHiNormal(conceptNumeric.getHiNormal());
				numericDto.setHiCritical(conceptNumeric.getHiCritical());
				numericDto.setHiAbsolute(conceptNumeric.getHiAbsolute());
				numericDto.setLowNormal(conceptNumeric.getLowNormal());
				numericDto.setLowCritical(conceptNumeric.getLowCritical());
				numericDto.setLowAbsolute(conceptNumeric.getLowAbsolute());
				conceptProposalDto.setNumericDetails(numericDto);
			}
		}

		if (conceptProposal.getConceptClass() != null) {
			conceptProposalDto.setConceptClass(conceptProposal.getConceptClass().getName());
		}

		if (conceptProposal.getConcept() != null) {
			conceptProposalDto.setConceptId(conceptProposal.getConcept().getId());
		}

		return conceptProposalDto;
	}

	private static ArrayList<NameDto> getNameDtos(ProposedConceptReview concept) {
		ArrayList<NameDto> nameDtos = new ArrayList<NameDto>();
		final List<ProposedConceptReviewName> names = concept.getNames();
		if (names != null) {
			for (ProposedConceptReviewName name: names) {
				NameDto nameDto = new NameDto();
				nameDto.setName(name.getName());
				nameDto.setType(name.getType());
				nameDto.setLocale(name.getLocale().toString());
				nameDtos.add(nameDto);
			}
		}
		return nameDtos;
	}

	private static ArrayList<DescriptionDto> getDescriptionDtos(ProposedConceptReview concept) {
		ArrayList<DescriptionDto> descriptionDtos = new ArrayList<DescriptionDto>();
		final List<ProposedConceptReviewDescription> descriptions = concept.getDescriptions();
		if (descriptions != null) {
			for (ProposedConceptReviewDescription description: descriptions) {
				DescriptionDto descriptionDto = new DescriptionDto();
				descriptionDto.setDescription(description.getDescription());
				descriptionDto.setLocale(description.getLocale().toString());
				descriptionDtos.add(descriptionDto);
			}
		}
		return descriptionDtos;
	}
}
