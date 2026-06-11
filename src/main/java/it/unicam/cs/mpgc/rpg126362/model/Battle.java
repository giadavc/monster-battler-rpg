package it.unicam.cs.mpgc.rpg126362.model;

/**
* Manages a single battle: player versus enemy.
* Responsibilities: Take turns, calculate damage, manage enemy actions, and determine the outcome.
*
* The enemy's style (EnemyStyle) determines the action pattern:
* EARLY: Alternate Attack, Buff, Heal (15 HP).
* LATE: Alternate Attack, Buff, Heal (25 HP).
*/
public class Battle {

    public enum EnemyStyle { EARLY, LATE }

    private final Player player;
    private final Creature enemy;
    private final EnemyStyle style;
    private int enemyTurnCounter = 0;
    private boolean finished = false;
    private boolean playerWon = false;

    public Battle(Player player, Creature enemy, EnemyStyle style) {
        this.player = player;
        this.enemy = enemy;
        this.style = style;
    }

    public Creature getEnemy()           { return enemy; }
    public boolean isFinished()          { return finished; }
    public boolean isPlayerWon()         { return playerWon; }
    public int getEnemyTurnCounter()     { return enemyTurnCounter; }
    public void setEnemyTurnCounter(int t) { this.enemyTurnCounter = t; }

    public String playerAttack() {
        Creature active = player.getActive();
        int dmg = active.getEffectiveAttack();
        enemy.takeDamage(dmg);
        active.tickBuff();
        return active.getName() + " ha attaccato! " + enemy.getName() + " subisce " + dmg + " danni."
            + checkEnd() + enemyTurn();
    }

    public String playerHeal() {
        Creature active = player.getActive();
        active.heal(20);
        active.tickBuff();
        return active.getName() + " si cura di 20 HP!" + checkEnd() + enemyTurn();
    }

    public String playerBuff() {
        Creature active = player.getActive();
        active.activateBuff();
        return active.getName() + " si potenzia! +10 ATK per 2 turni." + checkEnd() + enemyTurn();
    }

    public String playerSwitch(int index) {
        try {
            String prev = player.getActive().getName();
            player.switchCreature(index);
            return prev + " si ritira. " + player.getActive().getName() + " entra in campo!";
        } catch (IllegalArgumentException e) {
            return "Impossibile cambiare: " + e.getMessage();
        }
    }

    private String enemyTurn() {
        if (finished || enemy.isDefeated()) return "";
        enemyTurnCounter++;
        String action = (style == EnemyStyle.LATE) ? performAlternatingAction(25) : performAlternatingAction(15);
        if (player.getActive().isDefeated()) {
            if (!player.autoSwitch()) {
                finished = true;
                playerWon = false;
                action += "\nTutte le tue creature sono cadute!";
            } else {
                action += "\n" + player.getActive().getName() + " entra in campo!";
            }
        }
        return "\n" + action;
    }

    private String performAlternatingAction(int healAmount) {
        int actionType = enemyTurnCounter % 3;
        if (actionType == 1) {
            int dmg = enemy.getEffectiveAttack();
            player.getActive().takeDamage(dmg);
            enemy.tickBuff();
            return enemy.getName() + " attacca! " + player.getActive().getName() + " subisce " + dmg + " danni.";
        } else if (actionType == 2) {
            enemy.activateBuff();
            return enemy.getName() + " si potenzia! +10 ATK per 2 turni.";
        } else {
            enemy.heal(healAmount);
            enemy.tickBuff();
            return enemy.getName() + " si cura di " + healAmount + " HP!";
        }
    }

    private String checkEnd() {
        if (enemy.isDefeated()) {
            finished = true;
            playerWon = true;
            return "\n" + enemy.getName() + " è stato sconfitto!";
        }
        if (player.isAllDefeated()) {
            finished = true;
            playerWon = false;
            return "\nTutte le tue creature sono cadute!";
        }
        return "";
    }
}