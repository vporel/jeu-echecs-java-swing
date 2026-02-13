package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.Board;

import java.util.List;

public class Rook extends Piece{

    public Rook(Color color) {
        super(color);
    }

    @Override
    public List<Board.Case> getAccessibleCasses() {
        return List.of();
    }
}
