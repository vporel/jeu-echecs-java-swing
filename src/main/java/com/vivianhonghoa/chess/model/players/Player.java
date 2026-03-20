package com.vivianhonghoa.chess.model.players;

import com.vivianhonghoa.chess.model.engine.GameEngine;
import com.vivianhonghoa.chess.model.pieces.Piece;

public abstract class Player {
    protected final Piece.Color color;
    protected GameEngine gameEngine;
    private int evaluation = 0;

    protected Player(Piece.Color color) {
        this.color = color;
    }

    public Piece.Color getColor() {
        return color;
    }

    public boolean isTurn() {
        return gameEngine != null && gameEngine.getCurrentPlayerContext().player().color == this.color;
    }

    public int getNumber() {
        return gameEngine.getPlayerNumberByColor(color);
    }

    public Player setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        return this;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public abstract void onTurnStart();
}
