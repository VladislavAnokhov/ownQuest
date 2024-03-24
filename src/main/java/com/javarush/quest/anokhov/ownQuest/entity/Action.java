package com.javarush.quest.anokhov.ownQuest.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Action {
    private String name;
    private String description;

    public Action(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public  String getWasteland(){
        return "wasteland";
    }
}
