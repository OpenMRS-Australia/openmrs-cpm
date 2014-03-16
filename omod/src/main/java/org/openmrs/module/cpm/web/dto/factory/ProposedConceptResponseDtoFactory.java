package org.openmrs.module.cpm.web.dto.factory;

import org.openmrs.ConceptDatatype;
import org.openmrs.api.ConceptNameType;
import org.openmrs.module.cpm.ProposedConceptResponse;
import org.openmrs.module.cpm.ProposedConceptResponseName;
import org.openmrs.module.cpm.ProposedConceptResponseNumeric;
import org.openmrs.module.cpm.web.dto.ProposedConceptResponseDto;
import org.openmrs.module.cpm.web.dto.concept.NumericDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProposedConceptResponseDtoFactory {

    private final DescriptionDtoFactory descriptionDtoFactory;

    private final NameDtoFactory nameDtoFactory;

    @Autowired
    public ProposedConceptResponseDtoFactory(final DescriptionDtoFactory descriptionDtoFactory,
                                             final NameDtoFactory nameDtoFactory) {
        this.descriptionDtoFactory = descriptionDtoFactory;
        this.nameDtoFactory = nameDtoFactory;
    }

	public ProposedConceptResponseDto createProposedConceptResponseDto(final ProposedConceptResponse conceptProposal) {
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

		populateBaseResponseDto(conceptProposal, conceptProposalDto);
        populateConceptDataType(conceptProposal, conceptProposalDto);

		if (conceptProposal.getConceptClass() != null) {
			conceptProposalDto.setConceptClass(conceptProposal.getConceptClass().getName());
		}

		if (conceptProposal.getConcept() != null) {
			conceptProposalDto.setConceptId(conceptProposal.getConcept().getId());
		}

		return conceptProposalDto;
	}

    private void populateBaseResponseDto(ProposedConceptResponse conceptProposal,
                                         ProposedConceptResponseDto conceptProposalDto){

        conceptProposalDto.setId(conceptProposal.getId());
        conceptProposalDto.setNames(nameDtoFactory.create(conceptProposal));
        conceptProposalDto.setDescriptions(descriptionDtoFactory.create(conceptProposal));
        conceptProposalDto.setStatus(conceptProposal.getStatus());
        conceptProposalDto.setComment(conceptProposal.getComment());
        conceptProposalDto.setReviewComment(conceptProposal.getReviewComment());

    }

    private void populateConceptDataType(ProposedConceptResponse conceptProposal,
                                         ProposedConceptResponseDto conceptProposalDto){
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
    }



}