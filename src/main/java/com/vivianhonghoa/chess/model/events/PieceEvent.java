package com.vivianhonghoa.chess.model.events;

import com.vivianhonghoa.chess.model.pieces.Piece;

public class PieceEvent {
    private final Piece piece;

    public PieceEvent(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() { return piece; }
}
