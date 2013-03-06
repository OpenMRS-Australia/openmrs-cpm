package org.openmrs.module.cpm.rest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

public class TestDictionaryManagerEndpoint {

	@Test
	public void submitLiveRestRequest_shouldntThrowAnyExceptions() {
		HttpHost targetHost = new HttpHost("localhost", 8080, "http");

		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
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
			final String json = "{\"name\":\"test\",\"email\":\"test@test.com\",\"description\":\"rar\"}";
			httpPost.setEntity(new StringEntity(json, "UTF-8"));

			System.out.println("executing request: " + httpPost.getRequestLine());
			System.out.println("to target: " + targetHost);

			HttpResponse response = httpclient.execute(targetHost, httpPost, localcontext);
			HttpEntity entity = response.getEntity();

			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (entity != null) {
				System.out.println("Response content length: " + entity.getContentLength());
			}
			EntityUtils.consume(entity);

		} catch (ClientProtocolException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}

	}
}
