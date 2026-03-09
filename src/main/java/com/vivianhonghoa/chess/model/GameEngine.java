package com.vivianhonghoa.chess.model;

import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
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

    public void selectCase(Case selectedCase) {
        Piece selectedPiece = board.getSelectedPiece();
        if(selectedPiece != null && selectedPiece.canMoveTo(selectedCase)){
            board.movePiece(board.getSelectedCase(), selectedCase);
            board.setSelectedCase(null);
            return;
        }
        board.setSelectedCase(selectedCase);
    }

    public synchronized void addObserver(GameEngineObserver observer){
        observers.add(observer);
    }

    private void notifyObserver(Piece piece, BiConsumer<GameEngineObserver, GameEngineEvent> action){
        GameEngineEvent event = new GameEngineEvent();
        synchronized(this) {
            for (GameEngineObserver listener : observers) {
                action.accept(listener, event);
            }
        }
    }
}
