package com.vivianhonghoa.chess.model.events;

import java.util.EventListener;

public interface GameEngineObserver extends EventListener {

    default void onGameStarted(GameEngineEvent event) {

    }

    default void onGamePaused(GameEngineEvent event) {

    }

    default void onGameResumed(GameEngineEvent event) {

    }

    default void onGameStopped(GameEngineEvent event) {

    }

    default void onGameEnded(GameEngineEvent event) {

    }

    default void onGameTimeUpdated(GameEngineEvent event) {

    }

    default void onPlayerTurnChanged(GameEngineEvent event) {

    }
}
