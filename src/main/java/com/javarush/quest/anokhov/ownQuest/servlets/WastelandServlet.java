package com.javarush.quest.anokhov.ownQuest.servlets;

import com.javarush.quest.anokhov.ownQuest.entity.Action;
import com.javarush.quest.anokhov.ownQuest.entity.Player;
import com.javarush.quest.anokhov.ownQuest.locations.Locations;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

@WebServlet("/wasteland")
public class WastelandServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String direction = request.getParameter("direction");
        HttpSession session = request.getSession();
        Player player = (Player) session.getAttribute("player");

        Map<String, String> incorrectAnswers = (Map<String, String>) request.getAttribute("incorrectAnswers");
        Map<String, String> otherText = (Map<String, String>) request.getAttribute("otherText");

        if (direction == null) {
            response.sendRedirect("wasteland.jsp");
            return;
        }


        if (player.getChemicalProtection() <= 0) {
            session.setAttribute("message", incorrectAnswers.get("DIE_FROM_RADIATION"));
            response.sendRedirect("restart?dead=true");
            return;
        }
        switch (direction) {
            case "left":
                if (!player.isBearDen()) {
                    player.decreaseChemicalProtection();
                    if (player.getChemicalProtection() <= 0) {
                        session.setAttribute("message", incorrectAnswers.get("DIE_FROM_RADIATION"));
                        response.sendRedirect("restart?dead=true");
                        return;
                    }
                    request.setAttribute("currentLocation", Locations.BEARS_HOUSE);
                    request.getRequestDispatcher("locationPage.jsp").forward(request, response);
                    player.setBearDen(true);
                } else {
                    session.setAttribute("message",otherText.get("WAS_ALREADY") );
                    player.setChemicalProtection(player.getChemicalProtection()+1);
                    response.sendRedirect("wasteland.jsp");
                }
                break;
            case "right":
                if (!player.isDogsDen()) {
                    player.decreaseChemicalProtection();
                    if (player.getChemicalProtection() <= 0) {
                        session.setAttribute("message", incorrectAnswers.get("DIE_FROM_RADIATION"));
                        response.sendRedirect("restart?dead=true");
                        return;
                    }
                    request.setAttribute("currentLocation",Locations.DOGS_HOUSE);
                    request.getRequestDispatcher("locationPage.jsp").forward(request, response);
                    player.setDogsDen(true);
                } else {
                    session.setAttribute("message", otherText.get("WAS_ALREADY"));
                    player.setChemicalProtection(player.getChemicalProtection()+1);
                    response.sendRedirect("wasteland.jsp");
                }
                break;
            case "straight":
                if (!player.isGasStation()) {
                    player.decreaseChemicalProtection();
                    if (player.getChemicalProtection() <= 0) {
                        session.setAttribute("message", incorrectAnswers.get("DIE_FROM_RADIATION"));
                        response.sendRedirect("restart?dead=true");

                        return;
                    }
                    request.setAttribute("currentLocation",Locations.GAS_STATION);
                    request.getRequestDispatcher("locationPage.jsp").forward(request, response);
                    player.setGasStation(true);
                } else {
                    session.setAttribute("message", otherText.get("WAS_ALREADY"));
                    player.setChemicalProtection(player.getChemicalProtection()+1);
                    response.sendRedirect("wasteland.jsp");
                }
                break;
            case "back":
                player.setChemicalProtection(player.getChemicalProtection()+1);
                response.sendRedirect("restart?returnToBunker=true");
                break;
            default:
                request.getRequestDispatcher("wasteland.jsp").forward(request, response);
        }
    }

}
