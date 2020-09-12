package com.company.pokespeare.pokemon.model;

public class RequestPokemon extends AbstractPokemon{

	public RequestPokemon(String name) {
		super(name);
	}

	@Override
	public String toString() {
		return "RequestPokemon{" + "name='" + name + '\'' + '}';
	}
}
