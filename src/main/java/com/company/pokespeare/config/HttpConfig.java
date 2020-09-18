package com.company.pokespeare.config;

import com.company.pokespeare.http.client.HttpClient;
import com.company.pokespeare.http.logic.RequestConverter;
import com.company.pokespeare.http.manager.HttpManager;
import com.company.pokespeare.pokemon.logic.ResponseValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan(basePackageClasses = { HttpClient.class, HttpManager.class, RequestConverter.class, ResponseValidator.class })
public class HttpConfig {

	@Value("${request.threadpool.corepoolsize}")
	private int requestCorePoolSize;

	@Value("${request.threadpool.maxpoolsize}")
	private int requestMaxPoolSize;

	@Value("${request.threadpool.threadpriority}")
	private int requestThreadPriority;

	@Bean(name = "requestThreadPool")
	public ThreadPoolTaskExecutor requestTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(requestCorePoolSize);
		executor.setMaxPoolSize(requestMaxPoolSize);
		executor.setThreadPriority(requestThreadPriority);
		executor.setThreadNamePrefix("request_executor_thread");
		executor.initialize();
		return executor;
	}
}