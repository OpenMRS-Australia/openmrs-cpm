package org.openmrs.module.cpm;

import java.io.FileNotFoundException;
import java.util.List;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTestRunner;

import junit.framework.Test;
import junit.framework.TestResult;

import org.apache.tools.ant.taskdefs.optional.junit.XMLJUnitResultFormatter;

public class MyTestRunner {

	public static void main(String[] args) throws FileNotFoundException {
		JUnitTestRunner runner = new JUnitTestRunner(new JUnitTest("org.openmrs.module.cpm.TestAdminPageLinks"), true, true, true);
		XMLJUnitResultFormatter xmljUnitResultFormatter = new XMLJUnitResultFormatter();
		xmljUnitResultFormatter.setOutput(System.out);
		runner.addFormatter(xmljUnitResultFormatter);
		runner.run();
	}
}
