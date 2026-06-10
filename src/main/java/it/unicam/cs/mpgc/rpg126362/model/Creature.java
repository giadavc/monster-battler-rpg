package it.unicam.cs.mpgc.rpg126362.model;

/**
* Represents a creature with its combat stats.
* Responsibilities: Manage the creature's HP, attack, buffs, and status.
*/
public class Creature {

    private final String name;
    private int maxHp;
    private int currentHp;
    private int attack;
    private int buffTurns;

    public Creature(String name, int maxHp, int attack) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.attack = attack;
    }

    public String getName()         { return name; }
    public int getMaxHp()           { return maxHp; }
    public int getCurrentHp()       { return currentHp; }
    public int getAttack()          { return attack; }
    public int getBuffTurns()       { return buffTurns; }
    public boolean isBuffed()       { return buffTurns > 0; }
    public boolean isDefeated()     { return currentHp <= 0; }

    /** Returns the effective attack, including the buff bonus. */
    public int getEffectiveAttack() { return attack + (isBuffed() ? 10 : 0); }

    public void takeDamage(int dmg)  { currentHp = Math.max(0, currentHp - dmg); }
    public void heal(int amount)     { currentHp = Math.min(maxHp, currentHp + amount); }
    public void activateBuff()       { buffTurns = 2; }
    public void tickBuff()           { if (buffTurns > 0) buffTurns--; }
    public void resetBuff()          { buffTurns = 0; }
    public void restoreFullHp()      { currentHp = maxHp; }

    /** Applies the win bonus: HP or ATK based on the number of wins. */
    public void applyWinBonus(int winCount) {
        if (isDefeated()) return;
        if (winCount % 2 == 1) attack += 5;
        else maxHp += 5;
    }

    public void setCurrentHp(int hp)   { this.currentHp = Math.max(0, Math.min(maxHp, hp)); }
    public void setMaxHp(int maxHp)    { this.maxHp = maxHp; }
    public void setAttack(int attack)  { this.attack = attack; }

    @Override
    public String toString() {
        return name + " [HP:" + currentHp + "/" + maxHp + " ATK:" + getEffectiveAttack() + "]";
    }
}

