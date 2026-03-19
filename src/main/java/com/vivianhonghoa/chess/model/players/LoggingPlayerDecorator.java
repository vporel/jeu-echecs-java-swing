package com.vivianhonghoa.chess.model.players;

public class LoggingPlayerDecorator extends PlayerDecorator {

    public LoggingPlayerDecorator(Player wrapped) {
        super(wrapped);
    }

    @Override
    public void onTurnStart() {
        System.out.printf("[Player] Turn started for %s (%s)%n",
                wrapped.getClass().getSimpleName(), getColor());
        super.onTurnStart();
    }
}
