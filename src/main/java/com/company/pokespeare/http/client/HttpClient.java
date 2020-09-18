package com.company.pokespeare.http.client;

import com.company.pokespeare.http.model.BaseHttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class HttpClient implements IHttpClient {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public BaseHttpResponse makeGetRequest(HttpGet request) throws Exception {
		log.debug("GET-Request: start with request={}", request);

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(request)) {

			// TODO: check response fields (e.g. null)
			int statusCode = response.getStatusLine().getStatusCode();
			String payload = EntityUtils.toString(response.getEntity());

			// Get HttpResponse Status
			log.debug("GET-Request: Received response with Status={} and Payload={}", statusCode, payload);

			return new BaseHttpResponse(statusCode, payload);
		}
	}

	@Override
	public BaseHttpResponse makePostRequest(HttpPost request) throws IOException {
		log.debug("POST-Request: start with request={}", request);

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(request)) {

			// TODO: check response fields (e.g. null)
			int statusCode = response.getStatusLine().getStatusCode();
			String payload = EntityUtils.toString(response.getEntity());

			// Get HttpResponse Status
			log.debug("POST-Request: Received response with Status={} and Payload={}", statusCode, payload);

			return new BaseHttpResponse(statusCode, payload);
		}

	}
}