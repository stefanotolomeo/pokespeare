package com.company.pokespeare.http.manager;

import com.company.pokespeare.http.client.HttpClient;
import com.company.pokespeare.http.logic.RequestConverter;
import com.company.pokespeare.http.model.BaseHttpResponse;
import com.company.pokespeare.http.model.GetRequest;
import com.company.pokespeare.http.model.PostRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

@Service
public class HttpManager {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final static int INTERNAL_ERROR_CODE = 500;
	private final static String INTERNAL_ERROR_MESSAGE = "Unexpected error during request";

	@Inject
	private HttpClient httpClient;

	@Inject
	private RequestConverter requestConverter;

	// TODO: initialize thread pool
	@Async("requestThreadPool")
	public CompletableFuture<BaseHttpResponse> makeGetRequest(GetRequest request) {
		BaseHttpResponse response;

		try {
			log.trace("Making GET request..");
			HttpGet httpGetRequest = requestConverter.convertGetRequest(request);
			response = httpClient.makeGetRequest(httpGetRequest);

		} catch (Exception e) {
			log.error("Exception while making GET request", e);
			response = new BaseHttpResponse(INTERNAL_ERROR_CODE, INTERNAL_ERROR_MESSAGE);
		}
		return CompletableFuture.completedFuture(response);
	}

	@Async("requestThreadPool")
	public CompletableFuture<BaseHttpResponse> makePostRequest(PostRequest request) {
		BaseHttpResponse response;
		try {
			log.trace("Making POST request..");

			HttpPost httpPostRequest = requestConverter.convertPostRequest(request);
			response = httpClient.makePostRequest(httpPostRequest);

		} catch (Exception e) {
			log.error("Exception while making POST request", e);
			response = new BaseHttpResponse(INTERNAL_ERROR_CODE, INTERNAL_ERROR_MESSAGE);
		}

		return CompletableFuture.completedFuture(response);
	}

}
