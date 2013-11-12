package org.openmrs.module.conceptpropose.web.controller;

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

@Controller
public class CpmSettingsController {

    @RequestMapping(value = "/cpm/settings", method = RequestMethod.GET)
    public @ResponseBody
    Settings getSettings() {
        AdministrationService service = Context.getAdministrationService();
        Settings settings = new Settings();
        settings.setUrl(service.getGlobalProperty(CpmConstants.SETTINGS_URL_PROPERTY));
        settings.setUsername(service.getGlobalProperty(CpmConstants.SETTINGS_USER_NAME_PROPERTY));
        settings.setPassword(service.getGlobalProperty(CpmConstants.SETTINGS_PASSWORD_PROPERTY));
        return settings;
    }

    @RequestMapping(value = "/cpm/settings", method = RequestMethod.POST)
    public @ResponseBody Settings postNewSettings(@RequestBody Settings settings) {
        AdministrationService service = Context.getAdministrationService();
        service.saveGlobalProperty(new GlobalProperty(CpmConstants.SETTINGS_URL_PROPERTY, settings.getUrl()));
        service.saveGlobalProperty(new GlobalProperty(CpmConstants.SETTINGS_USER_NAME_PROPERTY, settings.getUsername()));
        service.saveGlobalProperty(new GlobalProperty(CpmConstants.SETTINGS_PASSWORD_PROPERTY, settings.getPassword()));
        return settings;
    }

}
