package org.openmrs.module.conceptpropose.web.dto.concept;

import java.util.List;

public class SearchConceptResultDto {

    private String requestNum;

    private List<ConceptDto> concepts;

    public String getRequestNum() {
        return requestNum;
    }

    public void setRequestNum(String requestNum) {
        this.requestNum = requestNum;
    }

    public List<ConceptDto> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<ConceptDto> concepts) {
        this.concepts = concepts;
    }
}
