package com.company.pokespeare.config;

import com.company.pokespeare.http.client.HttpClient;
import com.company.pokespeare.http.logic.RequestConverter;
import com.company.pokespeare.http.manager.HttpManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = { HttpClient.class, HttpManager.class, RequestConverter.class })
public class HttpConfig {

}