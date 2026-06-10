package it.unicam.cs.mpgc.rpg126362.model;

/**
 * Game phases.
 * Each phase contains two battles. Completing the EARLY_GAME phase unlocks a creature.
 */
public enum GamePhase {
    EARLY_GAME, LATE_GAME;

   /** Returns the name of the creature unlocked at the end of this phase, or null if none. */
    public String getUnlockedCreature() {
        return this == EARLY_GAME ? "StormHawk" : null;
    }
}

