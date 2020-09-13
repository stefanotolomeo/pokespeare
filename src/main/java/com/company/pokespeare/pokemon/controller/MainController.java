package com.company.pokespeare.pokemon.controller;

import com.company.pokespeare.config.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Mapping for "/"
@RequestMapping(Constants.BASE_URL)
@RestController
public class MainController extends AbstractController {

	@GetMapping
	public String getWelcome() {
		log.debug("Requesting Welcome");
		return Constants.WELCOME_MSG;
	}
}
