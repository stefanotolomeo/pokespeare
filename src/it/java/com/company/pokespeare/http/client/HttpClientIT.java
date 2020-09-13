package com.company.pokespeare.http.client;

import com.company.pokespeare.config.BaseIT;
import com.company.pokespeare.http.model.BaseHttpRequest;
import com.company.pokespeare.http.model.BaseHttpResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.io.IOException;

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
		BaseHttpRequest httpRequest = new BaseHttpRequest(completeUri);
		BaseHttpResponse response = httpClient.makeGetRequest(httpRequest);

		Assertions.assertNotNull(response);
	}
}
