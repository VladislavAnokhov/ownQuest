package com.javarush.quest.anokhov.ownQuest.utils;

import com.javarush.quest.anokhov.ownQuest.entity.Action;
import jakarta.servlet.http.HttpServletRequest;


import java.util.List;
import java.util.Map;

public class ServletUtil {
    public static void setEnd(HttpServletRequest request, String message) {
        Map<String, String> otherText = (Map<String, String>) request.getAttribute("otherText");
        request.setAttribute("message", message);
        request.setAttribute("options", List.of(new Action("restart", otherText.get("RESTART"))));
    }

    public static void setContinue(HttpServletRequest request,String message,List<Action> actions) {
        request.setAttribute("message",message);
        request.setAttribute("options",actions);
    }
}
