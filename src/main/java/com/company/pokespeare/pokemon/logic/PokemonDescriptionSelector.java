package com.company.pokespeare.pokemon.logic;

import com.company.pokespeare.pokemon.dto.PokemonDTO;
import com.company.pokespeare.pokemon.dto.PokemonFlavorTextEntry;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PokemonDescriptionSelector {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	private static final String ENGLISH = "en";

	// TODO: implement your own logic to select the proper description base on GameVersion.
	// Here it is simply considered the first description and ignored the game version
	public String selectDescription(PokemonDTO pokemonDTO){

		log.debug("Selecting description for input: {}", pokemonDTO);
		Preconditions.checkNotNull(pokemonDTO, "PokemonDT0 is null");
		Preconditions.checkNotNull(pokemonDTO.getFlavorTextEntries(), "FlavorTextEntries is null");

		try{
			//@formatter:off
			return pokemonDTO.getFlavorTextEntries().stream()
					.filter(el -> el.getLanguage().getName().equals(ENGLISH))
					.map(PokemonFlavorTextEntry::getFlavorText)
					.findFirst()
					.get();
			//@formatter:on
		} catch (Exception e){
			throw new RuntimeException("Cannot select a pokemon description from PokemonDTO", e);
		}



	}
}
