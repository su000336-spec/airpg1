package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private String playerName = "";
    private int intelligence = 10;
    private int creativity = 10;
    private int aiLiteracy = 0;
    private int resume = 0;
    private String major = "Undeclared";
    private GameStep step = GameStep.WELCOME;
    private final List<String> skills = new ArrayList<>();git --version
    private String endingTitle;
    private final List<String> endingLines = new ArrayList<>();

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getCreativity() {
        return creativity;
    }

    public void setCreativity(int creativity) {
        this.creativity = creativity;
    }

    public int getAiLiteracy() {
        return aiLiteracy;
    }

    public void setAiLiteracy(int aiLiteracy) {
        this.aiLiteracy = aiLiteracy;
    }

    public int getResume() {
        return resume;
    }

    public void setResume(int resume) {
        this.resume = resume;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public GameStep getStep() {
        return step;
    }

    public void setStep(GameStep step) {
        this.step = step;
    }

    public List<String> getSkills() {
        return skills;
    }

    public String getEndingTitle() {
        return endingTitle;
    }

    public void setEndingTitle(String endingTitle) {
        this.endingTitle = endingTitle;
    }

    public List<String> getEndingLines() {
        return endingLines;
    }
}
