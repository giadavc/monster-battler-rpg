package it.unicam.cs.mpgc.rpg126362.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player and their team of creatures.
 * Responsibilities: manage the team, the active creature, and the win counter.
 */
public class Player {

    private final String name;
    private final List<Creature> team = new ArrayList<>();
    private int activeIndex = 0;
    private int winCount = 0;

    public Player(String name, Creature starter) {
        this.name = name;
        team.add(starter);
    }

    public String getName()              { return name; }
    public List<Creature> getTeam()      { return team; }
    public int getWinCount()             { return winCount; }
    public int getActiveIndex()          { return activeIndex; }
    public Creature getActive()          { return team.get(activeIndex); }

    /** Adds a creature to the team. Returns false if the team is full (max 2). */
    public boolean addCreature(Creature c) {
        if (team.size() >= 2) return false;
        team.add(c);
        return true;
    }

    /** Checks whether all creatures in the team are defeated. */
    public boolean isAllDefeated() {
        return team.stream().allMatch(Creature::isDefeated);
    }

    /** Switches the active creature to the given index. */
    public void switchCreature(int index) {
        if (index < 0 || index >= team.size() || team.get(index).isDefeated())
            throw new IllegalArgumentException("Cannot switch to creature at index " + index);
        activeIndex = index;
    }

    /** Automatically switches to the first alive creature. Returns false if none are available. */
    public boolean autoSwitch() {
        for (int i = 0; i < team.size(); i++) {
            if (!team.get(i).isDefeated()) { activeIndex = i; return true; }
        }
        return false;
    }

    /** Records a win and applies win bonuses to the entire team. */
    public void registerWin() {
        winCount++;
        for (Creature c : team) c.applyWinBonus(winCount);
    }

    public void setWinCount(int w)     { this.winCount = w; }
    public void setActiveIndex(int i)  { if (i >= 0 && i < team.size()) activeIndex = i; }
}

