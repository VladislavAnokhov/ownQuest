package com.javarush.quest.anokhov.ownQuest.servlets;

import com.javarush.quest.anokhov.ownQuest.entity.Action;
import com.javarush.quest.anokhov.ownQuest.entity.Player;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/restart")
public class RestartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Player player = (Player) session.getAttribute("player");
        boolean dead = Boolean.parseBoolean(request.getParameter("dead"));
        Map<String, String> otherText = (Map<String, String>) request.getAttribute("otherText");

        if (dead) {
            session.setAttribute("message", otherText.get("DIE_FROM_RADIATION"));
            session.setAttribute("options", List.of(
                    new Action("restart",otherText.get("RESTART"))
            ));
            response.sendRedirect("resultPage.jsp");
            return;
        } else {
            // Обработка возвращения в бункер и подсчета канистр
            String returnToBunker = request.getParameter("returnToBunker");
            if ("true".equals(returnToBunker)) {
                if (player.getCanisters() >= 2) {
                    session.setAttribute("message", otherText.get("YOU_WON"));
                    session.setAttribute("options", List.of(
                            new Action("restart",otherText.get("RESTART"))
                    ));
                } else {
                    session.setAttribute("message", otherText.get("YOU_LOSE_CANISTERS"));
                    session.setAttribute("options", List.of(
                            new Action("restart",otherText.get("RESTART"))
                    ));
                }
                response.sendRedirect("resultPage.jsp");
                return;
            }
        }

        // Сброс состояния для новой игры
        resetPlayerForNewGame(session, player,request);
        response.sendRedirect("quest.jsp");
    }

    private void resetPlayerForNewGame(HttpSession session, Player player, HttpServletRequest request) {
        String playerName = player.getName();
        int totalGames = player.getTotalGames() + 1;
        int totalWins = player.getTotalWins() + (player.getCanisters() >= 2 ? 1 : 0);

        session.invalidate();
        HttpSession newSession = request.getSession(true);
        Player newPlayer = new Player(playerName);
        newPlayer.setTotalGames(totalGames);
        newPlayer.setTotalWins(totalWins);

        newSession.setAttribute("player", newPlayer);
    }

}