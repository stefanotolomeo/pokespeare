package com.company.pokespeare.http.client;

import com.company.pokespeare.http.model.BaseHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

public interface IHttpClient {

	BaseHttpResponse makeGetRequest(HttpGet request) throws Exception;

	BaseHttpResponse makePostRequest(HttpPost request) throws Exception;
}
