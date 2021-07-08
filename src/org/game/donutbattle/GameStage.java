package org.game.donutbattle;

/**
 * Enum for controlling gamestages
 */
public enum GameStage {

    INITIALIZE_GAME,
    PLAYER_1_SET_UP,
    PLAYER_2_SET_UP,
    PLAYER_1_TURN,
    PLAYER_2_TURN,
    GAME_OVER;

    public static GameStage getNextPlayerTurn(GameStage gameStage){
        if(gameStage == PLAYER_1_TURN){
            return PLAYER_2_TURN;
        }else{
            return PLAYER_1_TURN;
        }
    }
}
