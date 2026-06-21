package it.unicam.cs.mpgc.rpg126362.controller;

import java.io.IOException;

import it.unicam.cs.mpgc.rpg126362.model.*;
import it.unicam.cs.mpgc.rpg126362.persistence.JsonGameRepository;
import it.unicam.cs.mpgc.rpg126362.persistence.JsonStatsRepository;

public class GameController implements IGameController {

    private final GameSaveService gameSaveService;
    private final CreatureFactory creatureFactory;
    private final VictoryHandler victoryHandler;

    private GameState state;
    private IBattleController battleController;

    public GameController() {
        this.creatureFactory = new DefaultCreatureFactory();
        this.gameSaveService = new GameSaveService(new JsonGameRepository(), creatureFactory);
        this.victoryHandler = new VictoryHandler(new JsonStatsRepository(), creatureFactory);
    }
 
    @Override
    public void startNewGame(String trainerName, String starterName) {
        Creature starter = creatureFactory.createCreature(starterName);
        state = new GameState(new Player(trainerName, starter));
        battleController = new BattleController(buildBattle());
    }

    @Override
    public void loadGame() throws IOException {
        state = gameSaveService.loadState();
        Battle baseBattle = buildBattle();
        battleController = new BattleController(gameSaveService.restoreSavedBattle(baseBattle));
    }

    @Override
    public GameState getState()                       { return state; }

    @Override
    public IBattleController getBattleController()    { return battleController; }

    @Override
    public GameSaveService getGameSaveService()        { return gameSaveService; }

    private Battle buildBattle() {
        return creatureFactory.createBattle(state.getPlayer(), state.getGlobalBattle());
    }

    @Override
    public String handleVictory() {
        String enemyName = battleController.getBattle().getEnemy().getName();
        String summary = victoryHandler.processVictory(state, enemyName);
        if (!state.isGameWon()) {
            battleController = new BattleController(buildBattle());
            gameSaveService.clearLoadedBattleSnapshot();
        } else {
            gameSaveService.deleteSave();
            gameSaveService.clearLoadedBattleSnapshot();
        }
        return summary;
    }

    @Override
    public String handleDefeat() {
        gameSaveService.deleteSave();
        gameSaveService.clearLoadedBattleSnapshot();
        return victoryHandler.processDefeat();
    }

}
