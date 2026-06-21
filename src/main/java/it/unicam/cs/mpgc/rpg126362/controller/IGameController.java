package it.unicam.cs.mpgc.rpg126362.controller;

import java.io.IOException;

import it.unicam.cs.mpgc.rpg126362.model.GameState;

/**
* Contract for the game's main controller.
* Responsibilities: Define game flow operations (start, load, progression).
*/
public interface IGameController {

    void startNewGame(String trainerName, String starterName);

    void loadGame() throws IOException;

    GameState getState();

    IBattleController getBattleController();

    GameSaveService getGameSaveService();

}
