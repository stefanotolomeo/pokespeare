package com.company.pokespeare.pokemon.logic;

import com.company.pokespeare.pokemon.dto.PokemonDTO;
import com.company.pokespeare.pokemon.dto.PokemonFlavorTextEntry;
import com.company.pokespeare.pokemon.dto.ShakespeareDTO;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ResponseSelector {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	private static final String ENGLISH = "en";

	// TODO: implement your own logic to select the proper description base on GameVersion.
	//  Here it is simply considered the first description and ignored the game version
	public String selectPokemonDescription(PokemonDTO pokemonDTO) {

		log.debug("Selecting description for PokemonDTO");

		try {
			Preconditions.checkArgument(pokemonDTO != null && pokemonDTO.getFlavorTextEntries() != null, "PokemonDTO is null");

			//@formatter:off
			String selectedDescription = pokemonDTO.getFlavorTextEntries().stream()
					.filter(el -> el.getLanguage().getName().equals(ENGLISH))
					.map(PokemonFlavorTextEntry::getFlavorText)
					.findFirst()
					.get();
			//@formatter:on

			log.debug("Selected description is: {}", selectedDescription);
			return selectedDescription;
		} catch (Exception e) {
			throw new RuntimeException("Cannot select a pokemon description from PokemonDTO", e);
		}

	}

	public String selectShakespeareTranslation(ShakespeareDTO shakespeareDTO) {
		log.debug("Selecting translation for ShakespeareDTO");
		try {
			Preconditions.checkArgument(
					shakespeareDTO != null && shakespeareDTO.getContents() != null && shakespeareDTO.getContents().getTranslated() != null,
					"ShakespeareDTO is null");
			Preconditions.checkArgument(!shakespeareDTO.getContents().getTranslated().isEmpty(), "Empty translation received");
			return shakespeareDTO.getContents().getTranslated();
		} catch (Exception e) {
			throw new RuntimeException("Cannot select a shakespeare translation from ShakespeareDTO", e);
		}
	}
}
