package com.company.pokespeare.pokemon.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonDTO extends AbstractDTO {

	@JsonProperty("flavor_text_entries")
	private List<PokemonFlavorTextEntry> flavorTextEntries;

	public List<PokemonFlavorTextEntry> getFlavorTextEntries() {
		return flavorTextEntries;
	}

	public void setFlavorTextEntries(List<PokemonFlavorTextEntry> flavorTextEntries) {
		this.flavorTextEntries = flavorTextEntries;
	}

	@Override
	public String toString() {
		return "PokemonDTO{" + "flavorTextEntries=" + flavorTextEntries + '}';
	}
}
