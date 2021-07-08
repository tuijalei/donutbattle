package org.game.donutbattle;

import org.game.donutbattle.Player.Player;
import org.game.donutbattle.UI.UIHandler;

/**
 * Main class for the game - launching the game
 */
public class Game {

    public static GameStage GAME_STAGE;
    public static final int BIG_GRID_SIZE = 32;
    public static final int SMALL_GRID_SIZE = 16;

    public static Player PLAYER1;
    public static Player PLAYER2;

    public static Player WINNER;

    public static void main(String[] a){
        GAME_STAGE = GameStage.INITIALIZE_GAME;
        javafx.application.Application.launch(UIHandler.class);
    }

    public static Player getWinner(){
        if(GAME_STAGE == GameStage.PLAYER_1_TURN)
            return PLAYER1;
        else
            return PLAYER2;
    }
}
