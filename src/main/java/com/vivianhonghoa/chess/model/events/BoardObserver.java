package com.vivianhonghoa.chess.model.events;

import java.util.EventListener;

public interface BoardObserver extends EventListener {

    void onCaseSelected(BoardEvent event);

    void onPieceMoved(BoardEvent event);
}
