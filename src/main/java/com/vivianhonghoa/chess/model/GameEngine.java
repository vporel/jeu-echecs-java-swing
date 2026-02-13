package com.vivianhonghoa.chess.model;

import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.model.events.PieceEvent;
import com.vivianhonghoa.chess.model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public final class GameEngine {
    private final List<GameEngineObserver> observers;
    private final Board board;

    public GameEngine(){
        observers = new ArrayList<>();
        board = new Board();
    }

    public Board getBoard() {
        return board;
    }

    public synchronized void addObserver(GameEngineObserver observer){
        observers.add(observer);
    }

    private void notifyObserver(Piece piece, BiConsumer<GameEngineObserver, PieceEvent> action){
        PieceEvent event = new PieceEvent(piece);
        List<GameEngineObserver> snapshot;
        synchronized(this) {
            for (GameEngineObserver listener : observers) {
                action.accept(listener, event);
            }
        }
    }

    public Board getPlateau() {
        return board;
    }
}
