package it.unicam.cs.mpgc.rpg126362.persistence;

import java.io.IOException;

import it.unicam.cs.mpgc.rpg126362.model.GameState;

public interface GameRepository {
    void save(GameState state, BattleSnapshot battleSnapshot) throws IOException;
    SaveData load() throws IOException;
    boolean hasSave();
    void deleteSave();
}
