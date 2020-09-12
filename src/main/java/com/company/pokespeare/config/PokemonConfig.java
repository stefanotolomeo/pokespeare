package com.company.pokespeare.config;

import com.company.pokespeare.pokemon.controller.PokemonController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = { PokemonController.class })
public class PokemonConfig {

}