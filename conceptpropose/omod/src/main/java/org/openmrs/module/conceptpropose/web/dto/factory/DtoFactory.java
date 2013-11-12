package org.openmrs.module.conceptpropose.web.dto.factory;

import org.openmrs.ConceptDatatype;
import org.openmrs.ConceptNumeric;
import org.openmrs.api.ConceptNameType;
import org.openmrs.module.conceptpropose.ProposedConceptResponse;
import org.openmrs.module.conceptpropose.ProposedConceptResponseDescription;
import org.openmrs.module.conceptpropose.ProposedConceptResponseName;
import org.openmrs.module.conceptpropose.ProposedConceptResponseNumeric;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptResponseDto;
import org.openmrs.module.conceptpropose.web.dto.concept.DescriptionDto;
import org.openmrs.module.conceptpropose.web.dto.concept.NameDto;
import org.openmrs.module.conceptpropose.web.dto.concept.NumericDto;

import java.util.ArrayList;
import java.util.List;

public class DtoFactory {

	public static ProposedConceptResponseDto createProposedConceptResponseDto(final ProposedConceptResponse conceptProposal) {
		final ProposedConceptResponseDto conceptProposalDto = new ProposedConceptResponseDto();

		final List<ProposedConceptResponseName> names = conceptProposal.getNames();
		if (names != null) {
			for (ProposedConceptResponseName name : names) {
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

		final ConceptDatatype conceptDatatype = conceptProposal.getDatatype();
		if (conceptDatatype != null) {
			conceptProposalDto.setDatatype(conceptDatatype.getName());

			if (conceptDatatype.getUuid().equals(ConceptDatatype.NUMERIC_UUID)) {
				ProposedConceptResponseNumeric conceptNumeric = conceptProposal.getNumericDetails();
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

	private static ArrayList<NameDto> getNameDtos(ProposedConceptResponse concept) {
		ArrayList<NameDto> nameDtos = new ArrayList<NameDto>();
		final List<ProposedConceptResponseName> names = concept.getNames();
		if (names != null) {
			for (ProposedConceptResponseName name: names) {
				NameDto nameDto = new NameDto();
				nameDto.setName(name.getName());
				nameDto.setType(name.getType());
				nameDto.setLocale(name.getLocale().toString());
				nameDtos.add(nameDto);
			}
		}
		return nameDtos;
	}

	private static ArrayList<DescriptionDto> getDescriptionDtos(ProposedConceptResponse concept) {
		ArrayList<DescriptionDto> descriptionDtos = new ArrayList<DescriptionDto>();
		final List<ProposedConceptResponseDescription> descriptions = concept.getDescriptions();
		if (descriptions != null) {
			for (ProposedConceptResponseDescription description: descriptions) {
				DescriptionDto descriptionDto = new DescriptionDto();
				descriptionDto.setDescription(description.getDescription());
				descriptionDto.setLocale(description.getLocale().toString());
				descriptionDtos.add(descriptionDto);
			}
		}
		return descriptionDtos;
	}
}