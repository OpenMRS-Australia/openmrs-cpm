package org.openmrs.module.cpm.rest;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class TestDictionaryManagerEndpoint {


	@Test
	@Ignore // not yet implemented
	public void submitProposalWithNoAuth_shouldReceived401() throws IOException {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpHost targetHost = new HttpHost("localhost", 8080, "http");

		HttpPost httpPost = new HttpPost("http://localhost:8080/openmrs/ws/cpm/dictionarymanager/proposals");
		httpPost.setHeader("Content-Type", "application/json");

		final String json = Resources.toString(Resources.getResource("proposal-no-concepts.json"), Charsets.UTF_8);
		httpPost.setEntity(new StringEntity(json, "UTF-8"));

		HttpResponse response = httpclient.execute(targetHost, httpPost);
		HttpEntity responseEntity = response.getEntity();

		assertThat(response.getStatusLine().getStatusCode(), equalTo(401));
	}

	@Test
	public void submitProposalNoConcepts_shouldReceive200() throws IOException {

		HttpHost targetHost = new HttpHost("localhost", 8080, "http");

		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getCredentialsProvider().setCredentials(
				new AuthScope(targetHost.getHostName(), targetHost.getPort()),
				new UsernamePasswordCredentials("admin", "Admin123"));

		// Create AuthCache instance
		AuthCache authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local
		// auth cache
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(targetHost, basicAuth);

		// Add AuthCache to the execution context
		BasicHttpContext localcontext = new BasicHttpContext();
		localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);

		HttpPost httpPost = new HttpPost("http://localhost:8080/openmrs/ws/cpm/dictionarymanager/proposals");
		httpPost.setHeader("Content-Type", "application/json");

		final String json = Resources.toString(Resources.getResource("proposal-no-concepts.json"), Charsets.UTF_8);
		httpPost.setEntity(new StringEntity(json, "UTF-8"));

		HttpResponse response = httpclient.execute(targetHost, httpPost, localcontext);
		HttpEntity responseEntity = response.getEntity();

		assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
	}

	@Test
	@Ignore // not yet implemented
	public void submitProposalSingleConcept_shouldReceive200() throws IOException {

		HttpHost targetHost = new HttpHost("localhost", 8080, "http");

		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getCredentialsProvider().setCredentials(
				new AuthScope(targetHost.getHostName(), targetHost.getPort()),
				new UsernamePasswordCredentials("admin", "Admin123"));

		// Create AuthCache instance
		AuthCache authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local
		// auth cache
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(targetHost, basicAuth);

		// Add AuthCache to the execution context
		BasicHttpContext localcontext = new BasicHttpContext();
		localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);

		HttpPost httpPost = new HttpPost("http://localhost:8080/openmrs/ws/cpm/dictionarymanager/proposals");
		httpPost.setHeader("Content-Type", "application/json");

		final String json = Resources.toString(Resources.getResource("proposal-with-concepts.json"), Charsets.UTF_8);
		httpPost.setEntity(new StringEntity(json, "UTF-8"));

		HttpResponse response = httpclient.execute(targetHost, httpPost, localcontext);
		HttpEntity responseEntity = response.getEntity();

		assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
	}
}
