package com.javarush.quest.anokhov.ownQuest.entity;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.*;
public class ActionTest extends TestCase {
    @Test
    public void testActionConstructorAndGetters() {
        Action action = new Action("TestName", "TestDescription");

        Assert.assertEquals("TestName", action.getName());
        Assert.assertEquals("TestDescription", action.getDescription());
    }

    @Test
    public void testSetters() {
        Action action = new Action("", "");
        action.setName("NewName");
        action.setDescription("NewDescription");
        Assert.assertEquals("NewName", action.getName());
        Assert.assertEquals("NewDescription", action.getDescription());
    }
}