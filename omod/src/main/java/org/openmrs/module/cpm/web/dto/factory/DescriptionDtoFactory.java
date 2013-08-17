package org.openmrs.module.cpm.web.dto.factory;

import com.google.common.collect.Lists;
import org.openmrs.Concept;
import org.openmrs.ConceptDescription;
import org.openmrs.module.cpm.ProposedConceptResponse;
import org.openmrs.module.cpm.ProposedConceptResponseDescription;
import org.openmrs.module.cpm.web.dto.concept.DescriptionDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DescriptionDtoFactory {

    public List<DescriptionDto> create(final Concept concept) {
        List<DescriptionDto> descriptionDtos = Lists.newArrayList();
        for (ConceptDescription description: concept.getDescriptions()) {
            DescriptionDto descriptionDto = new DescriptionDto();
            descriptionDto.setDescription(description.getDescription());
            descriptionDto.setLocale(description.getLocale().toString());
            descriptionDtos.add(descriptionDto);
        }
        return descriptionDtos;
    }

    public List<DescriptionDto> create(ProposedConceptResponse concept) {
        List<DescriptionDto> descriptionDtos = new ArrayList<DescriptionDto>();
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
