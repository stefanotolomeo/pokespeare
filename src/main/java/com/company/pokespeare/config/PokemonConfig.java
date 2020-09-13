package com.company.pokespeare.config;

import com.company.pokespeare.http.client.HttpClient;
import com.company.pokespeare.http.manager.HttpManager;
import com.company.pokespeare.pokemon.controller.PokemonController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = { PokemonController.class, HttpClient.class, HttpManager.class })
public class PokemonConfig {

}