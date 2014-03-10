package org.openmrs.module.conceptpropose.web.controller;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.openmrs.GlobalProperty;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.web.common.CpmConstants;
import org.openmrs.module.conceptpropose.web.dto.Settings;
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

    private final int httpConnectionTimeout = 10000;
    private final int httpSocketTimeout = 10000;

    public CpmSettingsController() {
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
    public @ResponseBody boolean testConnection(@RequestBody Settings settings) {
        Credentials credentials = new UsernamePasswordCredentials(
                settings.getUsername(),
                settings.getPassword());

        final String url = settings.getUrl() + "/ws/conceptreview/dictionarymanager/status";

        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, httpConnectionTimeout);
        HttpConnectionParams.setSoTimeout(params, httpSocketTimeout);

        boolean connectionSucceeded = false;

        try {
            DefaultHttpClient httpclient = new DefaultHttpClient(params);
            httpclient.getCredentialsProvider().setCredentials(AuthScope.ANY, credentials);
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);
            int statusCode = response.getStatusLine().getStatusCode();
            connectionSucceeded = (statusCode == HttpStatus.SC_OK);

            if (!connectionSucceeded) {
                log.error("Test " + url + " failed with status code " + statusCode);
            }

            return connectionSucceeded;
        }
        catch (IOException ex) {
            log.error("Test " + url + " failed with exception {" + ex.getMessage() + "}");
            return connectionSucceeded;
        }
    }
}
