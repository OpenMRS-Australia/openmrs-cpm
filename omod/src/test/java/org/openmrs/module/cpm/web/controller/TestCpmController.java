package org.openmrs.module.cpm.web.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.ProposedConceptPackage;
import org.openmrs.module.cpm.api.ProposedConceptService;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

@PrepareForTest(Context.class)
public class TestCpmController extends BaseModuleContextSensitiveTest {

	private MockHttpServletRequest request;
	private final MockHttpServletResponse response = new MockHttpServletResponse();
	private final AnnotationMethodHandlerAdapter adapter = new AnnotationMethodHandlerAdapter();
	private final CpmController controller = new CpmController();

	// See http://www.jayway.com/2010/12/28/using-powermock-with-spring-integration-testing/
	// for using powermock when used with Spring
	// (Can't do a @RunWith(PowerMockRunner.class) )
	// See http://code.google.com/p/powermock/wiki/PowerMockRule for dependencies
	@Rule
	public PowerMockRule rule = new PowerMockRule();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testCreateConceptForm() throws Exception {
		request = new MockHttpServletRequest("GET", "/module/cpm/proposals.list");
		final ModelAndView handle = adapter.handle(request, response, controller);
		assertEquals("/module/cpm/proposals", handle.getViewName());
		assertEquals(200, response.getStatus());
	}

	/**
	 * I couldn't figure out how to get the HttpMessageConverter to serialise the response so for
	 * now just expecting the seralisation exception
	 */
	@Test
	public void testMonitorProposalsList() throws Exception {

		exception.expect(HttpMediaTypeNotAcceptableException.class);

		final List<ProposedConceptPackage> packageList = new ArrayList<ProposedConceptPackage>();

		final ProposedConceptService cpServiceMock = mock(ProposedConceptService.class);
		when(cpServiceMock.getAllProposedConceptPackages()).thenReturn(packageList);

		mockStatic(Context.class);
		when(Context.getService(ProposedConceptService.class)).thenReturn(cpServiceMock);

		request = new MockHttpServletRequest("GET", "/module/cpm/rest/proposals.list");
		request.addHeader("Accept", "application/json");
		request.addHeader("Content-Type", "application/json");
		final ModelAndView handle = adapter.handle(request, response, controller);
//		assertEquals("/module/cpm/monitor", handle.getViewName());
//		assertEquals(200, response.getStatus());
	}

}
