package it.unicam.cs.mpgc.rpg126362.view;

import it.unicam.cs.mpgc.rpg126362.controller.IGameController;
import javafx.stage.Stage;

/**
* Battle Screen: Displays HP bars, combat log, and action buttons.
*/
public class BattleView {
    
private final Stage stage;
private final IGameController controller; 

    public BattleView(Stage stage, IGameController controller) {
        this.stage = stage;
        this.controller = controller;
    }

}

