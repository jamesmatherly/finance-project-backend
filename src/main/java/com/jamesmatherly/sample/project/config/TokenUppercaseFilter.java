package com.jamesmatherly.sample.project.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TokenUppercaseFilter implements jakarta.servlet.Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        TokenParameterRequestWrapper wrappedRequest = new TokenParameterRequestWrapper(httpRequest);
        chain.doFilter(wrappedRequest, response);
    }
}
