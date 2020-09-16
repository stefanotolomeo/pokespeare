package com.company.pokespeare.pokemon.controller;

import com.company.pokespeare.http.manager.HttpManager;
import com.company.pokespeare.http.model.BaseHttpResponse;
import com.company.pokespeare.pokemon.logic.ResponseValidator;
import com.company.pokespeare.pokemon.model.PokemonRequest;
import com.company.pokespeare.pokemon.model.ShakespeareRequest;
import com.company.pokespeare.pokemon.model.ShakespeareResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RequestMapping("/pokemon")
@RestController
public class PokemonController extends AbstractController {

	@Inject
	private HttpManager httpManager;

	@Inject
	private ResponseValidator responseValidator;

	@Value("${http.pokemon.base}")
	private String pokemonBaseUri;

	@Value("${http.pokemon.api.pokemon.pokemon}")
	private String pokemonPath;

	@Value("${http.shakespeare.base}")
	private String shakespeareBaseUri;

	@Value("${http.shakespeare.api.shakespeare}")
	private String shakespearePath;

	private String pokemonCompleteUri;
	private String shakespeareCompleteUri;

	@PostConstruct
	public void postConstruct() {
		this.pokemonCompleteUri = (pokemonBaseUri + pokemonPath).trim();
		this.shakespeareCompleteUri = (shakespeareBaseUri + shakespearePath).trim();

		log.info("URIs built: PokemonURI={}, ShakespeareURI={}", pokemonCompleteUri, shakespeareCompleteUri);
	}

	@GetMapping("/{name}")
	public BaseHttpResponse getPokemonByName(
			@PathVariable("name")
					String name) {

		log.debug("Received GET request with pokemonName={}", name);

		try {
			// TODO: validate input string: null/empty
			String pokemonUriWithName = pokemonCompleteUri + name;
			PokemonRequest pokemonDescriptionRequest = new PokemonRequest(pokemonUriWithName);

			// Steps to be followed:
			//  (1) Call Pokemon-API
			//  (2) Parse response and read fields into "flavor_text_entries";
			//  (3) Build the request for the second API call;
			//  (2) Call Shakespeare-API to shakespearized the description;
			//  (3) Parse response, check and return

			CompletableFuture<ShakespeareResponse> outcome =
					httpManager.makeGetRequest(pokemonDescriptionRequest)
					.thenApply((resp_1 -> {
						responseValidator.validatePokemonResponse(resp_1);
						return new ShakespeareRequest(shakespeareCompleteUri, resp_1.getPayload());
					}))
					.thenCompose(el -> httpManager.makePostRequest(el))
					.thenApply(resp_2 -> {
						responseValidator.validateShakespeareResponse(resp_2);
						return new ShakespeareResponse(resp_2);
					})
					.exceptionally(e -> {
						log.error("Exception with at least one future", e);
						// TODO: change
						return new ShakespeareResponse(0, null);
					});

			// TODO: manage
			/*if (outcome.isCompletedExceptionally()){
				throw new Exception("Future completed with Exception", outcome.get().toString());
			}*/

			return outcome.get(10, TimeUnit.SECONDS);


		} catch (Exception e) {
			log.error("Exception while making HTTP calls to remote service", e);
			// TODO: manage
			return new BaseHttpResponse(500, "Unexpected Internal Error");
		}
	}
}
