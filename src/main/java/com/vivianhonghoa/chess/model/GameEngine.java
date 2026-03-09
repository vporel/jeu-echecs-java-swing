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
    private boolean hasStarted = false;
    private int turnCount = 0;
    private boolean isGameOver = false;
    private boolean isWhiteTurn = true;
    private Player player1;
    private Player player2;
    private int player1TimeRemaining; // in seconds
    private int player2TimeRemaining; // in seconds

    public GameEngine(){
        observers = new ArrayList<>();
        board = new Board();
    }

    public int getPlayerRemainingTime(int playerNumber) {
        if (playerNumber == 1) {
            return player1TimeRemaining;
        } else if (playerNumber == 2) {
            return player2TimeRemaining;
        }
        throw new IllegalArgumentException("Invalid player number: " + playerNumber);
    }

    public Board getBoard() {
        return board;
    }

    public void selectCase(Case selectedCase) {
        if(board.movePiece(board.getSelectedCase(), selectedCase)){
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
