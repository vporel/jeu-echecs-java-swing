package com.vivianhonghoa.chess.model.engine;

import com.vivianhonghoa.chess.model.pieces.Piece;

public final class AlgebraicNotation {

    private AlgebraicNotation() {}

    public static String format(History.Entry entry) {
        StringBuilder sb = new StringBuilder();

        sb.append(getPiecePrefix(entry.piece()));

        // For pawns that capture, add the origin file
        if (entry.piece().getType() == Piece.Type.PAWN && entry.captured() != null) {
            sb.append(colToFile(entry.from().col()));
        }

        if (entry.captured() != null) {
            sb.append("x");
        }

        sb.append(colToFile(entry.to().col()));
        sb.append(rowToRank(entry.to().row()));

        return sb.toString();
    }

    private static char colToFile(int col) {
        return (char) ('a' + col);
    }

    private static int rowToRank(int row) {
        return row + 1;
    }

    private static String getPiecePrefix(Piece piece) {
        return switch (piece.getType()) {
            case KING -> "K";
            case QUEEN -> "Q";
            case ROOK -> "R";
            case BISHOP -> "B";
            case KNIGHT -> "N";
            case PAWN -> "";
        };
    }
}
