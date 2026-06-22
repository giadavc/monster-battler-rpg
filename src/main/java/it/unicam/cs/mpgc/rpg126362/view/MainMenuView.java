package it.unicam.cs.mpgc.rpg126362.view;

import it.unicam.cs.mpgc.rpg126362.controller.GameController;
import it.unicam.cs.mpgc.rpg126362.controller.IGameController;
import it.unicam.cs.mpgc.rpg126362.persistence.JsonStatsRepository;
import it.unicam.cs.mpgc.rpg126362.persistence.StatsRepository;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main menu view: Displays the main menu with options to start a new game, load a game, or exit.
 */

public class MainMenuView {

    private final Stage stage;
    private final IGameController controller = new GameController();
    private final StatsRepository statsRepository = new JsonStatsRepository();

    private TextField nameField;
    private ToggleGroup starterGroup;
    private int step = 0;
    private VBox content;
    private Button backBtn, nextBtn;

    public MainMenuView(Stage stage) { this.stage = stage; }

    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right,#111827,#1a1a2e,#0f172a);");
        root.setPadding(new Insets(28));
        root.setTop(buildHeader());

        content = new VBox(18);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(10, 30, 10, 30));
        root.setCenter(content);
        root.setBottom(buildFooter());

        renderStep();
        stage.setScene(new Scene(root, 900, 650));
        stage.show();
    }

    private VBox buildHeader() {
        Text title = new Text("MONSTER BATTLER RPG");
        title.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 34));
        title.setFill(Color.web("#e94560"));

        Text sub = new Text("Un'avventura RPG a fasi.");
        sub.setFont(Font.font("Arial", FontPosture.ITALIC, 15));
        sub.setFill(Color.web("#cbd5e1"));

        VBox h = new VBox(8, title, sub);
        h.setAlignment(Pos.CENTER);
        return h;
    }

    private VBox buildFooter() {
        Button loadBtn = new Button("CARICA PARTITA");
        loadBtn.setStyle(btnStyle("#0f3460"));
        loadBtn.setDisable(!controller.getGameSaveService().hasSave());
        loadBtn.setOnAction(e -> {
            try {
                controller.loadGame();
                new BattleView(stage, controller).show();
            } catch (IOException ex) {
                alert("Caricamento fallito: " + ex.getMessage());
            }
        });

        backBtn = new Button("INDIETRO");
        backBtn.setStyle(btnStyle("#334155"));
        backBtn.setOnAction(e -> { if (step > 0) { step--; renderStep(); } });

        nextBtn = new Button("AVANTI");
        nextBtn.setStyle(btnStyle("#e94560"));
        nextBtn.setOnAction(e -> { if (step < 1) { step++; renderStep(); } });

        HBox btnRow = new HBox(12, loadBtn, backBtn, nextBtn);
        btnRow.setAlignment(Pos.CENTER_RIGHT);

        StatsRepository.Stats stats = statsRepository.load();
        GridPane statsTable = buildStatsTable(stats);

        HBox footerRow = new HBox(50, statsTable, btnRow);
        footerRow.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(statsTable, Priority.ALWAYS);

        VBox footer = new VBox(footerRow);
        footer.setPadding(new Insets(12, 0, 0, 0));
        return footer;
    }

}