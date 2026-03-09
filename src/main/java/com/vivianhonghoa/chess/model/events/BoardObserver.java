package com.vivianhonghoa.chess.model.events;

import java.util.EventListener;

public interface BoardObserver extends EventListener {

    default void onCaseSelected(BoardEvent event) {

    }

    default void onPieceMoved(BoardEvent event) {

    }
}
