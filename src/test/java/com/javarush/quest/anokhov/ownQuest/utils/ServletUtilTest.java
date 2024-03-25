package com.javarush.quest.anokhov.ownQuest.utils;

import com.javarush.quest.anokhov.ownQuest.entity.Action;
import jakarta.servlet.http.HttpServletRequest;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServletUtilTest {
    @Mock
    private HttpServletRequest request;
    @Test
    public void testSetEnd() {
        Map<String, String> otherText = Map.of("RESTART", "Restart the game");
        when(request.getAttribute("otherText")).thenReturn(otherText);

        ServletUtil.setEnd(request, "Game Over");

        verify(request).setAttribute("message", "Game Over");
        verify(request).setAttribute(eq("options"), any(List.class));
    }

    @Test
    public void testSetContinue() {
        List<Action> actions = List.of(new Action("actionName", "actionDescription"));

        ServletUtil.setContinue(request, "Continue?", actions);

        verify(request).setAttribute("message", "Continue?");
        verify(request).setAttribute("options", actions);
    }
}