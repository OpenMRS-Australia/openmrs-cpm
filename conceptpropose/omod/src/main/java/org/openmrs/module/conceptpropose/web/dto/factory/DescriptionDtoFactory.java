package org.openmrs.module.conceptpropose.web.dto.factory;

import com.google.common.collect.Lists;
import org.openmrs.Concept;
import org.openmrs.ConceptDescription;
import org.openmrs.module.conceptpropose.web.dto.concept.DescriptionDto;
import org.springframework.stereotype.Component;

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
}
