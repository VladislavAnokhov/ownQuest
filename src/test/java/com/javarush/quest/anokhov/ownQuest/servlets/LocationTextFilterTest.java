package com.javarush.quest.anokhov.ownQuest.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class LocationTextFilterTest extends TestCase {

    private LocationTextFilter filter;

    @Mock
    private ServletRequest request;

    @Mock
    private ServletResponse response;

    @Mock
    private FilterChain chain;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        filter = new LocationTextFilter();
        // Замена ObjectMapper реальным объектом, так как его поведение не требует мокирования
        filter.mapper = new ObjectMapper();
    }

    @Test
    public void testDoFilter() throws Exception {
        filter.doFilter(request, response, chain);

        verify(request, atLeastOnce()).setAttribute(eq("mainText"), any());
        verify(request, atLeastOnce()).setAttribute(eq("correctAnswers"), any());
        verify(request, atLeastOnce()).setAttribute(eq("incorrectAnswers"), any());
        verify(request, atLeastOnce()).setAttribute(eq("actionChoices"), any());
        verify(request, atLeastOnce()).setAttribute(eq("otherText"), any());

        verify(chain, times(1)).doFilter(request, response);
    }
}