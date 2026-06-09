package it.unicam.cs.mpgc.rpg126362.model;

/**
 * Fasi del gioco.
 * Ogni fase contiene due battaglie. Completare la fase EARLY_GAME sblocca una creatura.
 */
public enum GamePhase {
    EARLY_GAME, LATE_GAME;

    /** Restituisce il nome della creatura sbloccata al termine di questa fase, o null se nessuna. */
    public String getUnlockedCreature() {
        return this == EARLY_GAME ? "StormHawk" : null;
    }
}

