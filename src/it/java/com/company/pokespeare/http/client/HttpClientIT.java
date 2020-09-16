package com.company.pokespeare.http.client;

import com.company.pokespeare.http.model.BaseHttpResponse;
import com.company.pokespeare.itconfig.BaseIT;
import org.apache.http.client.methods.HttpGet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

// Manual test
// @Disabled
class HttpClientIT extends BaseIT {

	@Inject
	private HttpClient httpClient;

	private final String pokemonUri = "https://pokeapi.co/api/v2/pokemon/";

	@Test
	void makeGetRequest_Test() throws Exception {
		String pokemonDitto = "ditto";
		String completeUri = pokemonUri + pokemonDitto;
		HttpGet httpGet = new HttpGet(completeUri);
		BaseHttpResponse response = httpClient.makeGetRequest(httpGet);

		Assertions.assertNotNull(response);
	}
}
