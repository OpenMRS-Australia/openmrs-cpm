package org.openmrs.module.conceptpropose.web.controller;

import org.openmrs.GlobalProperty;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.web.authentication.factory.AuthHttpHeaderFactory;
import org.openmrs.module.conceptpropose.web.common.CpmConstants;
import org.openmrs.module.conceptpropose.web.dto.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestOperations;

@Controller
public class CpmSettingsController {
	private final RestOperations submissionRestTemplate;

	private final AuthHttpHeaderFactory httpHeaderFactory;


    @Autowired
    public CpmSettingsController(final RestOperations submissionRestTemplate,
                          final AuthHttpHeaderFactory httpHeaderFactory) {
        this.submissionRestTemplate = submissionRestTemplate;
        this.httpHeaderFactory = httpHeaderFactory;
    }
	
    @RequestMapping(value = "/conceptpropose/settings", method = RequestMethod.GET)
    public @ResponseBody Settings getSettings() {
        AdministrationService service = Context.getAdministrationService();
        Settings settings = new Settings();
        settings.setUrl(service.getGlobalProperty(CpmConstants.SETTINGS_URL_PROPERTY));
        settings.setUsername(service.getGlobalProperty(CpmConstants.SETTINGS_USER_NAME_PROPERTY));
        settings.setPassword(service.getGlobalProperty(CpmConstants.SETTINGS_PASSWORD_PROPERTY));
        //settings.setUrlInvalid(checkSettingsUrlInvalid()); // will hang UI and fail functional test if url is invalid
        return settings;
    }

    @RequestMapping(value = "/conceptpropose/settings", method = RequestMethod.POST)
    public @ResponseBody Settings postNewSettings(@RequestBody Settings settings) {
        AdministrationService service = Context.getAdministrationService();
        service.saveGlobalProperty(new GlobalProperty(CpmConstants.SETTINGS_URL_PROPERTY, settings.getUrl()));
        service.saveGlobalProperty(new GlobalProperty(CpmConstants.SETTINGS_USER_NAME_PROPERTY, settings.getUsername()));
        service.saveGlobalProperty(new GlobalProperty(CpmConstants.SETTINGS_PASSWORD_PROPERTY, settings.getPassword()));
        settings.setUrlInvalid(checkSettingsUrlInvalid());
        return settings;
    }

    private boolean checkSettingsUrlInvalid() {
    	AdministrationService service = Context.getAdministrationService();

		HttpHeaders headers = httpHeaderFactory.create(
				service.getGlobalProperty(CpmConstants.SETTINGS_USER_NAME_PROPERTY),
                service.getGlobalProperty(CpmConstants.SETTINGS_PASSWORD_PROPERTY)
        );

		final String url = service.getGlobalProperty(CpmConstants.SETTINGS_URL_PROPERTY) + "/ws/conceptpropose/settings";
		try {
			ResponseEntity<Settings> responseEntity = submissionRestTemplate.getForEntity(url, Settings.class, headers);
			return responseEntity.getStatusCode() != HttpStatus.OK;
		}
		catch (Exception ex) {
			return true;
		}
    }
}
