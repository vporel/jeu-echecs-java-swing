package com.vivianhonghoa.chess.model.engine;

import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.model.players.Player;

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
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicBoolean paused = new AtomicBoolean(false);
    private final AtomicBoolean ended = new AtomicBoolean(false);
    private final AtomicInteger currentPlayerNumber = new AtomicInteger(1); // 1 for white, 2 for black
    private PlayerContext player1Context;
    private PlayerContext player2Context;
    private final AtomicInteger winnerPlayerNumber = new AtomicInteger(0);
    private ScheduledExecutorService scheduler;

    public GameEngine(){
        board = new Board();
    }

    /*
     * Start the game with two players and a time limit
     * a time limit of null means unlimited time
     */
    public void start(Player player1, Player player2, Integer timeInSeconds, Piece[][] presetPieces) {
        // Do nothing if the game has already started
        if(running.get()) return;

        if(player1.getColor() == player2.getColor()) {
            throw new IllegalArgumentException("Both players cannot have the same color");
        }

        this.player1Context = new PlayerContext(
            player1.setGameEngine(this),
            new AtomicInteger(timeInSeconds == null ? 0 : timeInSeconds),
            new History()
        );

        this.player2Context = new PlayerContext(
                player2.setGameEngine(this),
                new AtomicInteger(timeInSeconds == null ? 0 : timeInSeconds),
                new History()
        );
        this.winnerPlayerNumber.set(0);
        this.currentPlayerNumber.set(player1.getColor() == Piece.Color.WHITE ? 1 : 2);
        this.running.set(true);
        this.paused.set(false);
        this.ended.set(false);

        board.reset();
        if(presetPieces != null) {
            board.setPieces(presetPieces);
        }

        boolean unlimitedTime = timeInSeconds == null;

        if(!unlimitedTime) {
            // Create a timer that ticks every 1 second (1000 ms)
            Runnable task = () -> {
                if (!running.get() || paused.get() || ended.get()) {
                    return;
                }
                int snapshotCurrentPlayerNumber = getCurrentPlayerNumber();
                PlayerContext snapshotCurrentPlayerContext = getPlayerContext(snapshotCurrentPlayerNumber);

                // If time runs out, the game is over
                if (snapshotCurrentPlayerContext.remainingTime().decrementAndGet() <= 0) {
                    this.end(snapshotCurrentPlayerNumber);
                }

                notifyObservers(GameEngineObserver::onGameTimeUpdated);
            };
            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);
        }
        notifyObservers(GameEngineObserver::onGameStarted);
    }

    private void end(int winnerPlayerNumber) {
        if(!running.get() || ended.get()) return;
        ended.set(true);
        this.winnerPlayerNumber.set(winnerPlayerNumber);
        if(scheduler != null) {
            scheduler.shutdown();
        }
        notifyObservers(GameEngineObserver::onGameEnded);
    }

    public void undo(int playerNumber) {
        if(!running.get() || paused.get() || ended.get()) return;
        if(isPlayerTurn(playerNumber)) {
            return; // Can't undo on your own turn
        }

        History currentHistory = getPlayerContext(playerNumber).history();
        if (currentHistory.isEmpty()) {
            return; // No moves to undo
        }

        History.Entry lastEntry = currentHistory.removeLast();
        board.undoMove(lastEntry.from(), lastEntry.to(), lastEntry.captured());
        nextPlayer();
        notifyObservers(GameEngineObserver::onPlayerTurnChanged);
    }

    public void giveUp(int playerNumber) {
        end(playerNumber == 1 ? 2 : 1);
    }

    public void pause() {
        if(!running.get() || paused.get()) return;
        paused.set(true);
        notifyObservers(GameEngineObserver::onGamePaused);
    }

    public void resume() {
        if(!running.get() || !paused.get()) return;
        paused.set(false);
        notifyObservers(GameEngineObserver::onGameResumed);
    }

    public void stop() {
        if(!running.get()) return;
        running.set(false);
        if(scheduler != null) {
            scheduler.shutdown();
        }
        notifyObservers(GameEngineObserver::onGameStopped);
    }

    public void nextPlayer() {
        if(!running.get() || paused.get() || ended.get()) return;
        int newPlayerNumber = getCurrentPlayerNumber() == 1 ? 2 : 1;
        currentPlayerNumber.set(newPlayerNumber);
        getPlayerContext(newPlayerNumber).player().onTurnStart();
        notifyObservers(GameEngineObserver::onPlayerTurnChanged);
    }

    public void selectCase(Case selectedCase) {
        if (!running.get() || paused.get() || ended.get()) {
            return;
        }

        if(selectedCase == null) {
            board.setSelectedCase(null);
            return;
        }

        // Check if the player is selecting their own piece
        Piece selectedPiece = board.getPiece(selectedCase.row(), selectedCase.col());
        Piece.Color currentColor = getCurrentPlayerContext().player().getColor();

        // If a piece is already selected, try to move it
        if (board.getSelectedCase() != null) {
            Case from = board.getSelectedCase();
            Piece movingPiece = board.getPiece(from.row(), from.col());
            Piece capturedPiece = board.getPiece(selectedCase.row(), selectedCase.col());
            boolean moved = board.movePiece(from, selectedCase);
            if (moved) {
                History currentHistory = getCurrentPlayerContext().history();
                currentHistory.add(new History.Entry(movingPiece, from, selectedCase, capturedPiece));
                board.setSelectedCase(null);
                if(board.isKingInCheckmate(Piece.Color.WHITE)) end(2);
                else if(board.isKingInCheckmate(Piece.Color.BLACK)) end(1);
                else if(board.isStalemate(Piece.Color.WHITE) || board.isStalemate(Piece.Color.BLACK)) end(0);
                nextPlayer();
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

    public boolean isRunning() {
        return running.get() && !ended.get();
    }

    public boolean isPaused() {
        return paused.get();
    }

    public boolean hasEnded() {
        return ended.get();
    }

    public PlayerContext getPlayerContext(int playerNumber) {
        if (playerNumber == 1) {
            return player1Context;
        } else if (playerNumber == 2) {
            return player2Context;
        }
        throw new IllegalArgumentException("Invalid player number: " + playerNumber);
    }

    public PlayerContext getCurrentPlayerContext() {
        return getPlayerContext(getCurrentPlayerNumber());
    }

    public int getCurrentPlayerNumber() {
        return currentPlayerNumber.get();
    }

    public boolean isPlayerTurn(int playerNumber) {
        return getCurrentPlayerNumber() == playerNumber;
    }

    public int getWinnerPlayerNumber() {
        return winnerPlayerNumber.get();
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
