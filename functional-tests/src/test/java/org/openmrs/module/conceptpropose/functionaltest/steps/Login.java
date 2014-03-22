package org.openmrs.module.conceptpropose.functionaltest.steps;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.conceptpropose.pagemodel.AdminPage;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Saman
 * Date: 10/8/13
 * Time: 8:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class Login {
    public AdminPage login() throws IOException {
        String username;
        String password;
        String adminPageUrl;
        String openmrsUrl = "openmrs";
        AdminPage adminPage;

        if (StringUtils.isNotBlank(System.getenv("openmrs_username"))) {
            username = System.getenv("openmrs_username");
            password = System.getenv("openmrs_password");

            if (StringUtils.isNotBlank(System.getenv("openmrs_url"))) {
                openmrsUrl = System.getenv("openmrs_url");
            }

            adminPageUrl = String.format("http://%s/%s/admin", System.getenv("openmrs_server"), openmrsUrl);
        } else {
            final Properties p = new Properties();
            final InputStream is = getClass().getResourceAsStream("/config.properties");

            p.load(new InputStreamReader(is));
            username = p.getProperty("username");
            password = p.getProperty("password");
            adminPageUrl = p.getProperty("adminPageUrl");
        }


        adminPage = new AdminPage(SeleniumDriver.getDriver(), adminPageUrl, openmrsUrl);
        adminPage.login(username, password);
        return adminPage;
    }
}
