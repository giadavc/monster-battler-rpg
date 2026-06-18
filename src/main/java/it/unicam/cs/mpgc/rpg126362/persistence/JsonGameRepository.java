package it.unicam.cs.mpgc.rpg126362.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import it.unicam.cs.mpgc.rpg126362.model.GameState;

/**
* Saves and loads game data to a JSON file.
*/
public class JsonGameRepository implements GameRepository {

    private static final Path SAVE_FILE = Paths.get(
        System.getProperty("user.home"), ".monster-battler-rpg", "save.json"
    );

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public JsonGameRepository() {
        try {
            Files.createDirectories(SAVE_FILE.getParent());
        } catch (IOException e) {
            System.err.println("Impossibile creare la directory di salvataggio: " + e.getMessage());
        }
    }

    @Override
    public void save(GameState state, BattleSnapshot battleSnapshot) throws IOException {
        Files.writeString(SAVE_FILE, gson.toJson(SaveData.from(state, battleSnapshot)));
    }

    @Override
    public SaveData load() throws IOException {
        return gson.fromJson(Files.readString(SAVE_FILE), SaveData.class);
    }

}
