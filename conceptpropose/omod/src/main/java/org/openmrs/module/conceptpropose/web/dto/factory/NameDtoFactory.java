package org.openmrs.module.conceptpropose.web.dto.factory;

import com.google.common.collect.Lists;
import org.openmrs.Concept;
import org.openmrs.ConceptName;
import org.openmrs.module.conceptpropose.web.dto.concept.NameDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class NameDtoFactory {

    public ArrayList<NameDto> create(Concept concept) {
        ArrayList<NameDto> nameDtos = Lists.newArrayList();
        for (ConceptName name: concept.getNames()) {
            NameDto nameDto = new NameDto();
            nameDto.setName(name.getName());
            nameDto.setType(name.getConceptNameType());
            nameDto.setLocale(name.getLocale().toString());
            nameDtos.add(nameDto);
        }
        return nameDtos;
    }
}
