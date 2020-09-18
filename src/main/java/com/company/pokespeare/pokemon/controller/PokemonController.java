package com.company.pokespeare.pokemon.controller;

import com.company.pokespeare.http.manager.HttpManager;
import com.company.pokespeare.pokemon.dto.PokemonDTO;
import com.company.pokespeare.pokemon.dto.ShakespeareDTO;
import com.company.pokespeare.pokemon.logic.PokemonDescriptionSelector;
import com.company.pokespeare.pokemon.logic.ResponseValidator;
import com.company.pokespeare.pokemon.logic.ShakespeareTranslationSelector;
import com.company.pokespeare.pokemon.model.Outcome;
import com.company.pokespeare.pokemon.model.PokemonRequest;
import com.company.pokespeare.pokemon.model.ShakespeareRequest;
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
	private PokemonDescriptionSelector pokemonDescriptionSelector;

	@Inject
	private ShakespeareTranslationSelector shakespeareTranslationSelector;

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
			// TODO: validate input string: null/empty
			String pokemonUriWithName = pokemonCompleteUri + name;
			PokemonRequest req_1 = new PokemonRequest(pokemonUriWithName);

			// Steps to be followed:
			//  (1) Call Pokemon-API
			//  (2) Parse response and read fields into "flavor_text_entries";
			//  (3) Build the request for the second API call;
			//  (2) Call Shakespeare-API to shakespearized the description;
			//  (3) Parse response, check and return

			CompletableFuture<Outcome> outcome =
					httpManager.makeGetRequest(req_1)
					.thenApply((resp_1 -> {
						PokemonDTO pokemonDTO = responseValidator.validatePokemonResponse(resp_1);
						String selectedDescription = pokemonDescriptionSelector.selectDescription(pokemonDTO);
						return new ShakespeareRequest(shakespeareCompleteUri, selectedDescription);
					}))
					.thenCompose(req_2 -> httpManager.makePostRequest(req_2))
					.thenApply(resp_2 -> {
						ShakespeareDTO shakespeareDTO = responseValidator.validateShakespeareResponse(resp_2);
						String translation = shakespeareTranslationSelector.selectTranslation(shakespeareDTO);
						return Outcome.ok(name, translation);
					})
					.exceptionally(e -> {
						log.error("Exception with at least one future", e);
						// TODO: change
						String msg = "Error while processing request";
						return Outcome.notOk(name, Collections.singletonList(msg));
					});

			// TODO: manage
			/*if (outcome.isCompletedExceptionally()){
				throw new Exception("Future completed with Exception", outcome.get().toString());
			}*/

			return outcome.get(10, TimeUnit.SECONDS);


		} catch (Exception e) {
			log.error("Exception while making HTTP calls to remote service", e);
			// TODO: manage
			String msg = "Unexpected Internal Error";

			return Outcome.notOk(name, Collections.singletonList(msg));
		}
	}
}
