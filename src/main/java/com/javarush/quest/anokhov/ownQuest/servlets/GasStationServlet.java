package com.javarush.quest.anokhov.ownQuest.servlets;

import com.javarush.quest.anokhov.ownQuest.entity.Action;
import com.javarush.quest.anokhov.ownQuest.entity.Player;
import com.javarush.quest.anokhov.ownQuest.locations.Locations;
import com.javarush.quest.anokhov.ownQuest.utils.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/gasStation")
public class GasStationServlet extends HttpServlet {
    private Map<String, String> correctAnswers ;
    private Map<String, String> incorrectAnswers ;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setAttribute("currentLocation", Locations.GAS_STATION);

        Player player = (Player) session.getAttribute("player");
        String action = request.getParameter("action");
        player.setGasStation(true);
        correctAnswers = (Map<String, String>) request.getAttribute("correctAnswers");
        incorrectAnswers = (Map<String, String>) request.getAttribute("incorrectAnswers");
        if ("usePistol".equals(action)) {
            player.setUsePistol(true);
            if (player.getPistolMagazines() == 0) {
                ServletUtil.setEnd(request, incorrectAnswers.get("EMPTY_MAGAZINES"));
            } else {
                player.setPistolMagazines(player.getPistolMagazines() - 1);
                player.setPistolMagazines(player.getPistolMagazines() + 1); //можно и убрать, но вдруг что-то нужно поменять
                player.findCanister();
                ServletUtil.setContinue(request, correctAnswers.get("RAIDERS_WIN_WITH_PISTOL"), generateActions(request));
            }
        } else if ("useShovel".equals(action)) {
            player.setUseShovel(true);
            ServletUtil.setEnd(request, incorrectAnswers.get("RAIDERS_LOSE_USE_SHOVEL"));
        }
        else if ("useHands".equals(action)) {
            player.setUseHands(true);
            ServletUtil.setEnd(request, incorrectAnswers.get("RAIDERS_LOSE_USE_HANDS"));
        }
        if(player.checkSkills()) {
            request.getRequestDispatcher("resultPage.jsp").forward(request,response);
        }
        else
            request.getRequestDispatcher("locationPage.jsp").forward(request, response);
            player.refreshSkills();
    }

    private List<Action> generateActions(HttpServletRequest request) {
        Map<String, String> actionChoices = (Map<String, String>) request.getAttribute("actionChoices");
        List<Action> actions = new ArrayList<>();
        actions.add(new Action("wasteland", actionChoices.get("BACK_TO_WASTELAND")));
        actions.add(new Action("restart?returnToBunker=true", actionChoices.get("BACK_TO_BUNKER")));
        return actions;
    }
}
