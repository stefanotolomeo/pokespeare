package com.company.pokespeare.http.logic;

import com.company.pokespeare.http.exception.InvalidRequestException;
import com.company.pokespeare.http.model.GetRequest;
import com.company.pokespeare.http.model.PostRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestConverter {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public HttpGet convertGetRequest(GetRequest getRequest) throws InvalidRequestException {
		try {
			// TODO: validate input
			HttpGet convertedRequest = new HttpGet(getRequest.getUrl());

			// TODO: set headers
			return convertedRequest;
		} catch (Exception e) {
			throw new InvalidRequestException("Exception while converting GET request", e);
		}
	}

	public HttpPost convertPostRequest(PostRequest postRequest) throws InvalidRequestException {
		try {
			// TODO: validate input
			HttpPost convertedRequest = new HttpPost(postRequest.getUrl());

			// TODO: set headers
			//@formatter:off
			List<NameValuePair> urlParameters =  postRequest.getParams().entrySet().stream()
				.map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
			//@formatter:on

			convertedRequest.setEntity(new UrlEncodedFormEntity(urlParameters));

			return convertedRequest;
		} catch (Exception e) {
			throw new InvalidRequestException("Exception while converting POST request", e);
		}
	}
}
