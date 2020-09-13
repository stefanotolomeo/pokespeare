package com.company.pokespeare.http.client;

import com.company.pokespeare.http.model.BaseHttpRequest;
import com.company.pokespeare.http.model.BaseHttpResponse;

public interface IHttpClient {

	BaseHttpResponse makeGetRequest(BaseHttpRequest httpRequest) throws Exception;

	BaseHttpResponse makePostRequest(BaseHttpRequest httpRequest) throws Exception;
}
