package com.vivianhonghoa.chess.model.players;

import com.vivianhonghoa.chess.model.engine.GameEngine;
import com.vivianhonghoa.chess.model.pieces.Piece;

public abstract class Player {
    protected final Piece.Color color;
    protected GameEngine gameEngine;

    protected Player(Piece.Color color) {
        this.color = color;
    }

    public Piece.Color getColor() {
        return color;
    }

    public final Player setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        return this;
    }

    public abstract void onTurnStart();
}
