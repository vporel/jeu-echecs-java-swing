package com.vivianhonghoa.chess.model.players.computer;

import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.model.players.ComputerMove;
import com.vivianhonghoa.chess.model.players.ComputerStrategy;

public class LoggingStrategy extends StrategyDecorator {

    public LoggingStrategy(ComputerStrategy wrapped) {
        super(wrapped);
    }

    @Override
    public ComputerMove chooseMove(Board board, Piece.Color color) {
        long start = System.currentTimeMillis();
        ComputerMove move = wrapped.chooseMove(board, color);
        long elapsed = System.currentTimeMillis() - start;
        if (move != null) {
            System.out.printf("[Strategy] %s chose move %s -> %s in %d ms%n",
                    wrapped.getClass().getSimpleName(),
                    move.from(), move.to(), elapsed);
        } else {
            System.out.printf("[Strategy] %s returned no move in %d ms%n",
                    wrapped.getClass().getSimpleName(), elapsed);
        }
        return move;
    }
}
