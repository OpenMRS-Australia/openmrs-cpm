package org.openmrs.module.cpm.web.controller;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@PrepareForTest(Context.class)
@Ignore
public class TestDictionaryManagerController extends BaseCpmOmodTest {

	private MockHttpServletRequest request;
	private final MockHttpServletResponse response = new MockHttpServletResponse();
	private final AnnotationMethodHandlerAdapter adapter = new AnnotationMethodHandlerAdapter();
	private final DictionaryManagerController controller = new DictionaryManagerController();

	// See http://www.jayway.com/2010/12/28/using-powermock-with-spring-integration-testing/
	// for using powermock when used with Spring
	// (Can't do a @RunWith(PowerMockRunner.class) )
	//
	// Also see https://wiki.openmrs.org/display/docs/Mock+Doc
//	@Rule
//	public PowerMockRule rule = new PowerMockRule();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void submitProposal_shouldReturn200() throws Exception {
		request = new MockHttpServletRequest("POST", "/cpm/dictionarymanager/proposals");
		request.addHeader("Content-Type", "application/json");
		final ModelAndView handle = adapter.handle(request, response, controller);
		assertEquals(200, response.getStatus());
	}

}
