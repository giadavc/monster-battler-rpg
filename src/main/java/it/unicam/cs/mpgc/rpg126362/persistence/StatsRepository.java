package it.unicam.cs.mpgc.rpg126362.persistence;

/**
* Contract for saving and loading global statistics.
*/
public interface StatsRepository {

/** Loads statistics. If they don't exist, returns an object with a zero value. */
Stats load();

/** Saves statistics, updating the data from the last game. */
void save(Stats stats);

  class Stats {
        public int wins = 0;
        public int losses = 0;
        public String lastDate = "-";
    }   

}
