package com.vivianhonghoa.chess.model.players.computer;

import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.model.players.ComputerMove;
import com.vivianhonghoa.chess.model.players.ComputerStrategy;

public abstract class StrategyDecorator implements ComputerStrategy {
    protected final ComputerStrategy wrapped;

    protected StrategyDecorator(ComputerStrategy wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ComputerMove chooseMove(Board board, Piece.Color color) {
        return wrapped.chooseMove(board, color);
    }
}
