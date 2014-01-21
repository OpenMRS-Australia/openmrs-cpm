package org.openmrs.module.conceptpropose.web.dto;

import org.openmrs.module.conceptpropose.SubmissionResponseStatus;

public class SubmissionResponseDto {

    private int id;

    private SubmissionResponseStatus status;

    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
