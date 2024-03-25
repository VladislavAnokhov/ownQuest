package com.javarush.quest.anokhov.ownQuest.servlets;

import com.javarush.quest.anokhov.ownQuest.entity.Player;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RestartServletTest extends TestCase {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    private RestartServlet servlet;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        servlet = new RestartServlet();
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testProcessRequestDead() throws IOException {
        when(request.getParameter("dead")).thenReturn("true");
        Map<String, String> otherText = new HashMap<>();
        otherText.put("DIE_FROM_RADIATION", "You died from radiation.");
        when(request.getAttribute("otherText")).thenReturn(otherText);

        servlet.doGet(request, response);

        verify(session).setAttribute("message", "You died from radiation.");
        verify(response).sendRedirect("resultPage.jsp");
    }

    @Test
    public void testProcessRequestReturnToBunkerWin() throws IOException {
        when(request.getParameter("dead")).thenReturn(null);
        when(request.getParameter("returnToBunker")).thenReturn("true");
        Player player = new Player("TestPlayer");
        player.findCanister();
        player.findCanister(); // Предполагаем, что выигрыш требует 2 канистры
        when(session.getAttribute("player")).thenReturn(player);
        Map<String, String> otherText = new HashMap<>();
        otherText.put("YOU_WON", "You successfully returned to the bunker and won!");
        when(request.getAttribute("otherText")).thenReturn(otherText);

        servlet.doPost(request, response);

        verify(session).setAttribute("message", "You successfully returned to the bunker and won!");
        verify(response).sendRedirect("resultPage.jsp");
    }

    @Test
    public void testProcessRequestReturnToBunkerLose() throws IOException {
        when(request.getParameter("dead")).thenReturn(null);
        when(request.getParameter("returnToBunker")).thenReturn("true");
        Player player = new Player("TestPlayer");
        player.findCanister(); // Предполагаем, что проигрыш, если меньше 2 канистр
        when(session.getAttribute("player")).thenReturn(player);
        Map<String, String> otherText = new HashMap<>();
        otherText.put("YOU_LOSE_CANISTERS", "You didn't find enough canisters.");
        when(request.getAttribute("otherText")).thenReturn(otherText);

        servlet.doPost(request, response);

        verify(session).setAttribute("message", "You didn't find enough canisters.");
        verify(response).sendRedirect("resultPage.jsp");
    }
}