package org.openmrs.module.cpmn;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;


public class TestCreateProposalLink {

	private AdminPage page;
	private final FirefoxDriver driver = new FirefoxDriver();

	@Before
	public void load() {
		page = new AdminPage(driver);
	}

	@Test
	public void test() {
		assertTrue(page.hasCreateProposalLink());
	}

	@After
	public void dasIstJaKaputt() {
		driver.quit();
	}

}
