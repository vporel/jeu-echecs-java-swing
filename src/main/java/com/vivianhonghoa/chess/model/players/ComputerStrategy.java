package com.vivianhonghoa.chess.model.players;

import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.pieces.Piece;

public interface ComputerStrategy {
    ComputerMove chooseMove(Board board, Piece.Color color);
}
