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

@WebServlet("/actionServlet")
public class ActionServlet  extends HttpServlet {
    private Map<String, String> correctAnswers ;
    private Map<String, String> incorrectAnswers ;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ВЫЗОВ ВЫЗОВ ВЫЗОВ СЕРВЛЕТА!");
        HttpSession session = request.getSession();

        Player player = (Player) session.getAttribute("player");
        String action = request.getParameter("action");
        String locationType = request.getParameter("currentLocation");
        Locations currentLocation = Locations.valueOf(locationType);
        System.out.println("ПРОВЕРКА 1");
        request.setAttribute("currentLocation", currentLocation);
        System.out.println("Проверка локации " + currentLocation);

        if (locationType != null && !locationType.isEmpty()) {
            try {
                currentLocation = Locations.valueOf(locationType);
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректное значение для currentLocation: " + locationType);
                // Здесь можно добавить логику обработки некорректного значения
            }
        }
        if (currentLocation != null) {
            request.setAttribute("currentLocation", currentLocation);
            System.out.println("Проверка локации: " + currentLocation);

            switch (currentLocation) {
                case DOGS_HOUSE:
                    handleDogsHouse(request, response, player, action);
                    break;
                case GAS_STATION:
                    request.getRequestDispatcher("gasStation").forward(request,response);
                    break;
                case BEARS_HOUSE:
                    handleBearHouse(request, response, player, action);
                    break;
                default:
                    request.setAttribute("message", "Неизвестное местоположение.");
                    ServletUtil.setEnd(request, "Произошла ошибка в выборе местоположения.");
                    break;
            }

        } else {
            // Если currentLocation null, перенаправляем на страницу с ошибкой или начальную страницу
            System.out.println("currentLocation не установлен.");
            request.setAttribute("message", "Location is not set.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
            return;
        }


            request.getRequestDispatcher("resultPage.jsp").forward(request, response);
    }


    private void handleDogsHouse(HttpServletRequest request,HttpServletResponse response, Player player, String action) throws ServletException, IOException {
        correctAnswers = (Map<String, String>) request.getAttribute("correctAnswers");
        incorrectAnswers = (Map<String, String>) request.getAttribute("incorrectAnswers");
        if ("usePistol".equals(action)) {
            System.out.println(" выбрал пистолет ");
            if (player.getPistolMagazines() == 0) {
                ServletUtil.setEnd(request, incorrectAnswers.get("EMPTY_MAGAZINES"));
            } else {
                player.setPistolMagazines(player.getPistolMagazines() - 1);
                ServletUtil.setContinue(request, correctAnswers.get("DOGS_WIN_WITH_PISTOL"), generateActions(player,request));
            }
        } else if ("useShovel".equals(action)) {
            ServletUtil.setContinue(request, correctAnswers.get("DOGS_WIN_WITH_SHOVEL"), generateActions(player,request));
        }
        else if ("useHands".equals(action)) {
            ServletUtil.setEnd(request, incorrectAnswers.get("DOGS_LOSE_USE_HANDS"));
        }
    }


    private void handleBearHouse(HttpServletRequest request,HttpServletResponse response, Player player, String action) throws ServletException, IOException {
        correctAnswers = (Map<String, String>) request.getAttribute("correctAnswers");
        incorrectAnswers = (Map<String, String>) request.getAttribute("incorrectAnswers");
        if ("usePistol".equals(action)) {
            if (player.getPistolMagazines() == 0) {
                ServletUtil.setEnd(request, incorrectAnswers.get("EMPTY_MAGAZINES"));
            } else if (player.isShovel() && player.getPistolMagazines() >= 1)
            {
                player.setPistolMagazines(player.getPistolMagazines() - 1);
                player.findCanister();
                ServletUtil.setContinue(request, correctAnswers.get("BEAR_WIN_WITH_PISTOL_AND_SHOVEL"),
                        generateActions(player,request));
            } else if (!player.isShovel() && player.getPistolMagazines() >= 1) {
                ServletUtil.setEnd(request, incorrectAnswers.get("BEAR_LOSE_USE_PISTOL"));
            }

        } else if ("useShovel".equals(action)) {
            if (!player.isPistol()) {
                ServletUtil.setEnd(request, incorrectAnswers.get("BEAR_LOSE_USE_SHOVEL"));
            } else if (player.isPistol() && player.getPistolMagazines() == 0) {
                ServletUtil.setEnd(request, incorrectAnswers.get("BEAR_LOSE_USE_SHOVEL_AND_EMPTY_PISTOL"));
            } else {
                player.setPistolMagazines(player.getPistolMagazines() - 1);
                player.findCanister();
                ServletUtil.setContinue(request, correctAnswers.get("BEAR_WIN_WITH_SHOVEL_AND_PISTOL"),
                        generateActions(player,request));

            }
        }
        else if ("useHands".equals(action)) {
                ServletUtil.setEnd(request, incorrectAnswers.get("BEAR_LOSE_USE_HANDS"));
        }

    }

    private List<Action> generateActions(Player player, HttpServletRequest request) {
        Map<String, String> actionChoices = (Map<String, String>) request.getAttribute("actionChoices");
        List<Action> actions = new ArrayList<>();
        if (!player.isGasStation()) {
            actions.add(new Action("gasStation", actionChoices.get("GO_TO_GAS_STATION")));
            HttpSession session = request.getSession();
            session.setAttribute("currentLocation",Locations.GAS_STATION);
            System.out.println("ДОБАВЛЕНА КНОПКА");
            System.out.println("currentLocation из класса" + session.getAttribute("currentLocation"));
        }
        actions.add(new Action("wasteland", actionChoices.get("BACK_TO_WASTELAND")));
        actions.add(new Action("restart?returnToBunker=true", actionChoices.get("BACK_TO_BUNKER")));

        return actions;
    }
}