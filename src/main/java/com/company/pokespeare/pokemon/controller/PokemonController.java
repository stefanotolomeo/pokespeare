package com.company.pokespeare.pokemon.controller;

import com.company.pokespeare.http.manager.HttpManager;
import com.company.pokespeare.pokemon.dto.PokemonDTO;
import com.company.pokespeare.pokemon.dto.ShakespeareDTO;
import com.company.pokespeare.pokemon.exception.ResponseValidationException;
import com.company.pokespeare.pokemon.logic.ResponseSelector;
import com.company.pokespeare.pokemon.logic.ResponseValidator;
import com.company.pokespeare.pokemon.model.Outcome;
import com.company.pokespeare.pokemon.model.PokemonRequest;
import com.company.pokespeare.pokemon.model.ShakespeareRequest;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RequestMapping("/pokemon")
@RestController
public class PokemonController extends AbstractController {

	@Inject
	private HttpManager httpManager;

	@Inject
	private ResponseValidator responseValidator;

	@Inject
	private ResponseSelector responseSelector;

	@Value("${http.pokemon.base}")
	private String pokemonBaseUri;

	@Value("${http.pokemon.api.pokemon.pokemon_species}")
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

	// TODO: use ResponseEntity<String> to set HEADER in response
	@GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Outcome getPokemonByName(
			@PathVariable("name")
					String name) {

		log.debug("Received GET request with pokemonName={}", name);

		try {
			Preconditions.checkNotNull(name, "Input is null");
			Preconditions.checkArgument(!name.isEmpty(), "Input is empty");
			String pokemonUriWithName = pokemonCompleteUri + name;
			PokemonRequest req_1 = new PokemonRequest(pokemonUriWithName);

			CompletableFuture<Outcome> outcome =
					//  (1) Call Pokemon-API
					httpManager.makeGetRequest(req_1)
					.thenApply((resp_1 -> {
						//  (2) Validate/Parse response and build the request for the second API call
						PokemonDTO pokemonDTO = responseValidator.validatePokemonResponse(resp_1);
						String selectedDescription = responseSelector.selectPokemonDescription(pokemonDTO);
						return new ShakespeareRequest(shakespeareCompleteUri, selectedDescription);
					}))
					//  (3) Call Shakespeare-API to shakespearized the description;
					.thenCompose(req_2 -> httpManager.makePostRequest(req_2))
					.thenApply(resp_2 -> {
						//  (4) Validate/Parse response and create the outcome
						ShakespeareDTO shakespeareDTO = responseValidator.validateShakespeareResponse(resp_2);
						String translation = responseSelector.selectShakespeareTranslation(shakespeareDTO);
						return Outcome.ok(name, translation);
					})
					.exceptionally(e -> {
						log.error("Exception with at least one future", e);
						String msg = "Error while processing request";
						if(e.getCause()!= null && e.getCause() instanceof ResponseValidationException){
							ResponseValidationException castedExp = (ResponseValidationException) e.getCause();
							switch (castedExp.getResponseType()){
							case POKEMON:
								msg = "Error while getting Pokemon info. Ensure pokemon name is correct";
								break;
							case SHAKESPEARE:
								msg = "Error while getting Shakespeare translation. Max attempts per hour is 5";
								break;
							}
						}
						return Outcome.notOk(name, Collections.singletonList(msg));
					});

			return outcome.get(10, TimeUnit.SECONDS);


		} catch (Exception e) {
			log.error("Exception while making HTTP calls to remote service", e);
			String msg = "Unexpected Internal Error";

			return Outcome.notOk(name, Collections.singletonList(msg));
		}
	}
}
