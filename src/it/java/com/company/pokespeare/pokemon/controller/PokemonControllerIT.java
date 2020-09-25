package com.company.pokespeare.pokemon.controller;

import com.company.pokespeare.itconfig.BaseIT;
import com.company.pokespeare.pokemon.model.Outcome;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;

class PokemonControllerIT extends BaseIT {

	@Inject
	private PokemonController pokemonController;

	@Test
	void getPokemonByName_Test(){
		String pokemonName = "ditto";
		ResponseEntity<Outcome> outcome = pokemonController.getPokemonByName(pokemonName);
		Assertions.assertNotNull(outcome);
		Assertions.assertNotNull(outcome.getBody());
		Assertions.assertNotNull(outcome.getBody().getDescription());

		String expectedDitto = "'t can freely recombine its own cellular structure to transform into other life-forms.";
		Assertions.assertEquals(expectedDitto, outcome.getBody().getDescription());
	}
}
