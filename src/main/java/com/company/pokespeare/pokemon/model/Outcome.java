package com.company.pokespeare.pokemon.model;

import java.util.Collections;
import java.util.List;

public class Outcome {

	private String status;    // OK, NOT_OK
	private String name;    // Pokemon Name
	private String translation;    // Shakespeare Translation
	private List<String> errorMessages;    // Populated only if status = NOT_OK

	private Outcome(String status, String name, String translation, List<String> errorMessages) {
		this.status = status;
		this.name = name;
		this.translation = translation;
		this.errorMessages = errorMessages;
	}

	public static Outcome ok(String pokemonName, String translation) {
		return new Outcome("OK", pokemonName, translation, Collections.emptyList());
	}

	public static Outcome notOk(String pokemonName, List<String> errorMessages) {
		return new Outcome("NOT OK", pokemonName, null, errorMessages);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	@Override
	public String toString() {
		return "Outcome{" + "status='" + status + '\'' + ", name='" + name + '\'' + ", translation='" + translation + '\''
				+ ", errorMessages=" + errorMessages + '}';
	}
}
