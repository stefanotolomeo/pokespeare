package com.company.pokespeare.pokemon.model;

public class ResponsePokemon extends AbstractPokemon {

	public ResponsePokemon(String name) {
		super(name);
	}

	@Override
	public String toString() {
		return "ResponsePokemon{" + "name='" + name + '\'' + '}';
	}
}
