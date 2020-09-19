package com.company.pokespeare.pokemon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

// NON_EMPTY rule covers NON_NULL too
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Outcome {

	@JsonIgnore
	private int statusCode;    // 200, 400, 502
	private String name;    // Pokemon Name
	private String description;    // Shakespeare Translation
	private String error;    // Populated only if status = NOT_OK

	private Outcome(int statusCode, String name, String description, String error) {
		this.statusCode = statusCode;
		this.name = name;
		this.description = description;
		this.error = error;
	}

	public static Outcome ok(String pokemonName, String description) {
		return new Outcome(200, pokemonName, description, null);
	}

	public static Outcome notOk(String pokemonName, int statusCode, String errorMessage) {
		return new Outcome(statusCode, pokemonName, null, errorMessage);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getError() {
		return error;
	}

	@Override
	public String toString() {
		return "Outcome{" + "statusCode=" + statusCode + ", name='" + name + '\'' + ", description='" + description + '\'' + ", error="
				+ error + '}';
	}
}
