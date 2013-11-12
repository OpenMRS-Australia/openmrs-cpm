package org.openmrs.module.conceptpropose.web.controller;


import org.junit.Ignore;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

/**
 * Sub-classing the core OpenMRS Web test class
 */
public abstract class BaseCpmOmodTest extends BaseModuleWebContextSensitiveTest {
    // See http://www.jayway.com/2010/12/28/using-powermock-with-spring-integration-testing/
    // for using powermock when used with Spring
    // (Can't do a @RunWith(PowerMockRunner.class) )
    //
    // Also see https://wiki.openmrs.org/display/docs/Mock+Doc
}
