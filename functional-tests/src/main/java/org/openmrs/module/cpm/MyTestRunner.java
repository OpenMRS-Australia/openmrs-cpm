package org.openmrs.module.cpm;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTestRunner;
import org.apache.tools.ant.taskdefs.optional.junit.XMLJUnitResultFormatter;

import java.io.FileNotFoundException;

public class MyTestRunner {

	public static void main(String[] args) throws FileNotFoundException {
		JUnitTestRunner runner = new JUnitTestRunner(new JUnitTest("org.openmrs.module.cpm.functionaltest.TestAdminPageLinks"), true, true, true);
		XMLJUnitResultFormatter xmljUnitResultFormatter = new XMLJUnitResultFormatter();
		xmljUnitResultFormatter.setOutput(System.out);
		runner.addFormatter(xmljUnitResultFormatter);
		runner.run();
	}
}