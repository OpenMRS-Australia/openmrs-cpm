package org.openmrs.module.cpm.web.controller.org.openmrs.module.cpm.web.validator;


import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.openmrs.api.ConceptNameType;
import org.openmrs.module.cpm.web.dto.concept.ConceptDto;
import org.openmrs.module.cpm.web.dto.concept.NameDto;
import org.openmrs.module.cpm.web.dto.validator.ConceptDtoValidator;

import java.util.List;

import static junit.framework.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ConceptDtoValidatorTest {

    ConceptDtoValidator validator = new ConceptDtoValidator();

    @Test
    public void shouldValidate_isValid(){

        NameDto nameDto1 = new NameDto();
        nameDto1.setName("Full name");
        nameDto1.setType(ConceptNameType.FULLY_SPECIFIED);

        NameDto nameDto2 = new NameDto();
        nameDto2.setName("short1 name");
        nameDto2.setType(ConceptNameType.SHORT);

        NameDto nameDto3 = new NameDto();
        nameDto3.setName("Full name");
        nameDto3.setType(ConceptNameType.FULLY_SPECIFIED);
        nameDto3.setLocale("en");

        NameDto nameDto4 = new NameDto();
        nameDto4.setName("short1 name");
        nameDto4.setType(ConceptNameType.SHORT);
        nameDto4.setLocale("en");

        List<NameDto> nameDtoList = Lists.newArrayList(nameDto1, nameDto3, nameDto2, nameDto4);
        ConceptDto conceptDto = new ConceptDto();
        conceptDto.setId(123);
        conceptDto.setPreferredName("PREFERED_NAME");
        conceptDto.setNames(nameDtoList);

        Boolean isValid = validator.validate(conceptDto);
        assertTrue(isValid == true);
    }

    @Test
    public void shouldValidate_isInvalidDuplicateNameType(){

        NameDto nameDto1 = new NameDto();
        nameDto1.setName("Full name");
        nameDto1.setType(ConceptNameType.FULLY_SPECIFIED);

        NameDto nameDto2 = new NameDto();
        nameDto2.setName("Invalid Full name again");
        nameDto2.setType(ConceptNameType.FULLY_SPECIFIED);

        NameDto nameDto3 = new NameDto();
        nameDto3.setName("Full name");
        nameDto3.setType(ConceptNameType.FULLY_SPECIFIED);
        nameDto3.setLocale("en");

        NameDto nameDto4 = new NameDto();
        nameDto4.setName("short1 name");
        nameDto4.setType(ConceptNameType.SHORT);
        nameDto4.setLocale("en");

        List<NameDto> nameDtoList = Lists.newArrayList(nameDto1, nameDto3, nameDto2, nameDto4);
        ConceptDto conceptDto = new ConceptDto();
        conceptDto.setId(123);
        conceptDto.setPreferredName("PREFERED_NAME");
        conceptDto.setNames(nameDtoList);

        Boolean isValid = validator.validate(conceptDto);
        assertTrue(isValid == false);
    }

    @Test
    public void shouldValidate_isInvalidDuplicateNameString(){

        NameDto nameDto1 = new NameDto();
        nameDto1.setName("Full name");
        nameDto1.setType(ConceptNameType.FULLY_SPECIFIED);

        NameDto nameDto2 = new NameDto();
        nameDto2.setName("Invalid Full name again");
        nameDto2.setType(ConceptNameType.FULLY_SPECIFIED);

        NameDto nameDto3 = new NameDto();
        nameDto3.setName("Identical name");
        nameDto3.setType(ConceptNameType.FULLY_SPECIFIED);
        nameDto3.setLocale("en");

        NameDto nameDto4 = new NameDto();
        nameDto4.setName("Identical name");
        nameDto4.setType(ConceptNameType.SHORT);
        nameDto4.setLocale("en");

        List<NameDto> nameDtoList = Lists.newArrayList(nameDto1, nameDto3, nameDto2, nameDto4);
        ConceptDto conceptDto = new ConceptDto();
        conceptDto.setId(123);
        conceptDto.setPreferredName("PREFERED_NAME");
        conceptDto.setNames(nameDtoList);

        Boolean isValid = validator.validate(conceptDto);
        assertTrue(isValid == false);
    }

}
