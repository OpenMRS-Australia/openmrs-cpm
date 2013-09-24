package org.openmrs.module.cpm.functionaltest.steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang.StringUtils;
import org.openmrs.module.cpm.pagemodel.AdminPage;
import org.openmrs.module.cpm.pagemodel.SettingsPage;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class SeleniumStepDefs {
	
	private AdminPage adminPage;
	private static FirefoxDriver driver;
	private String openmrsUrl = "openmrs";

    @Before("@Selenium")
    public void startScenario() throws IOException {
        driver = new FirefoxDriver();
        login();
    }

    @After("@Selenium")
    public static void endScenario() {
        driver.quit();
    }

    public void login() throws IOException {
        String username;
        String password;
        String adminPageUrl;

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

        adminPage = new AdminPage(driver);
        adminPage.navigateToAdminPage(adminPageUrl);
        adminPage.login(username, password);
    }
    
}
