package it.unicam.cs.mpgc.rpg126362.persistence;

import java.util.ArrayList;
import java.util.List;

import it.unicam.cs.mpgc.rpg126362.model.*;


public class SaveData {

    public String trainerName;
    public String phase;
    public int battleIndex;
    public int activeIndex;
    public int winCount;
    public List<CreatureData> team = new ArrayList<>();

    public BattleSnapshot battleSnapshot;

    public static class CreatureData {
        public String name;
        public int maxHp, currentHp, attack;
    }

    /** Constructs a SaveData from the current game state and the battle snapshot. */
    public static SaveData from(GameState state, BattleSnapshot battleSnapshot) {
        SaveData d = new SaveData();
        Player p = state.getPlayer();
        d.trainerName  = p.getName();
        d.phase        = state.getPhase().name();
        d.battleIndex  = state.getBattleIndex();
        d.activeIndex  = p.getActiveIndex();
        d.winCount     = p.getWinCount();

        for (Creature c : p.getTeam()) {
            CreatureData cd = new CreatureData();
            cd.name      = c.getName();
            cd.maxHp     = c.getMaxHp();
            cd.currentHp = c.getCurrentHp();
            cd.attack    = c.getAttack();
            d.team.add(cd);
        }

        d.battleSnapshot = battleSnapshot != null ? battleSnapshot : BattleSnapshot.empty();
        return d;
    }

    /**
    * Rebuilds a {@link GameState} from this SaveData.
    *
    * @param factory used to recreate creatures from the name
    */
    public GameState toGameState(CreatureFactory factory) {
        CreatureData first = team.get(0);
        Creature starter = factory.createCreature(first.name);
        

        Player player = new Player(trainerName, starter);
        for (int i = 1; i < team.size(); i++) {
            CreatureData cd = team.get(i);
            Creature c = factory.createCreature(cd.name);
           
            player.addCreature(c);
        }
        player.setWinCount(winCount);
        player.setActiveIndex(activeIndex);

        GameState state = new GameState(player);
        state.restore(GamePhase.valueOf(phase), battleIndex);

        return state;
    }

    public BattleSnapshot toBattleSnapshot() {
        return battleSnapshot != null ? battleSnapshot : BattleSnapshot.empty();
    }

 
}
