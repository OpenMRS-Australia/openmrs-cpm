package org.openmrs.module.conceptpropose.test;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.Environment;
import org.junit.Ignore;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Sub-classing the core OpenMRS test class so that we have an opportunity to modify runtime properties and 
 * configure H2 for tracking
 */
@Ignore("Base class for testing")
public class CpmBaseContextSensitive extends BaseModuleContextSensitiveTest {
	
	private static Log log = LogFactory.getLog(CpmBaseContextSensitive.class);

	public CpmBaseContextSensitive() {
		super();
		Properties props = getRuntimeProperties();
		String oldUrlProperty = props.getProperty(Environment.URL);
		String newUrlProperty = oldUrlProperty + ";TRACE_LEVEL_FILE=4"; 
		
		if (log.isInfoEnabled())
			log.info("Changing URL property from: " + oldUrlProperty + " to " + newUrlProperty);
		
		props.setProperty(Environment.URL, newUrlProperty);
		Context.setRuntimeProperties(props);
	}
	
}