package it.unicam.cs.mpgc.rpg126362.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class EndView {

    private final Stage stage;
    private final boolean won;
    private final String trainerName;

    public EndView(Stage stage, boolean won, String trainerName) {
        this.stage = stage;
        this.won = won;
        this.trainerName = trainerName;
    }

    public void show() {
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(60));
        root.setStyle("-fx-background-color:linear-gradient(to bottom right,"
                + (won ? "#0d1f0d,#183a18" : "#1f0d0d,#3a1818") + ");");

        Text icon  = new Text(won ? "🏆" : "💀");
        icon.setFont(Font.font(80));

        Text title = new Text(won ? "CONGRATULAZIONI!" : "GAME OVER");
        title.setFont(Font.font("Arial Black", FontWeight.BOLD, 36));
        title.setFill(Color.web(won ? "#4ecca3" : "#e94560"));

        String body = won
            ? "Hai sconfitto ShadowDragon!\n" + trainerName + ", sei il più grande Allenatore di Mostri!"
            : "Tutte le tue creature sono state sconfitte.\nIn bocca al lupo la prossima volta, " + trainerName + ".";
        Text bodyText = new Text(body);
        bodyText.setFont(Font.font("Arial", FontPosture.ITALIC, 18));
        bodyText.setFill(Color.web("#cccccc"));
        bodyText.setTextAlignment(TextAlignment.CENTER);

        Button menu = new Button("↩ Torna al Menu Principale");
        menu.setStyle("-fx-background-color:" + (won ? "#4ecca3" : "#e94560")
                + ";-fx-text-fill:white;-fx-font-weight:bold;-fx-font-size:15;"
                + "-fx-padding:12 30;-fx-background-radius:10;-fx-cursor:hand;");
        menu.setOnAction(e -> new MainMenuView(stage).show());

        root.getChildren().addAll(icon, title, bodyText, menu);
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }
}

