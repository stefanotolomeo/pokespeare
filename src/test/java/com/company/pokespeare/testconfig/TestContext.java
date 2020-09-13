package com.company.pokespeare.testconfig;

import com.company.pokespeare.config.HttpConfig;
import com.company.pokespeare.pokemon.controller.PokemonController;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@ComponentScan(basePackageClasses = { })
@Import({ HttpConfig.class })
public class TestContext {

	@MockBean
	public PokemonController pokemonController;
}
