package it.unicam.cs.mpgc.rpg126362.model;

/**
* Implementation of {@link CreatureFactory} with the base game's creatures and battles.
* Responsibility: Building the game's default creatures and battles.
*/

public class DefaultCreatureFactory implements CreatureFactory {

    @Override
    public Creature createCreature(String name) {
        return switch (name) {
            case "FireWolf"    -> new Creature("FireWolf",    100, 18);
            case "AquaTurtle"  -> new Creature("AquaTurtle",  120, 14);
            case "NatureFox"   -> new Creature("NatureFox",   110, 16);
            case "StormHawk"   -> new Creature("StormHawk",   115, 17);
            default -> throw new IllegalArgumentException("Creatura sconosciuta: " + name);
        };
    }

    @Override
    public Battle createBattle(Player player, int battleNumber) {
        return switch (battleNumber) {
            case 1 -> new Battle(player, new Creature("LeafCat",      70,  15), Battle.EnemyStyle.EARLY);
            case 2 -> new Battle(player, new Creature("TinySlime",    80,  18), Battle.EnemyStyle.EARLY);
            case 3 -> new Battle(player, new Creature("IcePhoenix",  160,  26), Battle.EnemyStyle.LATE);
            case 4 -> new Battle(player, new Creature("ShadowDragon",180,  20), Battle.EnemyStyle.LATE);
            default -> throw new IllegalStateException("Nessuna battaglia per il numero " + battleNumber);
        };
    }
}
