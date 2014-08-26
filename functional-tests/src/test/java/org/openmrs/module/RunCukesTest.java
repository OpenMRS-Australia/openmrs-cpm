package org.openmrs.module;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@Cucumber.Options(format = {"pretty", "html:target/cucumber-html-report", "json:target/cucumber-report.json"})
@RunWith(Cucumber.class)
public class RunCukesTest {
}

