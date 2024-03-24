package com.javarush.quest.anokhov.ownQuest.servlets;

import com.javarush.quest.anokhov.ownQuest.entity.Player;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

@WebServlet("/bunker")
public class BunkerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Player player = (Player) session.getAttribute("player");
        String action = request.getParameter("action");
        Map<String, String> otherText = (Map<String, String>) request.getAttribute("otherText");

        switch (action) {
            case "armory":
                if (!player.isVisitedArmory()){
                    player.findPistol();
                    player.findPistolMagazine(1);
                    player.setVisitedArmory(true);
                    request.setAttribute("message", otherText.get("YOU_FOUND_PISTOL_AND_MAGAZINE"));
                }
                else {request.setAttribute("message",otherText.get("WAS_ALREADY"));}
                break;

            case "storage":
                if (!player.isVisitedStorage()) {
                    player.findShovel();
                    player.setVisitedStorage(true);
                    request.setAttribute("message", otherText.get("YOU_FOUND_SHOVEL"));
                }
                else {request.setAttribute("message",otherText.get("WAS_ALREADY"));}
                break;

            case "chemical":
                if(!player.isVisitedChemical()) {
                    player.findChemicalProtection();
                    player.setVisitedChemical(true);
                    request.setAttribute("message", otherText.get("YOU_FOUND_CHEMICAL_PROTECTION"));
                }
                else {request.setAttribute("message",otherText.get("WAS_ALREADY"));}
                break;
            case "exit":
                request.getRequestDispatcher("wasteland.jsp").forward(request, response);
                return;
        }
        request.getRequestDispatcher("bunker.jsp").forward(request, response);
    }

}
