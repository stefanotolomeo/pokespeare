package com.company.pokespeare.http.manager;

import com.company.pokespeare.http.client.HttpClient;
import com.company.pokespeare.http.model.BaseHttpRequest;
import com.company.pokespeare.http.model.BaseHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HttpManager {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private HttpClient httpClient;

	public HttpManager(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public BaseHttpResponse makeGetRequest(BaseHttpRequest httpRequest) {
		try {
			log.trace("Making GET request..");
			return httpClient.makeGetRequest(httpRequest);
		} catch (Exception e) {
			// TODO: manage the response
			log.error("Exception while making GET request", e);
			// BaseHttpResponse response = new BaseHttpResponse()
			return null;
		}
	}

}
