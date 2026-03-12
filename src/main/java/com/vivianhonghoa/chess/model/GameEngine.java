package com.vivianhonghoa.chess.model;

import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.pieces.Piece;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

public final class GameEngine {
    private final CopyOnWriteArrayList<GameEngineObserver> observers = new CopyOnWriteArrayList<>();
    private final Board board;
    private boolean started = false;
    private final AtomicBoolean paused = new AtomicBoolean(false);
    private final AtomicBoolean ended = new AtomicBoolean(false);
    private boolean isWhiteTurn = true;
    private Player player1;
    private Player player2;
    private boolean unlimitedTime = false; // If true, players have unlimited time
    private AtomicInteger player1TimeRemaining = new AtomicInteger(0); // in seconds
    private AtomicInteger player2TimeRemaining = new AtomicInteger(0); // in seconds
    private AtomicInteger winnerPlayerNumber = new AtomicInteger(0);
    private ScheduledExecutorService scheduler;

    public GameEngine(){
        board = new Board();
    }

    /*
     * Start the game with two players and a time limit
     * a time limit of null means unlimited time
     */
    public void start(Player player1, Player player2, Integer timeInSeconds) {
        // Do nothing if the game has already started
        if(started) return;

        this.player1 = player1;
        this.player2 = player2;
        this.player1TimeRemaining.set(timeInSeconds == null ? 0 : timeInSeconds);
        this.player2TimeRemaining.set(timeInSeconds == null ? 0 : timeInSeconds);
        this.started = true;
        this.unlimitedTime = timeInSeconds == null;
        this.paused.set(false);
        this.ended.set(false);
        this.isWhiteTurn = true;

        if(!unlimitedTime) {
            // Create a timer that ticks every 1 second (1000 ms)
            Runnable task = () -> {
                if (!started || paused.get() || ended.get()) {
                    return;
                }

                // Subtract 1 second from the current player's time
                if (isWhiteTurn) {
                    player1TimeRemaining.decrementAndGet();
                } else {
                    player2TimeRemaining.decrementAndGet();
                }

                // If time runs out, the game is over
                if (player1TimeRemaining.get() <= 0 || player2TimeRemaining.get() <= 0) {
                    this.end(player1TimeRemaining.get() > 0 ? 1 : 2);
                }

                notifyObservers(GameEngineObserver::onGameTimeUpdated);
            };
            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);
        }
        notifyObservers(GameEngineObserver::onGameStarted);
    }

    private void end(int winnerPlayerNumber) {
        if(!started || ended.get()) return;
        ended.set(true);
        this.winnerPlayerNumber.set(winnerPlayerNumber);
        if(scheduler != null) {
            scheduler.shutdown();
        }
        notifyObservers(GameEngineObserver::onGameEnded);
    }

    public void pause() {
        if(!started || paused.get()) return;
        paused.set(true);
        notifyObservers(GameEngineObserver::onGamePaused);
    }

    public void resume() {
        if(!started || !paused.get()) return;
        paused.set(false);
        notifyObservers(GameEngineObserver::onGameResumed);
    }

    public void stop() {
        if(!started) return;
        started = false;
        scheduler.shutdown();
        notifyObservers(GameEngineObserver::onGameStopped);
    }

    /**
     * @return null to indicate unlimited time, or the remaining time in seconds for the specified player
     */
    public Integer getPlayerRemainingTime(int playerNumber) {
        if(unlimitedTime) {
            return null;
        }
        if (playerNumber == 1) {
            return player1TimeRemaining.get();
        } else if (playerNumber == 2) {
            return player2TimeRemaining.get();
        }
        throw new IllegalArgumentException("Invalid player number: " + playerNumber);
    }

    public void selectCase(Case selectedCase) {
        // Do nothing if the game has not started or is over
        if (!started || paused.get() || ended.get()) {
            return;
        }

        if(selectedCase == null) {
            board.setSelectedCase(null);
            return;
        }

        // Check if the player is selecting their own piece
        Piece selectedPiece = board.getPiece(selectedCase.row(), selectedCase.col());
        Piece.Color currentColor = isWhiteTurn ? Piece.Color.WHITE : Piece.Color.BLACK;

        // If a piece is already selected, try to move it
        if (board.getSelectedCase() != null) {
            boolean moved = board.movePiece(board.getSelectedCase(), selectedCase);
            if (moved) {
                board.setSelectedCase(null);
                isWhiteTurn = !isWhiteTurn;
                notifyObservers(GameEngineObserver::onPlayerTurnChanged);
                return;
            }
        }

        // Select a new piece (only if it belongs to the current player)
        if (selectedPiece != null && selectedPiece.getColor() == currentColor) {
            board.setSelectedCase(selectedCase);
        }
    }

    public Board getBoard() {
        return board;
    }

    public boolean hasStarted() {
        return started;
    }

    public boolean isPaused() {
        return paused.get();
    }

    public boolean hasEnded() {
        return ended.get();
    }

    public int getCurrentPlayerNumber() {
        return isWhiteTurn ? 1 : 2;
    }

    public Player getCurrentPlayer() {
        if (isWhiteTurn) {
            return player1;
        } else {
            return player2;
        }
    }

    public synchronized void addObserver(GameEngineObserver observer){
        observers.add(observer);
    }

    private void notifyObservers(BiConsumer<GameEngineObserver, GameEngineEvent> action){
        GameEngineEvent event = new GameEngineEvent();
        synchronized(this) {
            for (GameEngineObserver listener : observers) {
                action.accept(listener, event);
            }
        }
    }
}
