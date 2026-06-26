package it.unicam.cs.mpgc.rpg126362.controller;

import java.io.IOException;

import it.unicam.cs.mpgc.rpg126362.model.Battle;
import it.unicam.cs.mpgc.rpg126362.model.Creature;
import it.unicam.cs.mpgc.rpg126362.model.CreatureFactory;
import it.unicam.cs.mpgc.rpg126362.model.GameState;
import it.unicam.cs.mpgc.rpg126362.persistence.BattleSnapshot;
import it.unicam.cs.mpgc.rpg126362.persistence.GameRepository;
import it.unicam.cs.mpgc.rpg126362.persistence.SaveData;

/**
 * Service dedicated to saving and loading the game:
 * coordinates the repository and model for persistence operations.
 */
public class GameSaveService {
 
    private final GameRepository repository;
    private final CreatureFactory factory;
    private BattleSnapshot loadedBattleSnapshot = BattleSnapshot.empty();

    public GameSaveService(GameRepository repository, CreatureFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    public GameState loadState() throws IOException {
        SaveData saveData = repository.load();
        loadedBattleSnapshot = saveData.toBattleSnapshot();
        return saveData.toGameState(factory);
    }

    /**
    * Restores the enemy's state to a previously created base battle,
    * applying the saved values ​​(HP, attack, turn counter).
    */
    public Battle restoreSavedBattle(Battle baseBattle) {
    if (loadedBattleSnapshot.getEnemy() != null) {
        Creature savedEnemy = loadedBattleSnapshot.getEnemy();
        baseBattle.getEnemy().setMaxHp(savedEnemy.getMaxHp());
        baseBattle.getEnemy().setCurrentHp(savedEnemy.getCurrentHp());
        baseBattle.getEnemy().setAttack(savedEnemy.getAttack());
        baseBattle.setEnemyTurnCounter(loadedBattleSnapshot.getEnemyTurnCounter());
        }
    return baseBattle;
    }

   /** Saves the current state, including the enemy state from the current battle. */
    public void saveState(GameState state, IBattleController battleController, String battleLog) throws IOException {
        BattleSnapshot battleSnapshot = BattleSnapshot.empty();
        if (battleController != null && battleController.getBattle() != null) {
            battleSnapshot = BattleSnapshot.from(battleController.getBattle(), battleLog);
        }
        repository.save(state, battleSnapshot);
        loadedBattleSnapshot = battleSnapshot;
    }


    public boolean hasSave() {
        return repository.hasSave();
    }

    public void deleteSave() {
        repository.deleteSave();
    }

    public String getLoadedBattleLog() {
        return loadedBattleSnapshot.getBattleLog();
    }

    public void clearLoadedBattleSnapshot() {
        loadedBattleSnapshot = BattleSnapshot.empty();
    }
}