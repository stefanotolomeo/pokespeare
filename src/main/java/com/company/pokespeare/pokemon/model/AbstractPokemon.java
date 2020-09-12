package com.company.pokespeare.pokemon.model;

public abstract class AbstractPokemon {

	protected String name;

	public AbstractPokemon(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
