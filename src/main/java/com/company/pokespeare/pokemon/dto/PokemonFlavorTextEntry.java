package com.company.pokespeare.pokemon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PokemonFlavorTextEntry {

	@JsonProperty("flavor_text")
	private String flavorText;
	private PokemonLanguage language;
	private PokemonVersion version;

	public String getFlavorText() {
		return flavorText;
	}

	public void setFlavorText(String flavorText) {
		this.flavorText = flavorText;
	}

	public PokemonLanguage getLanguage() {
		return language;
	}

	public void setLanguage(PokemonLanguage language) {
		this.language = language;
	}

	public PokemonVersion getVersion() {
		return version;
	}

	public void setVersion(PokemonVersion version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "PokemonFlavorTextEntry{" + "flavorText='" + flavorText + '\'' + ", language=" + language + ", version=" + version + '}';
	}
}
