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

	@Test
	public void testCreateConceptForm() throws Exception {

		request = new MockHttpServletRequest("GET", "/module/cpm/concept.form");
		final CpmController controller = new CpmController();
		final ModelAndView handle = adapter.handle(request, response, controller);
		assertEquals("/module/cpm/conceptCreate", handle.getViewName());
		assertEquals(200, response.getStatus());

	}

}
