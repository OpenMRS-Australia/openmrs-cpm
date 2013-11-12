package org.openmrs.module.conceptpropose.web.controller;

import org.directwebremoting.util.Logger;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.api.APIAuthenticationException;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.*;
import org.openmrs.module.conceptpropose.api.ProposedConceptService;
import org.openmrs.module.conceptpropose.api.exception.ConceptProposalSubmissionException;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptDto;
import org.openmrs.module.conceptpropose.web.dto.SubmissionDto;
import org.openmrs.module.conceptpropose.web.dto.SubmissionResponseDto;
import org.openmrs.module.conceptpropose.web.dto.SubmissionStatusDto;
import org.openmrs.module.conceptpropose.web.dto.concept.DescriptionDto;
import org.openmrs.module.conceptpropose.web.dto.concept.NameDto;
import org.openmrs.module.conceptpropose.web.dto.concept.NumericDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class DictionaryManagerController {

    protected final Logger log = Logger.getLogger(getClass());

    //
    // Proposer-Reviewer webservice endpoints
    //

    @RequestMapping(value = "/cpm/dictionarymanager/proposals", method = RequestMethod.POST)
    public @ResponseBody SubmissionResponseDto submitProposal(@RequestBody final SubmissionDto incomingProposal) throws IOException {

        //TODO: method size getting large...
        final ProposedConceptResponsePackage proposedConceptResponsePackage = new ProposedConceptResponsePackage();
        SubmissionResponseDto responseDto = new SubmissionResponseDto();

        try{
            final ProposedConceptService service = Context.getService(ProposedConceptService.class);
            proposedConceptResponsePackage.setName(incomingProposal.getName());
            proposedConceptResponsePackage.setEmail(incomingProposal.getEmail());
            proposedConceptResponsePackage.setDescription(incomingProposal.getDescription());
            proposedConceptResponsePackage.setProposedConceptPackageUuid("is-this-really-needed?");

            if (incomingProposal.getConcepts() == null) {
                throw new ConceptProposalSubmissionException("Concepts are missing in the submission");

            }
            for (ProposedConceptDto concept : incomingProposal.getConcepts()) {
                ProposedConceptResponse response = new ProposedConceptResponse();

                List<ProposedConceptResponseName> names = new ArrayList<ProposedConceptResponseName>();
                if (concept.getNames() == null || concept.getNames().isEmpty()) {
                    throw new ConceptProposalSubmissionException("Missing concept names for conceptId:" + concept.getId());

                }
                for (NameDto nameDto: concept.getNames()) {
                    ProposedConceptResponseName name = new ProposedConceptResponseName();
                    name.setName(nameDto.getName());
                    name.setType(nameDto.getType());
                    name.setLocale(new Locale(nameDto.getLocale()));
                    names.add(name);
                }
                response.setNames(names);

                List<ProposedConceptResponseDescription> descriptions = new ArrayList<ProposedConceptResponseDescription>();
                if (concept.getDescriptions() == null || concept.getDescriptions().isEmpty()) {
                    throw new ConceptProposalSubmissionException("Missing concept description for conceptId:" + concept.getId());

                }
                for (DescriptionDto descriptionDto: concept.getDescriptions()) {
                    ProposedConceptResponseDescription description = new ProposedConceptResponseDescription();
                    description.setDescription(descriptionDto.getDescription());
                    description.setLocale(new Locale(descriptionDto.getLocale()));
                    descriptions.add(description);
                }
                response.setDescriptions(descriptions);

                response.setProposedConceptUuid(concept.getUuid());
                response.setComment(concept.getComment());

                final ConceptDatatype conceptDatatype = Context.getConceptService().getConceptDatatypeByUuid(concept.getDatatype());
                if (conceptDatatype == null) {
                    throw new ConceptProposalSubmissionException("Datatype is missing for conceptId:" + concept.getId());
                }
                response.setDatatype(conceptDatatype);

                if (conceptDatatype.getUuid().equals(ConceptDatatype.NUMERIC_UUID)) {

                    final NumericDto numericDetails = concept.getNumericDetails();
                    ProposedConceptResponseNumeric proposedConceptResponseNumeric = new ProposedConceptResponseNumeric();

                    proposedConceptResponseNumeric.setUnits(numericDetails.getUnits());
                    proposedConceptResponseNumeric.setPrecise(numericDetails.getPrecise());
                    proposedConceptResponseNumeric.setHiNormal(numericDetails.getHiNormal());
                    proposedConceptResponseNumeric.setHiCritical(numericDetails.getHiCritical());
                    proposedConceptResponseNumeric.setHiAbsolute(numericDetails.getHiAbsolute());
                    proposedConceptResponseNumeric.setLowNormal(numericDetails.getLowNormal());
                    proposedConceptResponseNumeric.setLowCritical(numericDetails.getLowCritical());
                    proposedConceptResponseNumeric.setLowAbsolute(numericDetails.getLowAbsolute());

                    response.setNumericDetails(proposedConceptResponseNumeric);
                }

                final ConceptClass conceptClass = Context.getConceptService().getConceptClassByUuid(concept.getConceptClass());
                if (conceptClass == null) {
                    throw new ConceptProposalSubmissionException("Concept class expected for conceptId:"+ concept.getId());
                }
                response.setConceptClass(conceptClass);

                proposedConceptResponsePackage.addProposedConcept(response);
            }


            service.saveProposedConceptResponsePackage(proposedConceptResponsePackage);
            responseDto.setStatus(SubmissionResponseStatus.SUCCESS);
            responseDto.setMessage("All Good!");
            if (proposedConceptResponsePackage.getId() != null) {
            	responseDto.setId(proposedConceptResponsePackage.getId());
            }

        } catch (Exception ex) {
            //TODO: update error handling, more specific catch block rather than the generic Exception, add proper logging etc.
            ex.printStackTrace();
            responseDto.setStatus(SubmissionResponseStatus.FAILURE);
            responseDto.setMessage(ex.getMessage());
        }

        return responseDto;
    }

    @RequestMapping(value = "/cpm/dictionarymanager/proposalstatus/{proposalId}", method = RequestMethod.GET)
    public @ResponseBody SubmissionStatusDto getSubmissionStatus(@PathVariable int proposalId) {

        final ProposedConceptService service = Context.getService(ProposedConceptService.class);
        final ProposedConceptResponsePackage aPackage = service.getProposedConceptResponsePackageById(proposalId);

        return new SubmissionStatusDto(aPackage.getStatus());
    }

    @ExceptionHandler(APIAuthenticationException.class)
    public void apiAuthenticationExceptionHandler(Exception e, HttpServletResponse response) {

        if (Context.isAuthenticated()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.addHeader("WWW-Authenticate", "Basic realm=\"OpenMRS\"");
        }
    }
}
