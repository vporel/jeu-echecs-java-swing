package com.vivianhonghoa.chess.players.computer.strategy;

import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.players.computer.ComputerMove;

public interface ComputerStrategy {
    ComputerMove chooseMove(Board board, Piece.Color color);
}
