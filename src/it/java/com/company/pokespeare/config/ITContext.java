package com.company.pokespeare.config;

import com.company.pokespeare.http.client.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// @Import({ PokemonConfig.class })
public class ITContext {

	@Bean
	public HttpClient httpClient() {
		return new HttpClient();
	}
}
