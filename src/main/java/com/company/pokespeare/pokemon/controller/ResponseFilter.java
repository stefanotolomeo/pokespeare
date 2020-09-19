package com.company.pokespeare.pokemon.controller;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ResponseFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		res.addHeader("Access-Control-Allow-Origin", "*");
		res.addHeader("Accept", "application/json");
		res.addHeader("Accept-Language", "en-gb");
		res.addHeader("Content-Type", "application/json; charset=utf-8");
		chain.doFilter(req, res);
	}
}