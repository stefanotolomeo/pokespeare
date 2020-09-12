package com.company.pokespeare.pokemon.controller;

import com.company.pokespeare.config.AbstractLogger;
import com.company.pokespeare.pokemon.model.RequestPokemon;
import com.company.pokespeare.pokemon.model.ResponsePokemon;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class PokemonController extends AbstractLogger {

	@GetMapping("/pokemon/{name}")
	public ResponsePokemon getShakespearizedPokemonByName(@PathVariable("name")	String name) {

		log.debug("Received GET request with pokemonName={}", name);
		RequestPokemon requestPokemon = new RequestPokemon(name);

		// TODO: implement logic here: In particular:
		//  (1) Call Pokemon API to get description and other info;
		//  (2) Call Shakespeare API to shakespearized the description
		//  (3) Create the ResponsePokemon and return

		ResponsePokemon responsePokemon = new ResponsePokemon(name);
		log.debug("Sending response to GET request with response={}", responsePokemon);
		return responsePokemon;
	}
}
