package it.unicam.cs.mpgc.rpg126362.view;

import java.io.IOException;

import it.unicam.cs.mpgc.rpg126362.controller.IGameController;
import it.unicam.cs.mpgc.rpg126362.model.Creature;
import it.unicam.cs.mpgc.rpg126362.model.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;


/**
* Battle Screen: Displays HP bars, combat log, and action buttons.
*/
public class BattleView {
    
    private final Stage stage;
    private final IGameController controller; 

    private Label enemyHpLbl, playerHpLbl;
    private ProgressBar enemyBar, playerBar;
    private TextArea log;
    

    public BattleView(Stage stage, IGameController controller) {
        this.stage = stage;
        this.controller = controller;
    }

public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color:linear-gradient(to bottom right,#111827,#1f2937,#0f172a);");
        root.setPadding(new Insets(20));
        root.setTop(buildTop());
        root.setCenter(buildLog());
        root.setBottom(buildBottom());

        String initialLog = controller.getGameSaveService().getLoadedBattleLog();
        if (initialLog != null && !initialLog.isEmpty()) {
            log.setText(initialLog);
        }

        refreshUI();
        stage.setScene(new Scene(root, 800, 620));
        stage.show();
    }

    private VBox buildTop() {
        Label trainerLbl = new Label("Trainer: " + controller.getState().getPlayer().getName());
        trainerLbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        trainerLbl.setTextFill(Color.WHITE);

        Label battleLbl = new Label("Battaglia " + controller.getState().getGlobalBattle());
        battleLbl.setFont(Font.font("Arial Black", FontWeight.BOLD, 20));
        battleLbl.setTextFill(Color.web("#e94560"));

        VBox playerCard = hpCard("IL TUO TEAM", "#4ecca3");
        playerHpLbl = (Label) playerCard.getChildren().get(1);
        playerBar   = (ProgressBar) playerCard.getChildren().get(2);

        VBox enemyCard = hpCard("NEMICO", "#ff6b6b");
        enemyHpLbl = (Label) enemyCard.getChildren().get(1);
        enemyBar   = (ProgressBar) enemyCard.getChildren().get(2);

        Label vs = new Label("VS");
        vs.setFont(Font.font("Arial Black", FontWeight.BOLD, 28));
        vs.setTextFill(Color.web("#f5a623"));

        HBox row = new HBox(18, playerCard, vs, enemyCard);
        row.setAlignment(Pos.CENTER);

        VBox top = new VBox(8, trainerLbl, battleLbl, row);
        top.setAlignment(Pos.CENTER);
        return top;
    }

   
}


