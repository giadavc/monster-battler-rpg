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
    private GridPane buildStatsTable(StatsRepository.Stats stats) {
        GridPane table = new GridPane();
        table.setHgap(20); table.setVgap(5);
        table.setAlignment(Pos.CENTER_LEFT);
        table.setStyle("-fx-background-color:#16213e;-fx-padding:10;-fx-background-radius:8;"
                + "-fx-border-color:#334155;-fx-border-radius:8;-fx-border-width:1;");

        Label lTitle = new Label("Statistiche");
        lTitle.setTextFill(Color.web("#e94560"));
        lTitle.setFont(Font.font("Arial Black", FontWeight.BOLD, 12));

        Label lWins   = styledLabel("Vittorie:");
        Label vWins   = boldLabel(String.valueOf(stats.wins));
        Label lLosses = styledLabel("Sconfitte:");
        Label vLosses = boldLabel(String.valueOf(stats.losses));
        Label lDate   = styledLabel("Ultima partita:");
        Label vDate   = boldLabel(stats.lastDate);

        table.add(lTitle,  0, 0, 2, 1);
        table.add(lWins,   0, 1); table.add(vWins,   1, 1);
        table.add(lLosses, 2, 1); table.add(vLosses, 3, 1);
        table.add(lDate,   4, 1); table.add(vDate,   5, 1);
        return table;
    }

    private void renderStep() {
        content.getChildren().clear();
        backBtn.setDisable(step == 0);
        nextBtn.setDisable(step == 1);
        content.getChildren().add(step == 0 ? buildStoryStep() : buildStarterStep());
    }

    private VBox buildStoryStep() {
        nameField = new TextField();
        nameField.setPromptText("Il tuo nome da allenatore...");
        nameField.setMaxWidth(320);
        nameField.setStyle("-fx-background-color:#16213e;-fx-text-fill:white;-fx-border-color:#e94560;"
                + "-fx-border-radius:5;-fx-background-radius:5;-fx-padding:8;");

        Text story = new Text(
            "Il mondo è plasmato dalle antiche rivalità tra creature. Guida il tuo team attraverso\n"
            + "2 fasi di battaglie. Vinci per diventare più forte e sbloccare nuove creature.\n\n"
            + "Fase 1 (Early Game): LeafCat, TinySlime → sblocca StormHawk\n"
            + "Fase 2 (Late Game):  IcePhoenix, ShadowDragon → vinci il gioco!");
        story.setWrappingWidth(640);
        story.setFill(Color.web("#e2e8f0"));
        story.setFont(Font.font("Georgia", 17));

        Label nl = new Label("Inserisci il tuo nome da allenatore:");
        nl.setTextFill(Color.WHITE);
        nl.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        VBox box = card();
        box.getChildren().addAll(sectionTitle("1. Il Richiamo dell'Arena"), story, nl, nameField);
        return box;
    }

    private VBox buildStarterStep() {
        starterGroup = new ToggleGroup();
        HBox cards = new HBox(16);
        cards.setAlignment(Pos.CENTER);
        for (String[] s : new String[][]{{"FireWolf","100","18"},{"AquaTurtle","120","14"},{"NatureFox","110","16"}})
            cards.getChildren().add(creatureCard(s[0], s[1], s[2]));

        Button startBtn = new Button("INIZIA NUOVA PARTITA");
        startBtn.setStyle(btnStyle("#e94560"));
        startBtn.setOnAction(e -> startGame());

        VBox box = card();
        box.getChildren().addAll(sectionTitle("2. Scegli la tua Creatura Iniziale"), cards, startBtn);
        return box;
    }

    private VBox creatureCard(String name, String hp, String atk) {
        RadioButton rb = new RadioButton(name);
        rb.setToggleGroup(starterGroup);
        rb.setUserData(name);
        rb.setTextFill(Color.web("#e94560"));
        rb.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        Label hpL  = new Label("HP: "  + hp);  hpL.setTextFill(Color.web("#4ecca3"));
        Label atkL = new Label("ATK: " + atk); atkL.setTextFill(Color.web("#f5a623"));

        VBox card = new VBox(8, rb, hpL, atkL);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(15));
        card.setPrefWidth(170);
        card.setStyle("-fx-background-color:#16213e;-fx-border-color:#0f3460;"
                + "-fx-border-radius:10;-fx-background-radius:10;");

        rb.selectedProperty().addListener((o, w, sel) -> card.setStyle(sel
            ? "-fx-background-color:#0f3460;-fx-border-color:#e94560;-fx-border-radius:10;-fx-background-radius:10;-fx-border-width:2;"
            : "-fx-background-color:#16213e;-fx-border-color:#0f3460;-fx-border-radius:10;-fx-background-radius:10;"));
        card.setOnMouseClicked(e -> rb.setSelected(true));
        return card;
    }

    private void startGame() {
        String name = nameField != null ? nameField.getText().trim() : "";
        if (name.isEmpty()) { alert("Inserisci il tuo nome da allenatore."); step = 0; renderStep(); return; }
        RadioButton sel = starterGroup == null ? null : (RadioButton) starterGroup.getSelectedToggle();
        if (sel == null) { alert("Scegli una creatura iniziale."); return; }
        controller.startNewGame(name, (String) sel.getUserData());
        new BattleView(stage, controller).show();
    }

    private VBox card() {
        VBox box = new VBox(16);
        box.setAlignment(Pos.CENTER);
        box.setMaxWidth(820);
        box.setPadding(new Insets(24));
        box.setStyle("-fx-background-color:rgba(15,23,42,0.92);-fx-border-color:#334155;"
                + "-fx-border-radius:16;-fx-background-radius:16;-fx-border-width:1.5;");
        return box;
    }

    private Label sectionTitle(String t) {
        Label l = new Label(t);
        l.setTextFill(Color.web("#e94560"));
        l.setFont(Font.font("Arial Black", FontWeight.BOLD, 22));
        return l;
    }

    private Label styledLabel(String text) {
        Label l = new Label(text); l.setTextFill(Color.web("#a8a8b3")); return l;
    }

    private Label boldLabel(String text) {
        Label l = new Label(text); l.setTextFill(Color.WHITE); l.setFont(Font.font("System", FontWeight.BOLD, 12)); return l;
    }

    private String btnStyle(String color) {
        return "-fx-background-color:" + color + ";-fx-text-fill:white;-fx-font-weight:bold;"
                + "-fx-font-size:14;-fx-padding:10 30;-fx-border-radius:8;-fx-background-radius:8;-fx-cursor:hand;";
    }

    private void alert(String msg) {
        new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK).showAndWait();
    }
}