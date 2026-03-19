package com.vivianhonghoa.chess.players;

import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.model.pieces.Queen;
import com.vivianhonghoa.chess.model.players.*;
import com.vivianhonghoa.chess.model.players.computer.*;

import javax.swing.SwingWorker;

public class ComputerPlayer extends Player {
    private static final int MOVE_DELAY_MS = 400;
    private final ComputerStrategy strategy;

    public ComputerPlayer(Piece.Color color, ComputerDifficulty difficulty) {
        super(color);
        this.strategy = switch (difficulty) {
            case EASY -> new RandomStrategy();
            case MEDIUM -> new HeuristicStrategy();
            case HARD -> new MinimaxStrategy();
        };
    }

    @Override
    public void onTurnStart() {
        new SwingWorker<ComputerMove, Void>() {
            @Override
            protected ComputerMove doInBackground() throws Exception {
                Thread.sleep(MOVE_DELAY_MS);
                return strategy.chooseMove(gameEngine.getBoard(), color);
            }

            @Override
            protected void done() {
                try {
                    ComputerMove move = get();
                    if (move != null) {
                        Board board = gameEngine.getBoard();
                        Board.PromotionHandler savedHandler = board.getPromotionHandler();
                        board.setPromotionHandler(Queen::new);
                        gameEngine.selectCase(move.from());
                        gameEngine.selectCase(move.to());
                        board.setPromotionHandler(savedHandler);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
}