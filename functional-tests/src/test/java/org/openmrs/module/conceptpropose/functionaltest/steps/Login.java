package org.openmrs.module.conceptpropose.functionaltest.steps;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.conceptpropose.functionaltest.steps.SeleniumDriver;
import org.openmrs.module.common.pagemodel.AdminPage;

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
    public static class Credentials{
        String username;
        String password;
        String openmrsUrl;
        String adminPageUrl;
        String settingsPageUrl;
    }
    public static Credentials getCredentials(Class<?> theClass) throws IOException{
        String username;
        String password;
        String adminPageUrl;
        String openmrsUrl = "openmrs";
        String settingsPageUrl;

        if (StringUtils.isNotBlank(System.getenv("openmrs_username"))) {
            username = System.getenv("openmrs_username");
            password = System.getenv("openmrs_password");

            if (StringUtils.isNotBlank(System.getenv("openmrs_url"))) {
                openmrsUrl = System.getenv("openmrs_url");
            }

            adminPageUrl = String.format("http://%s/%s/admin", System.getenv("openmrs_server"), openmrsUrl);
            settingsPageUrl = String.format("http://%s/%s", System.getenv("openmrs_server"), openmrsUrl);
        } else {
            final Properties p = new Properties();
            final InputStream is = theClass.getResourceAsStream("/config.properties");

            p.load(new InputStreamReader(is));
            username = p.getProperty("username");
            password = p.getProperty("password");
            adminPageUrl = p.getProperty("openmrsUrl") + "/admin";
            settingsPageUrl = p.getProperty("openmrsUrl") + "";
        }
        Credentials credentials = new Credentials();
        credentials.username = username;
        credentials.password = password;
        credentials.openmrsUrl = openmrsUrl;
        credentials.adminPageUrl = adminPageUrl;
        credentials.settingsPageUrl = settingsPageUrl;
        return credentials;
    }
    public AdminPage login() throws IOException{
        Credentials credentials = Login.getCredentials(getClass());
        AdminPage adminPage;



        adminPage = new AdminPage(SeleniumDriver.getDriver(), credentials.adminPageUrl, credentials.openmrsUrl);
        adminPage.login(credentials.username, credentials.password);
        return adminPage;
    }
}
