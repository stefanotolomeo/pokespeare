package com.company.pokespeare.http.logic;

import com.company.pokespeare.http.exception.InvalidRequestException;
import com.company.pokespeare.http.model.GetRequest;
import com.company.pokespeare.http.model.PostRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestConverter {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public HttpGet convertGetRequest(GetRequest getRequest) throws InvalidRequestException {
		try {
			HttpGet httpGet = new HttpGet(getRequest.getUrl());

			if (getRequest.getParams() != null) {
				URIBuilder uriBuilder = new URIBuilder(httpGet.getURI());

				getRequest.getParams().forEach(uriBuilder::addParameter);
				URI uri = uriBuilder.build();

				httpGet.setURI(uri);
			}

			httpGet.setHeader("Accept", "application/json");
			httpGet.addHeader("Accept-Language", "en-gb");
			httpGet.addHeader("Content-Type", "application/json; charset=utf-8");
			// Eventually set other headers if needed (e.g. user-agent)

			return httpGet;
		} catch (Exception e) {
			throw new InvalidRequestException("Exception while converting GET request", e);
		}
	}

	public HttpPost convertPostRequest(PostRequest postRequest) throws InvalidRequestException {
		try {
			HttpPost httpPost = new HttpPost(postRequest.getUrl());
			httpPost.setHeader("Accept", "application/json");
			httpPost.addHeader("Accept-Language", "en-gb");
			// Eventually set other headers if needed (e.g. user-agent)

			//@formatter:off
			List<NameValuePair> urlParameters =  postRequest.getParams().entrySet().stream()
				.map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
			//@formatter:on

			httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));

			return httpPost;
		} catch (Exception e) {
			throw new InvalidRequestException("Exception while converting POST request", e);
		}
	}

}
