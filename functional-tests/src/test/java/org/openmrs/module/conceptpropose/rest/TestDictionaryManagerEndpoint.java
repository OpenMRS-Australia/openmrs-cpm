package org.openmrs.module.conceptpropose.rest;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class TestDictionaryManagerEndpoint {

	public static final String USERNAME = "admin";
	public static final String PASSWORD = "Admin123";
	public static final String RESOURCE_URI = "http://localhost:8080/openmrs/ws/cpm/dictionarymanager/proposals";
	public static final String STATUS_URI = "http://localhost:8080/openmrs/ws/cpm/dictionarymanager/proposalstatus";

	private HttpHost targetHost;
	private DefaultHttpClient httpclient;


    @Ignore
	@Test
	public void submitProposalWithNoAuth_shouldReceived401() throws IOException {

		final HttpPost httpPost = setupHttpPostWithJson("proposal-no-concepts.json");

		HttpResponse response = httpclient.execute(targetHost, httpPost);

		assertThat(response.getStatusLine().getStatusCode(), equalTo(401));
	}

	// TODO: setup account that doesn't have permission to access the cpm service
	@Test
	@Ignore
	public void submitProposalWithAccountThatDoesntHaveCorrectPermission_shouldReceive403() throws IOException {

		final HttpPost httpPost = setupHttpPostWithJson("proposal-no-concepts.json");

		// setup request with alternative credentials
		final HttpResponse response = null;

		assertThat(response.getStatusLine().getStatusCode(), equalTo(401));
	}

	// TODO: verify that a proposal with no concepts is allowed
    @Ignore
	@Test
	public void submitProposalNoConcepts_shouldReceive200() throws IOException {

		HttpPost httpPost = setupHttpPostWithJson("proposal-no-concepts.json");
		BasicHttpContext localcontext = setupBasicAuthentication();

		HttpResponse response = httpclient.execute(targetHost, httpPost, localcontext);

		assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
	}

    @Ignore
	@Test
	public void submitProposalSingleConcept_shouldReceive200() throws IOException {

		HttpPost httpPost = setupHttpPostWithJson("proposal-with-concepts.json");
		BasicHttpContext localcontext = setupBasicAuthentication();

		HttpResponse response = httpclient.execute(targetHost, httpPost, localcontext);

		assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
	}

    @Ignore
	@Test
	public void submitProposalThenRetrieveStatus_statusShouldBeReceived() throws IOException {

		HttpPost httpPost = setupHttpPostWithJson("proposal-with-concepts.json");
		BasicHttpContext localcontext = setupBasicAuthentication();
		HttpResponse response = httpclient.execute(targetHost, httpPost, localcontext);
		ObjectMapper mapper = new ObjectMapper();
		final JsonNode rootNode = mapper.readTree(response.getEntity().getContent());
		final JsonNode idNode = rootNode.path("id");
		final int resourceId = idNode.getIntValue();

		HttpGet get = new HttpGet(STATUS_URI + "/" + resourceId);
		final HttpResponse response2 = httpclient.execute(targetHost, get, localcontext);
		final JsonNode rootNode2 = mapper.readTree(response2.getEntity().getContent());
		final JsonNode statusNode = rootNode2.path("status");

		assertThat(statusNode.getTextValue(), equalTo("RECEIVED"));
	}


	//
	// Test setup
	//

	@Before
	public void before() {
		targetHost = new HttpHost("localhost", 8080, "http");
		httpclient = new DefaultHttpClient();
	}

	private BasicHttpContext setupBasicAuthentication() {
		httpclient.getCredentialsProvider().setCredentials(
				new AuthScope(targetHost.getHostName(), targetHost.getPort()),
				new UsernamePasswordCredentials(USERNAME, PASSWORD));

		// Create AuthCache instance
		AuthCache authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local
		// auth cache
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(targetHost, basicAuth);

		// Add AuthCache to the execution context
		BasicHttpContext localcontext = new BasicHttpContext();
		localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);

		return localcontext;
	}

	private HttpPost setupHttpPostWithJson(final String resourceName) throws IOException {

		final String json = Resources.toString(Resources.getResource(resourceName), Charsets.UTF_8);

		HttpPost httpPost = new HttpPost(RESOURCE_URI);
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setEntity(new StringEntity(json, "UTF-8"));

		return httpPost;
	}

}
