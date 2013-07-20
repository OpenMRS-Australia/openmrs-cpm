package org.openmrs.module.cpm.web.filter;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.apache.xerces.impl.dv.util.Base64;
import org.openmrs.api.context.Context;
import org.directwebremoting.util.Logger;
import org.openmrs.module.cpm.web.common.CpmConstants;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthorizationFilter implements Filter {

    protected final Logger log = Logger.getLogger(getClass());

    private static final String DATA_DELIMITER = ":";

    @Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		// do nothing
	}

	@Override
	public void destroy() {
		// do nothing
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		// skip if we're already authenticated, or it's not an HTTP request
		if (!Context.isAuthenticated() && request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String basicAuth = httpRequest.getHeader(CpmConstants.AUTH_HEADER);
			if (basicAuth != null) {
				// this is "Basic ${base64encode(username + ":" + password)}"
				try {
					basicAuth = basicAuth.substring(6); // remove the leading "Basic "
					String decoded = new String(Base64.decode(basicAuth), CharEncoding.UTF_8);
					String[] userAndPass = StringUtils.split(decoded, DATA_DELIMITER);
					Context.authenticate(userAndPass[0], userAndPass[1]);
				}
				catch (Exception ex) {
					// This filter never stops execution. If the user failed to
					// authenticate, that will be caught later.
                    log.error("Error in CPM authentication", ex);
                }
			}
		}
		// continue with the filter chain in all circumstances
		chain.doFilter(request, response);
	}
}