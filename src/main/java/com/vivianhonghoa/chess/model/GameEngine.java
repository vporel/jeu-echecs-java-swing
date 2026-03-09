package com.vivianhonghoa.chess.model;

import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.pieces.Piece;

import javax.swing.Timer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public final class GameEngine {
    private static final int DEFAULT_TIME = 600; // 10 minutes per player
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
    private Timer timer;

    public GameEngine(){
        observers = new ArrayList<>();
        board = new Board();
    }

    // Start the game with two players and a time limit
    public void start(Player player1, Player player2, int timeInSeconds) {
        this.player1 = player1;
        this.player2 = player2;
        this.player1TimeRemaining = timeInSeconds;
        this.player2TimeRemaining = timeInSeconds;
        this.hasStarted = true;
        this.isGameOver = false;
        this.isWhiteTurn = true;
        this.turnCount = 0;

        // Stop the old timer if it exists
        if (timer != null) {
            timer.stop();
        }

        // Create a timer that ticks every 1 second (1000 ms)
        timer = new Timer(1000, e -> {
            if (!hasStarted || isGameOver) {
                return;
            }

            // Subtract 1 second from the current player's time
            if (isWhiteTurn) {
                player1TimeRemaining--;
            } else {
                player2TimeRemaining--;
            }

            // If time runs out, the game is over
            if (player1TimeRemaining <= 0 || player2TimeRemaining <= 0) {
                isGameOver = true;
                timer.stop();
            }
        });
        timer.start();
    }

    // Start the game with default time (10 minutes)
    public void start(Player player1, Player player2) {
        start(player1, player2, DEFAULT_TIME);
    }

    public void selectCase(Case selectedCase) {
        // Do nothing if the game has not started or is over
        if (!hasStarted || isGameOver) {
            return;
        }

        // Check if the player is selecting their own piece
        Piece selectedPiece = board.getPiece(selectedCase.row(), selectedCase.col());
        Piece.Color currentColor = isWhiteTurn ? Piece.Color.BLANC : Piece.Color.NOIR;

        // If a piece is already selected, try to move it
        if (board.getSelectedCase() != null) {
            boolean moved = board.movePiece(board.getSelectedCase(), selectedCase);
            if (moved) {
                board.setSelectedCase(null);
                turnCount++;
                isWhiteTurn = !isWhiteTurn;
                return;
            }
        }

        // Select a new piece (only if it belongs to the current player)
        if (selectedPiece != null && selectedPiece.getColor() == currentColor) {
            board.setSelectedCase(selectedCase);
        }
    }

    // Getters

    public Board getBoard() {
        return board;
    }

    public boolean isHasStarted() {
        return hasStarted;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getPlayer1TimeRemaining() {
        return player1TimeRemaining;
    }

    public int getPlayer2TimeRemaining() {
        return player2TimeRemaining;
    }

    public Player getCurrentPlayer() {
        if (isWhiteTurn) {
            return player1;
        } else {
            return player2;
        }
    }

    // Observer methods

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
