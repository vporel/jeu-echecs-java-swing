package com.vivianhonghoa.chess.model;


import java.util.EventListener;

public interface BoxesManagerListener extends EventListener {

    void boxStateUpdated(BoxDataEvent event);
}
