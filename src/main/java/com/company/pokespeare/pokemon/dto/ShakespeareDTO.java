package com.company.pokespeare.pokemon.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShakespeareDTO {

	// JSON RESPONSE EXAMPLE
	/*
	{
		"success": {
			"total": 1
		},
		"contents": {
			"translated": "Thee did giveth mr. Tim a hearty meal,  but unfortunately what he did doth englut did maketh him kicketh the bucket.",
			"text": "You gave Mr. Tim a hearty meal, but unfortunately what he ate made him die.",
			"translation": "shakespeare"
		}
	}
	*/

	private ShakespeareSuccess success;
	private ShakespeareContent contents;

	public ShakespeareSuccess getSuccess() {
		return success;
	}

	public void setSuccess(ShakespeareSuccess success) {
		this.success = success;
	}

	public ShakespeareContent getContents() {
		return contents;
	}

	public void setContents(ShakespeareContent contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "ShakespeareDTO{" + "success=" + success + ", contents=" + contents + '}';
	}
}