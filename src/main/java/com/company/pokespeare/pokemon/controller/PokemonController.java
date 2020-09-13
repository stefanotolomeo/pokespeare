package com.company.pokespeare.pokemon.controller;

import com.company.pokespeare.http.manager.HttpManager;
import com.company.pokespeare.http.model.BaseHttpRequest;
import com.company.pokespeare.http.model.BaseHttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@RequestMapping("/pokemon")
@RestController
public class PokemonController extends AbstractController {

	@Inject
	private HttpManager httpManager;

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
	public BaseHttpResponse getShakespearizedPokemonByName(
			@PathVariable("name")
					String name) {

		log.debug("Received GET request with pokemonName={}", name);

		try {
			// TODO: validate input string: null/empty
			String pokemonUriWithName = pokemonCompleteUri + name;
			BaseHttpRequest pokemonHttpRequest = new BaseHttpRequest(pokemonUriWithName);
			BaseHttpResponse pokemonHttpResponse = httpManager.makeGetRequest(pokemonHttpRequest);
			// TODO: implement logic here: In particular:
			//  (1) Call Pokemon API to get description and other info;
			//  (2) Call Shakespeare API to shakespearized the description
			//  (3) Create the ResponsePokemon and return

			return pokemonHttpResponse;
		} catch (Exception e) {
			log.error("Exception while making HTTP calls to remote service", e);
			// TODO: manage
			return new BaseHttpResponse(500, "Unexpected Internal Error");
		}

		/*
		ResponsePokemon responsePokemon = new ResponsePokemon(name);
		log.debug("Sending response to GET request with response={}", responsePokemon);
		return responsePokemon;
		*/
	}
}
