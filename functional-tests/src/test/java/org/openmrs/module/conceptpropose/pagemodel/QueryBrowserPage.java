package org.openmrs.module.conceptpropose.pagemodel;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.conceptpropose.functionaltest.steps.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class QueryBrowserPage {

	private final String queryBrowserPageUrl;
	private WebDriver driver;
	private String openmrsUrl;

	public QueryBrowserPage() throws IOException {
		this.driver = SeleniumDriver.getDriver(); // request current driver every time new page constructed

		if (StringUtils.isNotBlank(System.getenv("openmrs_username"))) {
//			username = System.getenv("openmrs_username");
//			password = System.getenv("openmrs_password");

			if (StringUtils.isNotBlank(System.getenv("openmrs_url"))) {
				openmrsUrl = System.getenv("openmrs_url");
			}

			queryBrowserPageUrl = String.format("http://%s/%s/module/querybrowser/manage.form", System.getenv("openmrs_server"), openmrsUrl);
		} else {
			final Properties p = new Properties();
			final InputStream is = getClass().getResourceAsStream("/config.properties");

			p.load(new InputStreamReader(is));
//			username = p.getProperty("username");
//			password = p.getProperty("password");
			queryBrowserPageUrl = p.getProperty("openmrsUrl") + "/module/querybrowser/manage.form";
		}
	}
    public void removeAllProposals() {
        driver.get(queryBrowserPageUrl);

        final WebElement queryEl = driver.findElement(By.cssSelector("textarea[ng-model=\"query\"]"));
        queryEl.sendKeys("delete from conceptpropose_proposed_concept_package");
        driver.findElement(By.xpath("//button[contains(text(),'Submit Query')]")).click();
    }

    private void runSQLCommand(String command){
        driver.get(queryBrowserPageUrl);

        final WebElement queryEl = driver.findElement(By.cssSelector("textarea[ng-model=\"query\"]"));
        queryEl.sendKeys(command);
        driver.findElement(By.xpath("//button[contains(text(),'Submit Query')]")).click();
    }

    public void removeAllProposalsOnReviewModule() {
        runSQLCommand("delete from conceptreview_proposed_concept_review_package ");
        runSQLCommand("delete from conceptreview_proposed_concept_review ");
        runSQLCommand("delete from conceptreview_proposed_concept_review_answer");
        runSQLCommand("delete from conceptreview_proposed_concept_review_description ");
        runSQLCommand("delete from conceptreview_proposed_concept_review_name ");
        runSQLCommand("delete from conceptreview_proposed_concept_review_numeric");
    }

    public void createSubmittedProposalOnReviewModule(String description){
        String sql = "insert into  conceptreview_proposed_concept_review_package " +
            " (conceptreview_proposed_concept_review_package_id, uuid, conceptreview_proposed_concept_package_uuid, name, email, description, creator, date_created, changedBy, date_changed, version, status) " +
            " values " +
            " (1, '123', '456', 'submitter', 'test@email.com', '" + description + "', 1, '2014-01-01', NULL, '2014-01-01', 0, 'RECEIVED')";
        runSQLCommand(sql);
        sql = "insert into  conceptreview_proposed_concept_review_description" +
                " (proposed_concept_review, description, locale) " +
                " values " +
                " (1, 'A test description', 'en')";
        runSQLCommand(sql);
        sql = "insert into  conceptreview_proposed_concept_review_name" +
                " (conceptreview_review_name_id, proposed_concept_review, name, type, locale) " +
                " values " +
                "  (5,1, 'A test name1', 'FULLY SPECIFIED', 'en') " +
                " ,(6,1, 'A test name2', 'FULLY SPECIFIED', 'en') " +
                " ,(7,1, 'A test name3', 'FULLY SPECIFIED', 'en') " +
                "";
        runSQLCommand(sql);
        sql = "insert into  conceptreview_proposed_concept_review" +
                " (conceptreview_proposed_concept_review_id, conceptreview_proposed_concept_review_package_id, uuid, conceptreview_proposed_concept_uuid, version, status, datatype_id, concept_class_id) " +
                " values " +
                "  (5,1, 'UUID1', 'UUID_1', 0, 'RECEIVED', 4,3) " +
                " ,(6,1, 'UUID2', 'UUID_2', 0, 'RECEIVED', 4,3) " +
                " ,(7,1, 'UUID3', 'UUID_3', 0, 'RECEIVED', 4,3) " +
                "";
        runSQLCommand(sql);
    }
}
