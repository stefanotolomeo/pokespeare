package com.company.pokespeare.itconfig;

import com.company.pokespeare.http.client.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// @Import({ HttpConfig.class })
public class ITContext {

	@Bean
	public HttpClient httpClient() {
		return new HttpClient();
	}
}
