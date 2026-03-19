package com.vivianhonghoa.chess.model.players;

import com.vivianhonghoa.chess.model.engine.GameEngine;

public abstract class PlayerDecorator extends Player {
    protected final Player wrapped;

    protected PlayerDecorator(Player wrapped) {
        super(wrapped.getColor());
        this.wrapped = wrapped;
    }

    @Override
    public Player setGameEngine(GameEngine gameEngine) {
        super.setGameEngine(gameEngine);
        wrapped.setGameEngine(gameEngine);
        return this;
    }

    @Override
    public void onTurnStart() {
        wrapped.onTurnStart();
    }
}
