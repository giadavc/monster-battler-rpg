package it.unicam.cs.mpgc.rpg126362.persistence;

import it.unicam.cs.mpgc.rpg126362.model.Battle;
import it.unicam.cs.mpgc.rpg126362.model.Creature;

/**
* Temporary data needed to resume an interrupted battle.
*/
public class BattleSnapshot {

    public Creature enemy;
    public String battleLog = "";
    public int enemyTurnCounter;

    public static BattleSnapshot empty() {
        return new BattleSnapshot();
    }

    public static BattleSnapshot from(Battle battle, String battleLog) {
        BattleSnapshot snapshot = new BattleSnapshot();
        snapshot.enemy = copyCreature(battle.getEnemy());
        snapshot.battleLog = battleLog != null ? battleLog : "";
        snapshot.enemyTurnCounter = battle.getEnemyTurnCounter();
        return snapshot;
    }

    public Creature getEnemy() {
        return enemy;
    }

    public String getBattleLog() {
        return battleLog;
    }

    public int getEnemyTurnCounter() {
        return enemyTurnCounter;
    }

    private static Creature copyCreature(Creature source) {
        Creature copy = new Creature(source.getName(), source.getMaxHp(), source.getAttack());
        copy.setCurrentHp(source.getCurrentHp());
        return copy;
    }
}