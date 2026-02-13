package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.Board;

import java.util.List;

public class Pawn extends Piece{

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public List<Board.Case> getAccessibleCasses() {
        return List.of();
    }
}
