package com.company.pokespeare.itconfig;

import com.company.pokespeare.config.HttpConfig;
import com.company.pokespeare.config.WebConfig;
import com.company.pokespeare.http.client.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ WebConfig.class, HttpConfig.class })
public class ITContext {

}
