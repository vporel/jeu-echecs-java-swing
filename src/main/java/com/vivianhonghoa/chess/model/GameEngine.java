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
    private boolean unlimitedTime = false;
    private final AtomicInteger player1TimeRemaining = new AtomicInteger(0);
    private final AtomicInteger player2TimeRemaining = new AtomicInteger(0);
    private final AtomicInteger winnerPlayerNumber = new AtomicInteger(0);
    private ScheduledExecutorService scheduler;
    private final History player1History = new History(); // white
    private final History player2History = new History(); // black

    public GameEngine(){
        board = new Board();
    }

    /*
     * Start the game with two players and a time limit
     * a time limit of null means unlimited time
     */
    public void start(Player player1, Player player2, Integer timeInSeconds, Piece[][] presetPieces) {
        // Do nothing if the game has already started
        if(started) return;

        board.reset();
        if(presetPieces != null) {
            board.setPieces(presetPieces);
        }

        this.player1 = player1;
        this.player2 = player2;
        this.player1TimeRemaining.set(timeInSeconds == null ? 0 : timeInSeconds);
        this.player2TimeRemaining.set(timeInSeconds == null ? 0 : timeInSeconds);
        this.started = true;
        this.unlimitedTime = timeInSeconds == null;
        this.paused.set(false);
        this.ended.set(false);
        this.isWhiteTurn = true;
        player1History.clear();
        player2History.clear();

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

    public void undo(int playerNumber) {
        if(!started || paused.get() || ended.get()) return;
        if(isPlayerTurn(playerNumber)) {
            return; // Can't undo on your own turn
        }

        History currentHistory = getPlayerHistory(playerNumber);
        if (currentHistory.isEmpty()) {
            return; // No moves to undo
        }

        History.Entry lastEntry = currentHistory.removeLast();
        board.undoMove(lastEntry.from(), lastEntry.to(), lastEntry.captured());
        isWhiteTurn = (playerNumber == 1); // Set turn back to the player who undid
        notifyObservers(GameEngineObserver::onPlayerTurnChanged);
    }

    public void giveUp(int playerNumber) {
        end(playerNumber == 1 ? 2 : 1);
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
        if(scheduler != null) {
            scheduler.shutdown();
        }
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
            Case from = board.getSelectedCase();
            Piece movingPiece = board.getPiece(from.row(), from.col());
            Piece capturedPiece = board.getPiece(selectedCase.row(), selectedCase.col());
            boolean moved = board.movePiece(from, selectedCase);
            if (moved) {
                History currentHistory = isWhiteTurn ? player1History : player2History;
                currentHistory.add(new History.Entry(movingPiece, from, selectedCase, capturedPiece));
                board.setSelectedCase(null);
                isWhiteTurn = !isWhiteTurn;
                if(board.isKingInCheckmate(Piece.Color.WHITE)) end(2);
                else if(board.isKingInCheckmate(Piece.Color.BLACK)) end(1);
                else if(board.isStalemate(Piece.Color.WHITE) || board.isStalemate(Piece.Color.BLACK)) end(0);
                else notifyObservers(GameEngineObserver::onPlayerTurnChanged);
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
        return started && !ended.get();
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

    public boolean isPlayerTurn(int playerNumber) {
        return getCurrentPlayerNumber() == playerNumber;
    }

    public Player getCurrentPlayer() {
        if (isWhiteTurn) {
            return player1;
        } else {
            return player2;
        }
    }

    public int getWinnerPlayerNumber() {
        return winnerPlayerNumber.get();
    }

    public History getPlayerHistory(int playerNumber) {
        if (playerNumber == 1) {
            return player1History;
        } else if (playerNumber == 2) {
            return player2History;
        }
        throw new IllegalArgumentException("Invalid player number: " + playerNumber);
    }

    public void addObserver(GameEngineObserver observer){
        observers.add(observer);
    }

    private void notifyObservers(BiConsumer<GameEngineObserver, GameEngineEvent> action){
        GameEngineEvent event = new GameEngineEvent();
        for (GameEngineObserver listener : observers) {
            action.accept(listener, event);
        }
    }
}
