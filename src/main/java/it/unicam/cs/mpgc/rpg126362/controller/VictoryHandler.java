package it.unicam.cs.mpgc.rpg126362.controller;

import it.unicam.cs.mpgc.rpg126362.model.Creature;
import it.unicam.cs.mpgc.rpg126362.model.CreatureFactory;
import it.unicam.cs.mpgc.rpg126362.model.GamePhase;
import it.unicam.cs.mpgc.rpg126362.model.GameState;
import it.unicam.cs.mpgc.rpg126362.persistence.StatsRepository;

/**
* Handles the consequences of a finished battle: victory or defeat.
*/
public class VictoryHandler {

    private final StatsRepository statsRepository;
    private final CreatureFactory creatureFactory;

    public VictoryHandler(StatsRepository statsRepository, CreatureFactory creatureFactory) {
        this.statsRepository = statsRepository;
        this.creatureFactory = creatureFactory;
    }

/**
* Processes the player's victory: updates the model, unlocks creatures, and updates stats if the game is over.
*
* @param state the current state of the game
* @param defeatedEnemy the name of the enemy just defeated
* @return the summary message to display to the player
*/
    public String processVictory(GameState state, String defeatedEnemy) {
        state.getPlayer().registerWin();
        boolean phaseChanged = state.advance();
        int winCount = state.getPlayer().getWinCount();
        String bonus = (winCount % 2 == 1) ? "+5 ATK" : "+5 MaxHP";

        StringBuilder msg = new StringBuilder("Congratulazioni! Hai vinto la battaglia contro " + defeatedEnemy + "!");
        msg.append("\nHai ottenuto un aumento di ").append(bonus).append(" per il tuo team.");

        if (phaseChanged) {
            String unlocked = GamePhase.EARLY_GAME.getUnlockedCreature();
            if (unlocked != null) {
                Creature newCreature = creatureFactory.createCreature(unlocked);
                if (state.getPlayer().addCreature(newCreature))
                    msg.append("\n★ ").append(unlocked).append(" si è unita al tuo team!");
            }
        }

        state.getPlayer().getTeam().stream()
            .filter(c -> !c.isDefeated())
            .forEach(Creature::restoreFullHp);
        state.getPlayer().getTeam().forEach(Creature::resetBuff);

        if (state.isGameWon()) {
            StatsRepository.Stats stats = statsRepository.load();
            stats.wins++;
            statsRepository.save(stats);
            return msg + "\n\n🏆 Hai sconfitto tutti i nemici! HAI VINTO!";
        }

        return msg.toString();
        }
        
/**
* Processes player defeat: Updates global statistics.
* @return the game over message
*/
    public String processDefeat() {
            StatsRepository.Stats stats = statsRepository.load();
        stats.losses++;
        statsRepository.save(stats);
        return "Tutte le tue creature sono state sconfitte. Game Over.";
    }
}
