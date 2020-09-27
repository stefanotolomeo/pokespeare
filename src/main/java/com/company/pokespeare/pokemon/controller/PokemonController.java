package com.company.pokespeare.pokemon.controller;

import com.company.pokespeare.config.Constants;
import com.company.pokespeare.http.manager.HttpManager;
import com.company.pokespeare.pokemon.cache.VisitedCache;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RequestMapping(Constants.POKEMON_URL)
@RestController
public class PokemonController extends AbstractController {

	@Inject
	private VisitedCache visitedCache;

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

	@GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Outcome> getPokemonByName(
			@PathVariable("name")
					String name) {

		log.debug("Received GET request with pokemonName={}", name);

		Outcome outcome;
		try {
			Preconditions.checkArgument(name != null && !name.isEmpty(), "Invalid input: null or empty");

			if(visitedCache.get(name).isPresent()){
				log.info("Pokemon={} already in cache. Returning translation from cache", name);
				return ResponseEntity.status(200).body(Outcome.ok(name, visitedCache.get(name).get()));
			}

			PokemonRequest req_1 = new PokemonRequest(pokemonCompleteUri + name);

			//@formatter:off
			CompletableFuture<Outcome> futureOutcome =
					//  (1) Call Pokemon-API
					httpManager.makeGetRequest(req_1).thenApply((resp_1 -> {
						//  (2) Validate/Parse response and build the request for the second API call
						PokemonDTO pokemonDTO = responseValidator.validatePokemonResponse(resp_1);
						String selectedDescription = responseSelector.selectPokemonDescription(pokemonDTO);
						return new ShakespeareRequest(shakespeareCompleteUri, selectedDescription);
					}))
					//  (3) Call Shakespeare-API to shakespearized the description;
					.thenCompose(req_2 -> httpManager.makeGetRequest(req_2)).thenApply(resp_2 -> {
						//  (4) Validate/Parse response and create the overallOutcome
						ShakespeareDTO shakespeareDTO = responseValidator.validateShakespeareResponse(resp_2);
						String translation = responseSelector.selectShakespeareTranslation(shakespeareDTO);
						// (5) Put response in cache
						visitedCache.put(name, translation);
						return Outcome.ok(name, translation);
					}).exceptionally(e -> {
						log.error("Exception with at least one future", e);
						return buildOutcomeForException(e, name);
					});
			//@formatter:on

			outcome = futureOutcome.get(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			log.error("Exception while making HTTP calls to remote service", e);
			String msg = "Unexpected Internal Error";

			outcome = Outcome.notOk(name, 500, msg);
		}

		return ResponseEntity.status(outcome.getStatusCode()).body(outcome);

	}

	private Outcome buildOutcomeForException(Throwable e, String pokemonName) {
		String msg = "Error while processing request";
		int statusCode = 500;
		if (e.getCause() != null && e.getCause() instanceof ResponseValidationException) {
			ResponseValidationException castedExp = (ResponseValidationException) e.getCause();
			statusCode = 502;
			switch (castedExp.getResponseType()) {
			case POKEMON:
				msg = "Error while getting Pokemon info. Ensure pokemon name is correct";
				break;
			case SHAKESPEARE:
				msg = "Error while getting Shakespeare translation. Bad response (consider max attempts per hour is 5)";
				break;
			}
		}
		return Outcome.notOk(pokemonName, statusCode, msg);
	}
}
