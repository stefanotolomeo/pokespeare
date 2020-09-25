package com.company.pokespeare.config;

import com.company.pokespeare.pokemon.cache.VisitedCache;
import com.company.pokespeare.pokemon.controller.MainController;
import com.company.pokespeare.pokemon.logic.ResponseValidator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackageClasses = { MainController.class, ResponseValidator.class, VisitedCache.class })
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

}