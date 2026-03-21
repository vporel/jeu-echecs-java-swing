package com.vivianhonghoa.chess.viewcontroller.utils;

import com.vivianhonghoa.chess.model.pieces.Piece;

public final class PieceRenderer {

    private PieceRenderer() {}

    public static String getUnicodeSymbol(Piece piece) {
        return switch (piece.getType()) {
            case KING -> "\u265A";
            case QUEEN -> "\u265B";
            case ROOK -> "\u265C";
            case BISHOP -> "\u265D";
            case KNIGHT -> "\u265E";
            case PAWN -> "\u265F";
        };
    }

    public static char getLetter(Piece piece) {
        return switch (piece.getType()) {
            case KING -> 'K';
            case QUEEN -> 'Q';
            case ROOK -> 'R';
            case BISHOP -> 'B';
            case KNIGHT -> 'N';
            case PAWN -> 'P';
        };
    }
}
