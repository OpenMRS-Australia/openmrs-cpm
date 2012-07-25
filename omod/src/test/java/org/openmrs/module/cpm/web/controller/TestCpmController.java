package org.openmrs.module.cpm.web.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

public class TestCpmController extends BaseModuleContextSensitiveTest {

	private MockHttpServletRequest request;
	private final MockHttpServletResponse response = new MockHttpServletResponse();
	private final AnnotationMethodHandlerAdapter adapter = new AnnotationMethodHandlerAdapter();
	private final CpmController controller = new CpmController();

	@Test
	public void testCreateConceptForm() throws Exception {
		request = new MockHttpServletRequest("GET", "/module/cpm/concept.form");
		final ModelAndView handle = adapter.handle(request, response, controller);
		assertEquals("/module/cpm/conceptCreate", handle.getViewName());
		assertEquals(200, response.getStatus());
	}

	@Test
	public void testMonitorProposalsList() throws Exception {
		request = new MockHttpServletRequest("GET", "/module/cpm/monitor.list");
		final ModelAndView handle = adapter.handle(request, response, controller);
		assertEquals("/module/cpm/monitor", handle.getViewName());
		assertEquals(200, response.getStatus());
	}

}
