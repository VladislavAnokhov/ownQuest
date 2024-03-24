package com.javarush.quest.anokhov.ownQuest.servlets;

import com.javarush.quest.anokhov.ownQuest.entity.Action;
import com.javarush.quest.anokhov.ownQuest.entity.Player;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/quest")
public class GameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> incorrectAnswers = (Map<String, String>) request.getAttribute("incorrectAnswers");
        Map<String, String> correctAnswers = (Map<String, String>) request.getAttribute("correctAnswers");
        Map<String, String> otherText = (Map<String, String>) request.getAttribute("otherText");

        HttpSession session = request.getSession();
        String playerName = request.getParameter("playerName");
        Player player = (Player) session.getAttribute("player");
        session.setAttribute("playerName", playerName);

        if (player == null){
            player=new Player(playerName);
            session.setAttribute("player",player);
        }

        String acceptQuest = request.getParameter("acceptQuest");
        String rejectQuest = request.getParameter("rejectQuest");

        if (correctAnswers.get("ACCEPT_QUEST").equals(acceptQuest)) {
            response.sendRedirect("bunker.jsp");
        }
        else if (incorrectAnswers.get("DENY_QUEST").equals(rejectQuest)) {
            session.setAttribute("message", incorrectAnswers.get("DENIED_QUEST"));
            request.setAttribute("options",List.of(
                    new Action("restart",otherText.get("RESTART"))
            ));
            session.setAttribute("playerName", playerName);
            request.getRequestDispatcher("resultPage.jsp").forward(request, response);
        }
        else {
            request.getRequestDispatcher("quest.jsp").forward(request, response);
        }
    }
}
