package org.openmrs.module.conceptpropose.web.dto;

import org.openmrs.module.conceptpropose.SubmissionResponseStatus;

public class SubmissionResponseDto {

    private Integer id;

    private SubmissionResponseStatus status;

    private String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SubmissionResponseStatus getStatus() {
        return status;
    }

    public void setStatus(SubmissionResponseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
