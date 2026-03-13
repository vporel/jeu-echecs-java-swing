package com.vivianhonghoa.chess.model.events;

import java.util.EventListener;

public interface HistoryObserver extends EventListener {

    default void onChange(HistoryEvent event) {}
}

