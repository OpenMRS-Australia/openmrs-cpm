package org.openmrs.module.conceptpropose.web.common;

import org.apache.commons.codec.CharEncoding;

public interface CpmConstants {

    String SETTINGS_USER_NAME_PROPERTY = "cpm.username";

    String SETTINGS_PASSWORD_PROPERTY = "cpm.password";

    String SETTINGS_URL_PROPERTY = "cpm.url";

    String LIST_PROPOSAL_URL = "/module/cpm/proposals";

    String AUTH_DATA_DELIMITER = ":";

    String AUTH_CHAR_SET = CharEncoding.UTF_8;

    String AUTH_HEADER = "Authorization";

    String AUTH_TYPE = "Basic";

}
