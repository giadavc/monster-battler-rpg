package it.unicam.cs.mpgc.rpg126362.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
* Save and load global statistics to a JSON file.
*/ 
public class JsonStatsRepository implements StatsRepository {

    private static final Path STATS_FILE = Paths.get(
        System.getProperty("user.home"), ".monster-battler-rpg", "stats.json"
    );

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Stats load() {
        try {
            if (Files.exists(STATS_FILE)) {
                return gson.fromJson(Files.readString(STATS_FILE), Stats.class);
            }
        } catch (IOException e) {
            System.err.println("Impossibile leggere le statistiche: " + e.getMessage());
        }
        return new Stats();
    }

    @Override
    public void save(Stats stats) {
        try {
            Files.createDirectories(STATS_FILE.getParent());
            stats.lastDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Files.writeString(STATS_FILE, gson.toJson(stats));
        } catch (IOException e) {
            System.err.println("Impossibile salvare le statistiche: " + e.getMessage());
        }
    }
}

