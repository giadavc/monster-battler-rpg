package it.unicam.cs.mpgc.rpg126362.controller;

import it.unicam.cs.mpgc.rpg126362.model.Battle;
/**
 * Manages player actions during combat.
 */
public class BattleController implements IBattleController {

    private final Battle battle;

    public BattleController(Battle battle) {
        this.battle = battle;
    }

    @Override
    public Battle getBattle() { return battle; }

    @Override
    public boolean isFinished() { return battle.isFinished(); }

    @Override
    public String attack() { return battle.playerAttack(); }

    @Override
    public String heal() { return battle.playerHeal(); }

    @Override
    public String buff() { return battle.playerBuff(); }

    @Override
    public String switchTo(int index) { return battle.playerSwitch(index); }
}
