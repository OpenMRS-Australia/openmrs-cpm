package org.openmrs.module.conceptpropose.web.dto.factory;

import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.ConceptNumeric;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.ProposedConcept;
import org.openmrs.module.conceptpropose.ProposedConceptPackage;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptDto;
import org.openmrs.module.conceptpropose.web.dto.SubmissionDto;
import org.openmrs.module.conceptpropose.web.dto.concept.NumericDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SubmissionDtoFactory {

    private final ProposedConceptDtoFactory proposedConceptDtoFactory;

    @Autowired
    public SubmissionDtoFactory(final ProposedConceptDtoFactory proposedConceptDtoFactory) {
        this.proposedConceptDtoFactory = proposedConceptDtoFactory;
    }


    public SubmissionDto create(final ProposedConceptPackage conceptPackage){

        SubmissionDto submission = new SubmissionDto();
        submission.setName(conceptPackage.getName());
        submission.setEmail(conceptPackage.getEmail());
        submission.setDescription(conceptPackage.getDescription());

        final ArrayList<ProposedConceptDto> list = new ArrayList<ProposedConceptDto>();
        for (ProposedConcept proposedConcept : conceptPackage.getProposedConcepts()) {
            final Concept concept = proposedConcept.getConcept();

            final ProposedConceptDto conceptDto = proposedConceptDtoFactory.create(proposedConcept, concept);

            ConceptDatatype conceptDatatype = concept.getDatatype();
            populateNumericDto(conceptDto, concept, conceptDatatype);

            final ConceptClass conceptClass = concept.getConceptClass();
            if (conceptClass != null) {
                conceptDto.setConceptClass(conceptClass.getUuid());
            }

            list.add(conceptDto);
        }
        submission.setConcepts(list);
        return submission;
    }

    private void populateNumericDto (final ProposedConceptDto conceptDto,  final Concept concept,
                                     final ConceptDatatype conceptDatatype) {
        final String uuid = conceptDatatype.getUuid();
        conceptDto.setDatatype(uuid);

        // when datatype is numeric, add numeric metadata to payload

        if (ConceptDatatype.NUMERIC_UUID.equals(uuid)) {
            ConceptService conceptService = Context.getConceptService();
            final ConceptNumeric conceptNumeric = conceptService.getConceptNumeric(concept.getId());

            NumericDto numericDto = new NumericDto();
            numericDto.setUnits(conceptNumeric.getUnits());
            numericDto.setPrecise(conceptNumeric.getPrecise());
            numericDto.setHiNormal(conceptNumeric.getHiNormal());
            numericDto.setHiCritical(conceptNumeric.getHiCritical());
            numericDto.setHiAbsolute(conceptNumeric.getHiAbsolute());
            numericDto.setLowNormal(conceptNumeric.getLowNormal());
            numericDto.setLowCritical(conceptNumeric.getLowCritical());
            numericDto.setLowAbsolute(conceptNumeric.getLowAbsolute());

            conceptDto.setNumericDetails(numericDto);
        }
    }
}
