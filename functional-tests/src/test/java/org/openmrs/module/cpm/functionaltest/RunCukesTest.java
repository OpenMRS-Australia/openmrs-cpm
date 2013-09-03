package org.openmrs.module.cpm.functionaltest;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@Cucumber.Options(format = {"pretty", "html:target/cucumber-html-report", "json-pretty:target/cucumber-report.json"})
@RunWith(Cucumber.class)
public class RunCukesTest {
}
