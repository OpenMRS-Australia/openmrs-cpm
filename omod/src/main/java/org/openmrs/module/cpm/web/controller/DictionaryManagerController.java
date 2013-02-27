package org.openmrs.module.cpm.web.controller;

import org.openmrs.module.cpm.web.dto.SubmissionDto;
import org.openmrs.module.cpm.web.dto.SubmissionResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DictionaryManagerController {

    @RequestMapping(value = "/cpm/dictionarymanager/proposals", method = RequestMethod.GET)
    public @ResponseBody SubmissionResponseDto submitProposal(@RequestBody final SubmissionDto incomingProposal) {
        SubmissionResponseDto responseDto = new SubmissionResponseDto();
        responseDto.setStatus("OK");
        return responseDto;
    }
}
