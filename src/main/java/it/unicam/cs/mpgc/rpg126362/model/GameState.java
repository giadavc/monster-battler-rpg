package it.unicam.cs.mpgc.rpg126362.model;

/**
* Current game state: progression, stage, and progress.
* Responsibility: Only track gameplay flow, not technical save details.
*/

public class GameState {

    private final Player player;
    private GamePhase phase = GamePhase.EARLY_GAME;
    private int battleIndex = 0;
    private boolean gameWon = false;

    public GameState(Player player) {
        this.player = player;
    }

    public Player getPlayer()      { return player; }
    public GamePhase getPhase()    { return phase; }
    public int getBattleIndex()    { return battleIndex; }
    public boolean isGameWon()     { return gameWon; }

/** Returns the current battle's global number (1-4). */
    
    public int getGlobalBattle() {
        return (phase == GamePhase.EARLY_GAME ? 0 : 2) + battleIndex + 1;
    }

/**
* Advances to the next battle.
* Returns true if the phase has changed (e.g., from EARLY_GAME to LATE_GAME).
*/
    public boolean advance() {
        battleIndex++;
        if (battleIndex >= 2) {
            battleIndex = 0;
            if (phase == GamePhase.EARLY_GAME) {
                phase = GamePhase.LATE_GAME;
                return true;
            } else {
                gameWon = true;
            }
        }
        return false;
    }

    public void restore(GamePhase p, int idx) {
        this.phase = p;
        this.battleIndex = idx;
    }
}
