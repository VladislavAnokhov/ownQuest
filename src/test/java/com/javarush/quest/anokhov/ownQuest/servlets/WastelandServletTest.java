package com.javarush.quest.anokhov.ownQuest.servlets;

import com.javarush.quest.anokhov.ownQuest.entity.Player;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class WastelandServletTest extends TestCase {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher dispatcher;

    private WastelandServlet servlet;
    private Player player;

    @Before
    public void setUp() {
        servlet = new WastelandServlet();
        player = new Player("TestPlayer");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("player")).thenReturn(player);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        player.setChemicalProtection(5);

        // Мокирование для incorrectAnswers и otherText необходимо, если они используются в сервлете
        when(request.getAttribute("incorrectAnswers")).thenReturn(Map.of("DIE_FROM_RADIATION", "You died from radiation."));
        when(request.getAttribute("otherText")).thenReturn(Map.of("WAS_ALREADY", "You have already been here."));
    }

    @Test
    public void testDoPostMoveDirectionLeft() throws ServletException, IOException {
        when(request.getParameter("direction")).thenReturn("left");

        servlet.doPost(request, response);

        // Проверка вызова forward для направления "left"
        verify(request).getRequestDispatcher("locationPage.jsp");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoPostMoveDirectionRight() throws ServletException, IOException {
        when(request.getParameter("direction")).thenReturn("right");

        servlet.doPost(request, response);

        // Проверка вызова forward для направления "right"
        verify(request).getRequestDispatcher("locationPage.jsp");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoPostMoveDirectionStraight() throws ServletException, IOException {
        when(request.getParameter("direction")).thenReturn("straight");

        servlet.doPost(request, response);

        // Проверка вызова forward для направления "straight"
        verify(request).getRequestDispatcher("locationPage.jsp");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoPostMoveDirectionBack() throws ServletException, IOException {
        when(request.getParameter("direction")).thenReturn("back");

        servlet.doPost(request, response);

        // Проверка перенаправления для направления "back"
        verify(response).sendRedirect("restart?returnToBunker=true");
    }
}