package com.vivianhonghoa.chess.model.events;


import java.util.EventListener;

public interface GameEngineObserver extends EventListener {

    void boxStateUpdated(PieceEvent event);
}
