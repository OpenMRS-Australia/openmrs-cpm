package org.openmrs.module.cpm.web.dto.factory;

import org.openmrs.Concept;
import org.openmrs.module.cpm.ProposedConcept;
import org.openmrs.module.cpm.web.dto.ProposedConceptDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProposedConceptDtoFactory {

    private final DescriptionDtoFactory descriptionDtoFactory;

    private final NameDtoFactory nameDtoFactory;

    @Autowired
    public ProposedConceptDtoFactory(final DescriptionDtoFactory descriptionDtoFactory, final NameDtoFactory nameDtoFactory) {
        this.descriptionDtoFactory = descriptionDtoFactory;
        this.nameDtoFactory = nameDtoFactory;
    }


    public ProposedConceptDto create(final ProposedConcept proposedConcept, final Concept concept){
        ProposedConceptDto conceptDto = new ProposedConceptDto();

        // concept details
        conceptDto.setNames(nameDtoFactory.create(concept));
        conceptDto.setDescriptions(descriptionDtoFactory.create(concept));
        conceptDto.setUuid(concept.getUuid());
        conceptDto.setComment(proposedConcept.getComment()); // proposer's comment

        return conceptDto;
    }
}
