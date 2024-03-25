package com.javarush.quest.anokhov.ownQuest.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Player {
    private String name;
    private boolean pistol;
    private int pistolMagazines;
    private boolean shovel;
    private int chemicalProtection;
    private int canisters;
    private int totalGames;
    private int totalWins;
    private boolean bearDen = false;
    private boolean dogsDen = false;
    private boolean gasStation = false;
    private boolean visitedArmory = false;
    private boolean visitedStorage = false;
    private boolean visitedChemical = false;
    private boolean usePistol = false;
    private boolean useShovel = false;
    private boolean useHands = false;
    public Player (String name){
        this.name=name;
        pistol=false;
        pistolMagazines=0;
        shovel=false;
        chemicalProtection=0;
        canisters=0;
    }
    public void findPistol(){
        this.pistol=true;
    }
    public void findChemicalProtection(){
        this.chemicalProtection=chemicalProtection+7;
    }
    public void findShovel (){
        this.shovel=true;
    }
    public void findCanister(){
        this.canisters=canisters+1;
    }
    public void findPistolMagazine(int count){
        this.pistolMagazines=pistolMagazines+count;
    }
    public void decreaseChemicalProtection(){
        chemicalProtection=chemicalProtection-1;
    }
    public void refreshSkills(){
        useHands=false;
        usePistol=false;
        useShovel=false;
    }
    public boolean checkSkills(){
        if(useHands){
            return true;
        }
         if(usePistol){
            return true;
        }
         if(useShovel){
            return true;
        }
        return false;
    }
}