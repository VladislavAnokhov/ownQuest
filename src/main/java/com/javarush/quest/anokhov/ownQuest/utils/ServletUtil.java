package com.javarush.quest.anokhov.ownQuest.utils;

import com.javarush.quest.anokhov.ownQuest.entity.Action;
import jakarta.servlet.http.HttpServletRequest;


import java.util.List;

public class ServletUtil {
    public static void setEnd(HttpServletRequest request, String message) {
        request.setAttribute("message", message);
        request.setAttribute("options", List.of(new Action("restart", "начать сначала")));
    }

    public static void setContinue(HttpServletRequest request,String message,List<Action> actions) {
        request.setAttribute("message",message);
        request.setAttribute("options",actions);
    }
}
