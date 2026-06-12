package it.unicam.cs.mpgc.rpg126362.model;

/**
* Interface for creating creatures and battles.
*
* Allows you to extend the game with new creatures and battles
* without modifying existing code.
*/
public interface CreatureFactory {

/**
* Creates a creature from its name.
*
* @param name the name of the creature
* @return the creature created
* @throws IllegalArgumentException if the name is not recognized
*/
Creature createCreature(String name);

/**
* Creates the battle corresponding to the global battle number.
*
* @param player the player participating in the battle
* @param battleNumber the global number (1-4)
* @return the battle created
* @throws IllegalStateException if the number does not match any battles
*/
Battle createBattle(Player player, int battleNumber);
}