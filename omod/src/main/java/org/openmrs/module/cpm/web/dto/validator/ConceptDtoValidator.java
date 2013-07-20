package org.openmrs.module.cpm.web.dto.validator;

import com.google.common.base.Function;
import com.google.common.collect.*;
import org.directwebremoting.util.Logger;
import org.openmrs.api.ConceptNameType;
import org.openmrs.module.cpm.web.dto.concept.ConceptDto;
import org.openmrs.module.cpm.web.dto.concept.NameDto;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class ConceptDtoValidator{

    protected final Logger log = Logger.getLogger(getClass());

     //TODO: incomplete validation implementation....WIP. Complete this and use it for DTO validation
     // 1.before submission and
     // 2.receiving end of Dictionary manager controller (before persisting the DTO)
    // A simplified version of these validations should probably be added to the front end too

    /* Date:  20 July 2013
     * Validation rules obtained from openmrs-core (org.openmrs.validator.ConceptValidator)

     * @should pass if the concept has at least one fully specified name added to it
     *
     * @should fail if there is a duplicate unretired concept name in the locale
	 * @should fail if there is a duplicate unretired preferred name in the same locale
	 * @should fail if there is a duplicate unretired fully specified name in the same locale
	 * @should fail if any names in the same locale for this concept are similar
	 *
	 * @should pass if the concept with a duplicate name is retired
	 * @should pass if the concept being validated is retired and has a duplicate name
	 * @should fail if any name is an empty string
	 * @should fail if the object parameter is null
	 * @should pass if the concept is being updated with no name change
	 * @should fail if any name is a null value
	 *
	 * @should not allow multiple preferred names in a given locale
	 * @should not allow multiple fully specified conceptNames in a given locale
	 * @should not allow multiple short names in a given locale
	 * @should not allow an index term to be a locale preferred name
	 * @should fail if there is no name explicitly marked as fully specified
	 * @should pass if the duplicate ConceptName is neither preferred nor fully Specified
	 * @should pass if the concept has a synonym that is also a short name
	 * @should fail if a term is mapped multiple times to the same concept
	 * @should not fail if a term has two new mappings on it
	 * @should fail if there is a duplicate unretired concept name in the same locale different than
	 *         the system locale
	 * @should pass for a new concept with a map created with deprecated concept map methods
	 * @should pass for an edited concept with a map created with deprecated concept map methods
	 */
    public Boolean validate(final ConceptDto conceptDto) {
        Boolean isValid = Boolean.TRUE;

        if (conceptDto == null ) {
            throw new IllegalArgumentException("The argument dto to the validator should not be null");
        }

        try{

            Map<String, Boolean> containsFullySpecifiedName = Maps.newHashMap();
            for(NameDto nameDto : conceptDto.getNames()) {
                if(containsFullySpecifiedName.get(nameDto.getLocale()) == null) {
                    containsFullySpecifiedName.put(nameDto.getLocale(), false);
                }
                if(nameDto.getName() == null || nameDto.getName().isEmpty()) {
                    log.debug("Name for conceptDto is null or empty for ConceptDtoId:" + conceptDto.getId());
                    return false;
                }
                if (ConceptNameType.FULLY_SPECIFIED.equals(nameDto.getType()) ) {
                    containsFullySpecifiedName.put(nameDto.getLocale(), true);
                }
            }
            if(containsFullySpecifiedName.isEmpty() || containsFullySpecifiedName.containsValue(false)) {
                log.debug("Missing fully specified name for ConceptDtoId:" + conceptDto.getId());
                return false;
            }
/*
            if(conceptDto.getDescriptions() == null || conceptDto.getDescriptions().isEmpty() ){
                log.debug("Descriptons for conceptDto is null or empty for ConceptDtoId:" + conceptDto.getId());
                return false;
            }*/

            if(containsDuplicateName(conceptDto.getNames())) {
                log.debug("Found duplicate for ConceptDtoId:" + conceptDto.getId());
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            isValid = Boolean.FALSE;
        }

        return isValid;
    }

    private Boolean containsDuplicateName(Collection<NameDto> nameDtos){

        //Grouped by locale
        Multimap<String, NameDto> groupedByLocaleNameDtos = Multimaps.index(nameDtos,
                new Function<NameDto, String>() {
                    @Override
                    public String apply(NameDto item) {
                        return item.getLocale() != null ? item.getLocale() : "";
                    }
                });



        //FInd the duplicates
        Function<NameDto, String> convertToNameStringFunction = new Function<NameDto,String>() {
                    public String apply(NameDto n) {
                        return n.getName();
                    }
                };

        for(String locale: groupedByLocaleNameDtos.keys()) {
            //Cannot have 2 same Name TYPE within 1 locale
            String fullName = null;
            String shortName = null;
            String otherName = null;
            for(NameDto nameDto : groupedByLocaleNameDtos.get(locale)) {
               if(ConceptNameType.FULLY_SPECIFIED.equals(nameDto.getType())) {
                    if(fullName == null) {
                        fullName = nameDto.getName();
                    } else {
                        return true;
                    }
                }
                if(ConceptNameType.SHORT.equals(nameDto.getType())) {
                    if(shortName == null) {
                        shortName = nameDto.getName();
                    } else {
                        return true;
                    }
                }

                if(nameDto.getType() == null) {
                    if(otherName == null) {
                        otherName = nameDto.getName();
                    } else {
                        return true;
                    }
                }
            }


            //Cannot have 2 identical name String within 1 locale ( regardless of name TYPE)
            Iterator<String> names = Iterables.transform(groupedByLocaleNameDtos.get(locale), convertToNameStringFunction).iterator();
            int existingSize = 0;
            List<String> localeSpecificNames = Lists.newArrayList();

            while(names.hasNext()) {
                localeSpecificNames.add(names.next()) ;
                existingSize++;

            }
            List<String> nonDuplicateNames = ImmutableSet.copyOf(localeSpecificNames).asList();
            if(nonDuplicateNames.size() < existingSize)  {
                //found duplicate
                return true;
            }
        }


        return false;
    }
}
