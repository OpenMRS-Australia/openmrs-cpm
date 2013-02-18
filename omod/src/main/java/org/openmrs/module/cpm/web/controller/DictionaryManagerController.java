package org.openmrs.module.cpm.web.controller;

import org.openmrs.module.cpm.web.dto.SubmissionResponseDto;
import com.google.common.collect.Lists;
import org.openmrs.Concept;
import org.openmrs.ConceptName;
import org.openmrs.ConceptSearchResult;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.ProposedConcept;
import org.openmrs.module.cpm.ProposedConceptPackage;
import org.openmrs.module.cpm.ProposedConceptResponse;
import org.openmrs.module.cpm.ProposedConceptResponsePackage;
import org.openmrs.module.cpm.api.ProposedConceptService;
import org.openmrs.module.cpm.web.dto.ConceptDto;
import org.openmrs.module.cpm.web.dto.ProposedConceptDto;
import org.openmrs.module.cpm.web.dto.ProposedConceptPackageDto;
import org.openmrs.module.cpm.web.dto.ProposedConceptResponsePackageDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.ApplicationContext;

@Controller
public class DictionaryManagerController {
    @Autowired
    private ApplicationContext appContext;

    @RequestMapping(value = "/cpm/dictionarymanager/proposals", method = RequestMethod.POST)
    public @ResponseBody
    SubmissionResponseDto submitProposal(@RequestBody final ProposedConceptResponsePackageDto incomingProposal) {
        appContext.getBean("submissionRestTemplate", RestTemplate.class);
        return new SubmissionResponseDto();
    }
}
