package it.unicam.cs.mpgc.rpg126362.controller;

import it.unicam.cs.mpgc.rpg126362.model.Battle;

/**
* Contract for the battle controller.
* Define the player's actions during a battle.
*
*/
public interface IBattleController {

Battle getBattle();

boolean isFinished();

String attack();

String heal();

String buff();

String switchTo(int index);

}