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
    private Button attackBtn, healBtn, buffBtn, switchBtn;

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
    
    private VBox hpCard(String title, String color) {
        Label titleLbl = new Label(title);
        titleLbl.setTextFill(Color.web(color));
        titleLbl.setFont(Font.font("Arial Black", FontWeight.BOLD, 13));

        Label hpLbl = new Label();
        hpLbl.setTextFill(Color.WHITE);

        ProgressBar bar = new ProgressBar(1.0);
        bar.setPrefWidth(280);
        bar.setStyle("-fx-accent:" + color + ";");

        VBox card = new VBox(8, titleLbl, hpLbl, bar);
        card.setPadding(new Insets(14));
        card.setStyle("-fx-background-color:#0f172a;-fx-border-color:" + color
                + ";-fx-border-width:2;-fx-background-radius:12;-fx-border-radius:12;");
        return card;
    }

    private TextArea buildLog() {
        log = new TextArea();
        log.setEditable(false);
        log.setWrapText(true);
        log.setStyle("-fx-control-inner-background:#fff;-fx-text-fill:black;"
                + "-fx-font-family:'Courier New';-fx-font-size:13;-fx-font-weight:bold;"
                + "-fx-border-color:#0f3460;-fx-border-radius:6;-fx-background-radius:6;");
        log.setPrefHeight(200);
        BorderPane.setMargin(log, new Insets(10, 0, 10, 0));
        return log;
    }

    private VBox buildBottom() {
        attackBtn = actionBtn("⚔ ATTACCA", "#e94560", e -> act(controller.getBattleController().attack()));
        healBtn   = actionBtn("💊 CURA",   "#4ecca3", e -> act(controller.getBattleController().heal()));
        buffBtn   = actionBtn("⚡ POTENZIA","#f5a623", e -> act(controller.getBattleController().buff()));
        switchBtn = actionBtn("🔄 CAMBIA", "#0f3460", e -> showSwitch());

        Button saveBtn = new Button("💾 Salva & Esci");
        saveBtn.setStyle("-fx-background-color:#333;-fx-text-fill:#aaa;-fx-font-size:11;"
                + "-fx-cursor:hand;-fx-background-radius:5;");
        saveBtn.setOnAction(e -> {
            try {
                controller.getGameSaveService().saveState(
                    controller.getState(),
                    controller.getBattleController(),
                    log.getText()
                );
                new MainMenuView(stage).show();
            } catch (IOException ex) {
                addLog("Salvataggio fallito: " + ex.getMessage());
            }
        });

        HBox row1 = row(attackBtn, healBtn, buffBtn);
        HBox row2 = row(switchBtn, saveBtn);

        Label actionLabel = new Label("Scegli la tua azione:");
        actionLabel.setTextFill(Color.web("#a8a8b3"));

        VBox bottom = new VBox(10, actionLabel, row1, row2);
        bottom.setAlignment(Pos.CENTER);
        return bottom;
    }

    private void act(String msg) {
        addLog(msg);
        refreshUI();
        if (controller.getBattleController().isFinished()) onBattleEnd();
    }

    private void onBattleEnd() {
        setButtons(true);
        if (controller.getBattleController().getBattle().isPlayerWon()) {
            String summary = controller.handleVictory();
            addLog("\n" + summary);
            if (controller.getState().isGameWon()) {
                new EndView(stage, true, controller.getState().getPlayer().getName()).show();
                return;
            }
            Button cont = new Button("▶ Continua");
            cont.setStyle("-fx-background-color:#e94560;-fx-text-fill:white;-fx-font-weight:bold;"
                    + "-fx-padding:10 20;-fx-background-radius:8;-fx-cursor:hand;");
            cont.setOnAction(e -> new BattleView(stage, controller).show());
            ((VBox) ((BorderPane) stage.getScene().getRoot()).getBottom()).getChildren().add(cont);
        } else {
            addLog("\n" + controller.handleDefeat());
            new EndView(stage, false, controller.getState().getPlayer().getName()).show();
        }
    }

    private void showSwitch() {
        Player player = controller.getState().getPlayer();
        Alert dialog = new Alert(Alert.AlertType.NONE);
        dialog.setTitle("Cambia Creatura");
        dialog.setHeaderText("Scegli una creatura:");

        ToggleGroup tg = new ToggleGroup();
        VBox box = new VBox(10);
        for (int i = 0; i < player.getTeam().size(); i++) {
            Creature c = player.getTeam().get(i);
            if (c.isDefeated() || i == player.getActiveIndex()) continue;
            RadioButton rb = new RadioButton(c.getName() + " HP:" + c.getCurrentHp() + "/" + c.getMaxHp());
            rb.setUserData(i);
            rb.setToggleGroup(tg);
            box.getChildren().add(rb);
        }

        if (box.getChildren().isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Nessun'altra creatura disponibile!", ButtonType.OK).showAndWait();
            return;
        }

        ButtonType confirm = new ButtonType("Cambia!", ButtonBar.ButtonData.OK_DONE);
        dialog.getButtonTypes().addAll(confirm, new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE));
        dialog.getDialogPane().setContent(box);
        dialog.showAndWait()
            .filter(b -> b == confirm)
            .ifPresent(b -> {
                RadioButton sel = (RadioButton) tg.getSelectedToggle();
                if (sel != null) act(controller.getBattleController().switchTo((int) sel.getUserData()));
            });
    }

    private void refreshUI() {
        if (controller.getBattleController() == null) return;
        Creature enemy  = controller.getBattleController().getBattle().getEnemy();
        Creature active = controller.getState().getPlayer().getActive();
        enemyHpLbl.setText(enemy.getName()  + "  " + enemy.getCurrentHp()  + "/" + enemy.getMaxHp()  + " HP");
        enemyBar.setProgress((double) enemy.getCurrentHp()  / enemy.getMaxHp());
        playerHpLbl.setText(active.getName() + "  " + active.getCurrentHp() + "/" + active.getMaxHp() + " HP");
        playerBar.setProgress((double) active.getCurrentHp() / active.getMaxHp());
    }

    private void addLog(String msg) {
        if (msg != null && !msg.isBlank()) log.appendText(msg + "\n" + "─".repeat(40) + "\n");
    }

    private void setButtons(boolean disabled) {
        attackBtn.setDisable(disabled);
        healBtn.setDisable(disabled);
        buffBtn.setDisable(disabled);
        switchBtn.setDisable(disabled);
    }

    private Button actionBtn(String label, String color,
                              javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        Button b = new Button(label);
        b.setStyle("-fx-background-color:" + color + ";-fx-text-fill:white;-fx-font-weight:bold;"
                + "-fx-font-size:13;-fx-padding:10 20;-fx-background-radius:8;-fx-cursor:hand;");
        b.setPrefWidth(160);
        b.setOnAction(handler);
        return b;
    }

    private HBox row(javafx.scene.Node... nodes) {
        HBox r = new HBox(15, nodes);
        r.setAlignment(Pos.CENTER);
        return r;
    }
}


