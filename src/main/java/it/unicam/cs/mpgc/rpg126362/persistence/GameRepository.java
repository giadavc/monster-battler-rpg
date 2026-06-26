package it.unicam.cs.mpgc.rpg126362.persistence;

import java.io.IOException;

import it.unicam.cs.mpgc.rpg126362.model.GameState;

/**
* Define game persistence operations.
*
* New save formats can be added by implementing this interface without modifying existing code.
*/
public interface GameRepository {
    void save(GameState state, BattleSnapshot battleSnapshot) throws IOException;
    SaveData load() throws IOException;
    boolean hasSave();
    void deleteSave();
}
