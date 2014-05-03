package org.openmrs.module.conceptpropose.web.authentication.factory;

import com.google.common.base.Joiner;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.openmrs.module.conceptpropose.web.common.CpmConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
public class AuthHttpHeaderFactory {

    public HttpHeaders create(final String username, final String password){
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", createAuthValue(username, password));
        return httpHeaders;
    }

    public Header createApacheHeader(final String username, final String password){
        final Header httpHeader = new BasicHeader("Authorization", createAuthValue(username, password));
        return httpHeader;
    }

    private String createAuthValue(final String username, final String password) {
        final String auth = Joiner.on(CpmConstants.AUTH_DATA_DELIMITER).skipNulls().join(username, password);
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName(CpmConstants.AUTH_CHAR_SET)));
        final String authHeader = CpmConstants.AUTH_TYPE + " " + new String( encodedAuth );
        return authHeader;
    }

}
