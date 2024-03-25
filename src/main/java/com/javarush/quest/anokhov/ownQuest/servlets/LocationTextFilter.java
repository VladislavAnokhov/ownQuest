package com.javarush.quest.anokhov.ownQuest.servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@WebFilter("/*")
public class LocationTextFilter implements Filter {
    protected ObjectMapper mapper = new ObjectMapper();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setAttribute("mainText",loadTexts("/mainText.json"));
        servletRequest.setAttribute("correctAnswers",loadTexts("/correctAnswers.json"));
        servletRequest.setAttribute("incorrectAnswers",loadTexts("/incorrectAnswers.json"));
        servletRequest.setAttribute("actionChoices",loadTexts("/actionChoices.json"));
        servletRequest.setAttribute("otherText",loadTexts("/otherText.json"));
        filterChain.doFilter(servletRequest, servletResponse);
    }


    private Map<String,String> loadTexts(String fileName) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }
        return mapper.readValue(inputStream, new TypeReference<Map<String, String>>() {});
    }
}
