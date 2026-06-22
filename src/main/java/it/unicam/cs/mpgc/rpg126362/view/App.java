package it.unicam.cs.mpgc.rpg126362.view;
import javafx.application.Application;
import javafx.stage.Stage;
/**
* JavaFX application entry point.
*/
public class App extends Application {

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Monster Battler RPG");
        stage.setResizable(false);
        new MainMenuView(stage).show();
    }
}