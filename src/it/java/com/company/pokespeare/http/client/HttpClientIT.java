package com.company.pokespeare.http.client;

import com.company.pokespeare.http.model.BaseHttpResponse;
import com.company.pokespeare.itconfig.BaseIT;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Collections;

// Manual test
@Disabled
class HttpClientIT extends BaseIT {

	@Inject
	private HttpClient httpClient;

	// TODO: read from application-it.properties
	private final String pokemonUri = "https://pokeapi.co/api/v2/pokemon/";
	private final String shakespeareUri = "https://api.funtranslations.com/translate/shakespeare/";

	@Test
	void makeGetRequest_Test() throws Exception {
		String pokemonDitto = "ditto";
		String completeUri = pokemonUri + pokemonDitto;
		HttpGet httpGet = new HttpGet(completeUri);
		BaseHttpResponse response = httpClient.makeGetRequest(httpGet);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(200, response.getStatusCode());
		Assertions.assertNotNull(response.getPayload());
	}

	@Test
	void makePostRequest_Test() throws Exception {
		String textName = "text";
		String textValue = "You gave Mr. Tim a hearty meal, but unfortunately what he ate made him die.";
		HttpPost httpPost = new HttpPost(shakespeareUri);
		BasicNameValuePair pair = new BasicNameValuePair(textName, textValue);
		httpPost.setEntity(new UrlEncodedFormEntity(Collections.singleton(pair)));

		BaseHttpResponse response = httpClient.makePostRequest(httpPost);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(200, response.getStatusCode());
		Assertions.assertNotNull(response.getPayload());
	}
}
