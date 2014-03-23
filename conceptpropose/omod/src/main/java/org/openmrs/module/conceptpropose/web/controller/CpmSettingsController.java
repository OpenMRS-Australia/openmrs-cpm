package org.openmrs.module.conceptpropose.web.controller;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.openmrs.GlobalProperty;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.web.authentication.factory.AuthHttpHeaderFactory;
import org.openmrs.module.conceptpropose.web.common.CpmConstants;
import org.openmrs.module.conceptpropose.web.dto.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.directwebremoting.util.Logger;
import java.io.IOException;

@Controller
public class CpmSettingsController {
    private final Logger log = Logger.getLogger(CpmSettingsController.class);

    private final int httpConnectionTimeout = 30000;
    private final int httpSocketTimeout = 30000;

    private final AuthHttpHeaderFactory httpHeaderFactory;

    @Autowired
    public CpmSettingsController(final AuthHttpHeaderFactory httpHeaderFactory) {
        this.httpHeaderFactory = httpHeaderFactory;
    }

    @RequestMapping(value = "/conceptpropose/settings", method = RequestMethod.GET)
    public @ResponseBody Settings getSettings() {
        AdministrationService service = Context.getAdministrationService();
        Settings settings = new Settings();
        settings.setUrl(service.getGlobalProperty(CpmConstants.SETTINGS_URL_PROPERTY));
        settings.setUsername(service.getGlobalProperty(CpmConstants.SETTINGS_USER_NAME_PROPERTY));
        settings.setPassword(service.getGlobalProperty(CpmConstants.SETTINGS_PASSWORD_PROPERTY));
        return settings;
    }

    @RequestMapping(value = "/conceptpropose/settings", method = RequestMethod.POST)
    public @ResponseBody Settings postNewSettings(@RequestBody Settings settings) {
        AdministrationService service = Context.getAdministrationService();
        service.saveGlobalProperty(new GlobalProperty(CpmConstants.SETTINGS_URL_PROPERTY, settings.getUrl()));
        service.saveGlobalProperty(new GlobalProperty(CpmConstants.SETTINGS_USER_NAME_PROPERTY, settings.getUsername()));
        service.saveGlobalProperty(new GlobalProperty(CpmConstants.SETTINGS_PASSWORD_PROPERTY, settings.getPassword()));
        return settings;
    }

    // Using Apache HttpComponents because OpenMRS includes Spring 3.0.5 without
    // Jakarta Commons HttpClient and so cannot set timeout using Spring RestTemplate
    @RequestMapping(value = "/conceptpropose/settings/connectionResult", method = RequestMethod.POST)
    public @ResponseBody String testConnection(@RequestBody Settings settings) {
        final String url = settings.getUrl() + "/ws/conceptreview/dictionarymanager/status";

        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, httpConnectionTimeout);
        HttpConnectionParams.setSoTimeout(params, httpSocketTimeout);

        try {
            DefaultHttpClient httpclient = new DefaultHttpClient(params);
            HttpGet httpget = new HttpGet(url);
            Header authHeader =
                    httpHeaderFactory.createApacheHeader(settings.getUsername(), settings.getPassword());
            httpget.addHeader(authHeader);
            HttpResponse response = httpclient.execute(httpget);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK)
                return "Success";

            log.error("Test " + url + " failed with status code " + statusCode);
            return ("HTTP status code " + statusCode + " " + response.getStatusLine().getReasonPhrase());
        } catch (ConnectTimeoutException ex) {
            log.error("Test " + url + " failed with ConnectTimeoutException {" + ex.getMessage() + "}");
            return ("Connection timed out");
        } catch (IOException ex) {
            log.error("Test " + url + " failed with IOException {" + ex.getMessage() + "}");
            return ex.getMessage();
        }
    }
}
