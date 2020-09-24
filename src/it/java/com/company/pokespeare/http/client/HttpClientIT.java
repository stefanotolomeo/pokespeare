package com.company.pokespeare.http.client;

import com.company.pokespeare.http.model.BaseHttpResponse;
import com.company.pokespeare.itconfig.BaseIT;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.URI;
import java.util.Collections;

// Manual test with real remote services.
// Disabled by default, if needed:
// 		(1) Comment @Disable
// 		(2) Change host into application-it.yml (from localhost to remote hostname)
//@Disabled
class HttpClientIT extends BaseIT {

	@Inject
	private HttpClient httpClient;

	@Value("${http.pokemon.base}")
	private String pokemonBaseUri;

	@Value("${http.pokemon.api.pokemon.pokemon_species}")
	private String pokemonPath;

	@Value("${http.shakespeare.base}")
	private String shakespeareBaseUri;

	@Value("${http.shakespeare.api.shakespeare}")
	private String shakespearePath;

	private String pokemonUri;
	private String shakespeareUri;

	@PostConstruct
	public void postConstruct() {
		this.pokemonUri = (pokemonBaseUri + pokemonPath).trim();
		this.shakespeareUri = (shakespeareBaseUri + shakespearePath).trim();
	}

	@Test
	void makeGetRequest_Pokemon_Test() throws Exception {
		String pokemonDitto = "ditto";
		String completeUri = pokemonUri + pokemonDitto;
		HttpGet httpGet = new HttpGet(completeUri);
		BaseHttpResponse response = httpClient.makeGetRequest(httpGet);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(200, response.getStatusCode());
		Assertions.assertNotNull(response.getPayload());
	}

	@Test
	void makeGetRequest_Shakespeare_Test() throws Exception {
		String textName = "text";
		// String textValue = "You gave Mr. Tim a hearty meal, but unfortunately what he ate made him die.";
		// String textValue = "Spits fire that is hot enough to melt boulders. Known to cause forest fires unintentionally.";
		// String textValue = "When several of these POKéMON gather, their electricity could build and cause lightning storms.";
		String textValue = "It can freely recombine its own cellular structure to transform into other life-forms.";
		HttpGet httpGet = new HttpGet(shakespeareUri);
		URI uri = new URIBuilder(httpGet.getURI()).addParameter(textName, textValue).build();
		httpGet.setURI(uri);

		BaseHttpResponse response = httpClient.makeGetRequest(httpGet);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(200, response.getStatusCode());
		Assertions.assertNotNull(response.getPayload());
	}

	@Test
	void makePostRequest_Shakespeare_Test() throws Exception {
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
