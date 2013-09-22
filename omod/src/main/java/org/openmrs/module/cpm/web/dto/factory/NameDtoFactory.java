package org.openmrs.module.cpm.web.dto.factory;

import com.google.common.collect.Lists;
import org.openmrs.Concept;
import org.openmrs.ConceptName;
import org.openmrs.module.cpm.ProposedConceptResponse;
import org.openmrs.module.cpm.ProposedConceptResponseName;
import org.openmrs.module.cpm.web.dto.concept.NameDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NameDtoFactory {

    public List<NameDto> create(Concept concept) {
        List<NameDto> nameDtos = Lists.newArrayList();
        for (ConceptName name: concept.getNames()) {
            NameDto nameDto = new NameDto();
            nameDto.setName(name.getName());
            nameDto.setType(name.getConceptNameType());
            nameDto.setLocale(name.getLocale().toString());
            nameDtos.add(nameDto);
        }
        return nameDtos;
    }

    public List<NameDto> create(ProposedConceptResponse concept) {
        List<NameDto> nameDtos = new ArrayList<NameDto>();
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
}
